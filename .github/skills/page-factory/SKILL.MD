
---

## description: 'Skill que especifica la implementación de Page Factory en Selenium WebDriver. Define el uso correcto de anotaciones, la inicialización perezosa de elementos y las estrategias de caché para optimizar la interacción con el DOM.'

# Skill: page-factory-selenium [QA]

## Responsabilidad

Implementar y gestionar la instanciación de WebElements mediante el motor Page Factory. Su objetivo es reducir el código repetitivo (boilerplate), centralizar los localizadores y optimizar el rendimiento y la resiliencia de las pruebas automatizadas mediante *Lazy Initialization*.

---

## ⚠️ REGLA ABSOLUTA — Consistencia y Rendimiento

```
PROHIBIDO ABSOLUTAMENTE:
  - Mezclar instanciación manual (`driver.findElement`) con Page Factory en la misma clase sin justificación.
  - Usar `@CacheLookup` en elementos dinámicos o que se refrescan mediante AJAX/React/Angular (provocará StaleElementReferenceException).
  - Olvidar llamar a `PageFactory.initElements()` antes de usar los elementos.

SIEMPRE usar:
  - Inicialización centralizada a través del constructor de una `BasePage`.
  - Nombres de variables descriptivos que indiquen el tipo de elemento (ej. `btnSubmitTicket`, `txtDescription`).
  - Anotaciones compuestas (`@FindAll`, `@FindBys`) solo cuando un selector simple (id, css) no sea suficiente o sea inestable.

```

---

## 1. El Motor de Page Factory: Conceptos Core

Page Factory actúa como un motor de búsqueda inteligente para los objetos de página.

* **El Motor `initElements()`:** Es el activador de la clase. No busca los elementos en el DOM inmediatamente; crea *proxies* (representantes). Esto evita que el test falle si la página aún no ha cargado completamente al instanciar la clase.
* **Inicialización Perezosa (Lazy Initialization):** Los elementos se buscan en el momento exacto en que ejecutas una acción (`.click()`, `.sendKeys()`).
* *Rendimiento:* Si una página tiene 50 elementos pero tu prueba usa 3, Selenium solo realiza 3 peticiones al DOM.
* *Resiliencia:* Mitiga el `StaleElementReferenceException`, ya que re-escanea el DOM cada vez que se invoca, asegurando un elemento "fresco".



---

## 2. Catálogo de Anotaciones Declarativas

En lugar de instanciar elementos manualmente, separamos la definición de su uso agrupando los localizadores al principio de la clase (inventario visual).

### A. `@FindBy` (Búsqueda Estándar)

La forma más común de mapear un elemento. Soporta estrategias como `id`, `xpath`, `css`, `name`, `className`, etc.

```java
@FindBy(id = "ticket-title-input")
private WebElement txtTicketTitle;

@FindBy(css = "button.create-issue")
private WebElement btnCreateIssue;

```

### B. Búsquedas Complejas: `@FindBys` vs `@FindAll`

Cuando un solo atributo no es suficiente para identificar unívocamente un elemento.

* **`@FindBys` (Condición AND):** El elemento debe cumplir **TODOS** los criterios (busca en cascada/descendencia).
```java
// Busca un botón 'Guardar' que esté estrictamente dentro del panel de 'Nuevo Ticket'
@FindBys({
    @FindBy(id = "new-ticket-panel"),
    @FindBy(className = "btn-save")
})
private WebElement btnSaveTicket;

```


* **`@FindAll` (Condición OR):** El elemento debe cumplir **AL MENOS UNO** de los criterios. Excelente para elementos que cambian de ID o clase según el estado.
```java
// Encuentra el botón de estado, ya sea que esté abierto o cerrado
@FindAll({
    @FindBy(id = "status-open"),
    @FindBy(id = "status-closed")
})
private WebElement badgeTicketStatus;

```



### C. Pro-Tip: El uso de `@CacheLookup`

Si un elemento (como un logo, un navbar o un footer estático) **nunca** cambia en la interfaz, se decora con `@CacheLookup`. Esto ordena guardar el elemento en memoria tras la primera búsqueda, evitando viajes innecesarios al DOM y acelerando drásticamente la ejecución.

---

## 3. Estructura de Implementación (Buenas Prácticas)

Para evitar llamar a `PageFactory.initElements(driver, this)` en cada clase individual de nuestro sistema, lo delegamos a la clase padre (`BasePage`).

```java
// 1. BasePage.java - Centraliza la inicialización
public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        // Se ejecuta automáticamente para cada clase hija
        PageFactory.initElements(driver, this); 
    }
}

// 2. TicketDashboardPage.java - Hereda y declara
public class TicketDashboardPage extends BasePage {
    
    @CacheLookup
    @FindBy(id = "main-system-logo")
    private WebElement imgLogo;

    @FindBy(id = "search-ticket-input")
    private WebElement txtSearch;

    public TicketDashboardPage(WebDriver driver) {
        super(driver); // Invoca la inicialización de PageFactory
    }

    public void searchTicket(String ticketId) {
        txtSearch.clear();
        txtSearch.sendKeys(ticketId);
    }
}

```

---

## 4. Limitaciones Conocidas (Cuándo NO usarlo)

* **Elementos altamente dinámicos (SPAs):** Si un elemento del DOM es destruido y recreado constantemente por un framework (como React/Vue) *mientras* interactúas con él, Page Factory puede lanzar excepciones. En estos casos aislados, una búsqueda dinámica con esperas explícitas explícitas (`wait.until(ExpectedConditions.presenceOfElementLocated(...))`) es más segura.
* **Listas dinámicas (WebTables):** Manipular tablas muy grandes con `@FindBy` sobre una `List<WebElement>` puede ser ineficiente si se iteran múltiples veces.

---

## Entregable: Refactorización a Page Factory

Generar/Actualizar clases en `{qa_output_folder}/pages/`:

```text
1. Identificar locators manuales (`driver.findElement`).
2. Mapearlos en la cabecera de la clase usando `@FindBy`.
3. Extender de `BasePage` para asegurar `initElements`.
4. Marcar con `@CacheLookup` elementos estáticos de la UI.

```

## Reporte

```
⚙️ PAGE-FACTORY-SELENIUM [QA] — REPORTE
════════════════════════════════════════════════
Clases de Página migradas a Factory: X
  Elementos @FindBy implementados:   X
  Elementos @CacheLookup (estáticos):X

Reglas validadas:
  Inicialización delegada en BasePage: ✅
  Encapsulamiento privado (private WebElement): ✅

Deuda técnica eliminada (driver.findElement): X instancias
════════════════════════════════════════════════

```

---