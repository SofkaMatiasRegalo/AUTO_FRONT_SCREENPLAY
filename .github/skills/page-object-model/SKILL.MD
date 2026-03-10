
---

## description: 'Skill que especifica la arquitectura, reglas y buenas prácticas para la implementación del patrón Page Object Model (POM), garantizando pruebas automatizadas mantenibles, legibles y resilientes a cambios en la UI.'

# Skill: page-object-model [QA]

## Responsabilidad

Diseñar, implementar y mantener la arquitectura de las pruebas automatizadas utilizando el patrón Page Object Model. El objetivo es abstraer la interfaz de usuario de la lógica de negocio de las pruebas, facilitando el mantenimiento y reduciendo la duplicación de código.

---

## ⚠️ REGLA ABSOLUTA — Separación de Responsabilidades

```
PROHIBIDO ABSOLUTAMENTE:
  - Incluir aserciones (asserts/expects) dentro de las Page Classes (las páginas interactúan, los tests validan).
  - Exponer localizadores (CSS/XPath) a los scripts de prueba.
  - Crear "God Objects" (clases de página con cientos de líneas que abarcan múltiples áreas funcionales).
  - Usar tiempos de espera fijos (Thread.sleep) dentro de los métodos de la página.

SIEMPRE usar:
  - Encapsulamiento estricto (localizadores privados).
  - Una `BasePage` para heredar métodos de interacción comunes.
  - Componentes (Component Objects) para elementos UI reutilizables (Navbars, Modales).
  - Nombres de métodos descriptivos enfocados en la acción del negocio (ej. `loginAs()`, no `clickLoginButton()`).

```

---

## 1. Arquitectura y Estructura del Patrón

El Patrón Page Object Model (POM) organiza las pruebas dividiendo las páginas web en objetos individuales y creando una capa de servicios. Esto simplifica el mantenimiento y hace que las pruebas sean resistentes a los cambios de la UI.

Su estructura técnica se divide en pilares fundamentales:

* **Clases de Página (Page Classes):** Punto único de verdad por cada página. Centraliza los cambios.
* **Repositorio de Localizadores:** Se encapsulan los selectores (IDs, CSS, XPaths) en variables privadas o decoradores. Evita el "Hard-coding".
* **Capa de Métodos de Acción:** Métodos públicos que representan acciones del usuario (ej. `searchProduct(item)`).
* **Desacoplamiento de la Lógica de Prueba:** Los scripts de prueba se limitan a orquestar el flujo y validar resultados.

### El Concepto de Encapsulamiento ("Caja Negra")

El script de prueba sabe qué quiere hacer, pero no cómo se hace técnicamente.

* **Ocultamiento de Selectores:** El script de prueba solo ve métodos, nunca etiquetas HTML.
* **Abstracción de Interacciones:** Acciones complejas (esperar un spinner, hacer scroll) quedan dentro del método de la Page Class.
* **Exposición de una Interfaz Limpia:** La clase actúa como una API.

> **Analogía del Cajero Automático:** Cuando usas un cajero, solo presionas "Retirar Efectivo". No necesitas saber qué motor mueve los billetes ni qué base de datos se consulta. Si el banco cambia el mecanismo interno, tú sigues presionando el mismo botón.

---

## 2. La Estructura de un Buen POM (Buenas Prácticas)

Para evitar los riesgos inherentes del patrón, la arquitectura debe estructurarse en tres niveles:

### A. La Clase Base (`BasePage`)

Todas las páginas deben heredar de una clase base. Aquí se centralizan los métodos de interacción genéricos (clics seguros, esperas explícitas, manejo de iframes).

```java
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Wrapper seguro para clics con esperas explícitas integradas
    protected void clickSafe(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}

```

### B. Component Objects (Composición sobre Herencia)

Para evitar "God Objects" y duplicación de código en elementos transversales (menús, pies de página, tablas).

```java
// Componente reutilizable
public class NavbarComponent {
    @FindBy(id = "logout-btn") private WebElement logoutBtn;
    
    public void logout() { /* lógica */ }
}

// Las páginas componen (usan) el componente
public class DashboardPage extends BasePage {
    public NavbarComponent navbar; // Accesible desde la página

    public DashboardPage(WebDriver driver) {
        super(driver);
        this.navbar = new NavbarComponent(driver);
    }
}

```

### C. Interfaces Fluidas (Method Chaining) - Opcional

Hacer que los métodos de navegación devuelvan la siguiente página instanciada, mejorando la legibilidad del test.

```java
// En LoginPage.java
public DashboardPage loginValidUser(String user, String pass) {
    userNameField.sendKeys(user);
    passwordField.sendKeys(pass);
    loginBtn.click();
    return new DashboardPage(driver); // Devuelve la siguiente página
}

// En el Test:
new LoginPage(driver)
    .loginValidUser("admin", "1234")
    .navbar.logout();

```

---


## 3. Comparativa Técnica

### ❌ Código "Espagueti" (Sin POM)

Deuda técnica inmediata. Si un selector cambia, hay que buscar y reemplazar en docenas de archivos.

```java
@Test
public void validLogin() {
    webDriver.get("https://www.saucedemo.com");
    webDriver.findElement(By.id("user-name")).sendKeys("standard_user");
    webDriver.findElement(By.id("password")).sendKeys("secret_sauce");
    webDriver.findElement(By.id("login-button")).click();
    
    // Aserción sucia mezclada con localizadores
    String text = webDriver.findElement(By.cssSelector("span.title")).getText();
    Assert.assertTrue(text.contains("Products"));
}

```

### ✅ Enfoque Arquitectónico (Con POM)

El test se lee como un caso de uso de negocio.

```java
// LoginPage.java (Encapsulamiento)
public class LoginPage extends BasePage {
    @FindBy(id = "user-name") private WebElement userNameField;
    @FindBy(id = "password") private WebElement passwordField;
    @FindBy(id = "login-button") private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        userNameField.sendKeys(user);
        passwordField.sendKeys(pass);
        clickSafe(loginBtn);
    }
}

// LoginTest.java (Solo orquestación y validación)
@Test
public void testValidLogin() {
    loginPage.navigateTo("https://www.saucedemo.com");
    loginPage.login("standard_user", "secret_sauce");
    
    Assert.assertTrue(productsPage.isTitleDisplayed(), "La página no cargó");
}

```

---

## 4. Riesgos a Mitigar

POM mejora el mantenimiento, pero mal aplicado genera problemas:

1. **God Objects:** Clases gigantes con cientos de métodos. *Solución: Dividir en Componentes.*
2. **Duplicación de Lógica UI:** Múltiples páginas con el método `clickLogout()`. *Solución: Component Objects.*
3. **Sobreingeniería:** Usar POM hiper-complejo para proyectos de 5 pruebas. *Solución: Adaptar la complejidad a la escala del proyecto.*
4. **Tests Poco Expresivos:** Métodos como `dashboardPage.doEverything()`. *Solución: Nomenclatura atómica (`createReport()`, `exportReport()`).*

---

## Entregable: Estructura del Proyecto de Automatización

El framework debe reflejar la siguiente jerarquía de archivos en `{qa_output_folder}/`:

```text
├── pages/
│   ├── base/
│   │   └── BasePage.java          # Wrappers y configuración base
│   ├── components/
│   │   ├── NavbarComponent.java   # Menú superior reutilizable
│   │   └── ModalComponent.java    # Ventanas emergentes
│   └── views/
│       ├── LoginPage.java         # Páginas específicas
│       └── DashboardPage.java
├── tests/
│   ├── base/
│   │   └── BaseTest.java          # Setup y Teardown de WebDriver
│   └── suites/
│       └── LoginTest.java         # Casos de prueba Gherkin/TestNG

```

---

## Proceso de Implementación

```
PASO 1 → Identificar las páginas y componentes clave del aplicativo.
PASO 2 → Crear la `BasePage` con utilidades genéricas de UI.
PASO 3 → Extraer elementos comunes a `Component Objects`.
PASO 4 → Crear las `Page Classes` extendiendo de `BasePage` y encapsulando localizadores.
PASO 5 → Desarrollar los métodos de acción utilizando abstracciones.
PASO 6 → Escribir los Scripts de Prueba consumiendo las clases de página.
PASO 7 → Realizar revisión cruzada: Verificar ausencia de "asserts" en páginas y selectores en tests.

```

## Reporte

```
🏗️ PAGE-OBJECT-MODEL [QA] — REPORTE DE ESTRUCTURA
════════════════════════════════════════════════
BasePage implementada:           [ ] SÍ / [ ] NO
Componentes extraídos:           X
Page Classes definidas:          X
Scripts de prueba refactorizados:X

Validaciones arquitectónicas:
  Aserciones fuera de las Pages: ✅
  Localizadores encapsulados:    ✅
  Uso de esperas explícitas:     ✅

Estado del Patrón: [OPTIMIZADO | EN REFACTORIZACIÓN]
════════════════════════════════════════════════

```

---