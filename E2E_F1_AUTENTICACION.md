# E2E — F1: Autenticación (POM + PageFactory)

## Tabla de contenidos
1. [Contexto de la aplicación](#1-contexto-de-la-aplicación)
2. [Arquitectura de autenticación](#2-arquitectura-de-autenticación)
3. [Page Objects requeridos](#3-page-objects-requeridos)
4. [Selectores por página](#4-selectores-por-página)
5. [Flujos detallados](#5-flujos-detallados)
6. [Mensajes de error esperados](#6-mensajes-de-error-esperados)
7. [Endpoints de la API](#7-endpoints-de-la-api)
8. [Validaciones frontend (sin petición HTTP)](#8-validaciones-frontend-sin-petición-http)
9. [Estructura sugerida POM + PageFactory](#9-estructura-sugerida-pom--pagefactory)
10. [Datos de prueba](#10-datos-de-prueba)

---

## 1. Contexto de la aplicación

| Ítem | Valor |
|---|---|
| Framework frontend | React + TypeScript + Vite |
| Enrutamiento | React Router v6 |
| Ruta base de la app | `http://localhost:5173` (dev) |
| Ruta raíz `/` | Redirige automáticamente a `/login` |
| Autenticación | HttpOnly cookies (JWT). El frontend **nunca** maneja tokens directamente. |
| Servicio de usuarios | `http://localhost:8003/api` |

---

## 2. Arquitectura de autenticación

```
Navegador
  │
  ├── GET  /api/auth/me/        → Verifica sesión activa al cargar la app
  ├── POST /api/auth/login/     → Inicia sesión; el servidor setea cookies HttpOnly
  ├── POST /api/auth/           → Registra usuario nuevo; el servidor setea cookies HttpOnly
  └── POST /api/auth/logout/    → Invalida cookies en el servidor
```

- Las cookies se envían automáticamente en cada petición gracias a `withCredentials: true`.
- Al recibir un 401 en cualquier endpoint (excepto `/auth/me` y `/auth/refresh`), el cliente intenta un refresh automático llamando a `POST /api/auth/refresh/`. Si falla, redirige a `/login`.
- El estado del usuario se guarda en `AuthContext` (`user: User | null`).

---

## 3. Page Objects requeridos

| Page Object | Ruta | Responsabilidad |
|---|---|---|
| `LoginPage` | `/login` | Llenar y enviar formulario de login; leer errores |
| `RegisterPage` | `/register` | Llenar y enviar formulario de registro; leer errores |
| `NavBarComponent` | (global) | Leer usuario activo; hacer logout; verificar visibilidad de links |
| `TicketListPage` | `/tickets` | Verificar llegada exitosa post-login/registro |

### PageFactory — responsabilidades sugeridas

```
PageFactory
  ├── getLoginPage()       → instancia y retorna LoginPage
  ├── getRegisterPage()    → instancia y retorna RegisterPage
  ├── getNavBar()          → instancia y retorna NavBarComponent
  └── getTicketListPage()  → instancia y retorna TicketListPage
```

---

## 4. Selectores por página

### 4.1 LoginPage (`/login`)

| Elemento | Tipo | Selector recomendado |
|---|---|---|
| Contenedor principal | `div` | `.auth-container` |
| Título de la página | `h1` | `.auth-title` → texto: `"Bienvenido a TicketSystem"` |
| Campo email | `input[type="email"]` | `#email` o `[name="email"]` |
| Campo contraseña | `input[type="password"]` | `#password` o `[name="password"]` |
| Botón submit | `button[type="submit"]` | `.btn-primary` o `button[type="submit"]` |
| Texto del botón (normal) | — | `"Iniciar sesión"` |
| Texto del botón (loading) | — | `"Iniciando sesión..."` |
| Spinner de carga | `span` | `.spinner` (aparece durante loading) |
| Mensaje de error | `div` | `.auth-error` |
| Link a register | `a` | `.btn-secondary` → texto: `"Crear cuenta nueva"` |

> **Nota:** El input `email` tiene atributo `required` y `type="email"`, por lo que el navegador valida el formato antes de enviar. El input `password` también tiene `required`.

### 4.2 RegisterPage (`/register`)

| Elemento | Tipo | Selector recomendado |
|---|---|---|
| Contenedor principal | `div` | `.auth-container` |
| Título de la página | `h1` | `.auth-title` → texto: `"Crear cuenta en TicketSystem"` |
| Campo username | `input[type="text"]` | `#username` o `[name="username"]` |
| Campo email | `input[type="email"]` | `#email` o `[name="email"]` |
| Campo contraseña | `input[type="password"]` | `#password` o `[name="password"]` |
| Campo confirmar contraseña | `input[type="password"]` | `#confirmPassword` o `[name="confirmPassword"]` |
| Hint del campo username | `span` | `.form-hint` (texto: `"Mínimo 3 caracteres"`) |
| Hint del campo password | `span` | `.form-hint` (texto: `"Mínimo 8 caracteres, 1 mayúscula y 1 símbolo"`) |
| Botón submit | `button[type="submit"]` | `.btn-primary` o `button[type="submit"]` |
| Texto del botón (normal) | — | `"Crear cuenta"` |
| Texto del botón (loading) | — | `"Creando cuenta..."` |
| Mensaje de error | `div` | `.auth-error` |
| Link a login | `a` | `.btn-secondary` → texto: `"Iniciar sesión"` |

> **Nota:** username tiene `minLength={3}` y `required`. password tiene `minLength={8}` y `required`.

### 4.3 NavBarComponent (global, visible post-login)

| Elemento | Tipo | Selector recomendado |
|---|---|---|
| Contenedor Navbar | `nav` | `.navbar` |
| Logo / link a tickets | `a` | `.navbar__logo` → texto: `"TicketSystem"` |
| Nombre de usuario actual | `span` | `.navbar__username` |
| Badge Admin | `span` | `.navbar__admin-badge` → texto: `"Admin"` |
| Botón de logout | `button` | `.navbar__link--logout` o `button` con texto `"Cerrar Sesión"` |
| Link Tickets | `a` | `[href="/tickets"]` con clase `.navbar__link` |
| Link Crear Ticket | `a` | `[href="/tickets/new"]` con clase `.navbar__link` |
| Link Notificaciones (solo Admin) | `a` | `[href="/notifications"]` con clase `.navbar__link` |
| Link Asignaciones (solo Admin) | `a` | `[href="/assignments"]` con clase `.navbar__link` |
| Badge de notificaciones no leídas | `span` | `.navbar__badge` |
| Botón hamburguesa (mobile) | `button` | `.navbar__hamburger` |

---

## 5. Flujos detallados

### F1.1 — Login exitoso → redirige a `/tickets`

**Actor:** USER o ADMIN  
**Precondición:** Usuario registrado y activo en base de datos.

```
Pasos:
1. Navegar a /login
2. Verificar que el título sea "Bienvenido a TicketSystem"
3. Ingresar email válido en #email
4. Ingresar contraseña correcta en #password
5. Click en button[type="submit"]
6. Esperar que desaparezca el spinner (.spinner)
7. Verificar que la URL actual sea /tickets
8. Verificar que el Navbar sea visible (.navbar)
9. Verificar que .navbar__username contenga el nombre del usuario
```

**POST body enviado al backend:**
```json
{ "email": "usuario@test.com", "password": "MiPassword1!" }
```

**Respuesta esperada del backend (200 OK):**
```json
{
  "data": {
    "id": "1",
    "attributes": {
      "email": "usuario@test.com",
      "username": "usuario",
      "role": "USER",
      "is_active": true,
      "created_at": "2026-01-01T00:00:00Z"
    }
  }
}
```

---

### F1.2 — Login con credenciales incorrectas → muestra error

**Actor:** Anónimo  
**Precondición:** Ninguna sesión activa.

```
Pasos:
1. Navegar a /login
2. Ingresar email válido en #email
3. Ingresar contraseña incorrecta en #password
4. Click en button[type="submit"]
5. Esperar que .auth-error sea visible
6. Verificar el texto del error
7. Verificar que la URL siga siendo /login
8. Verificar que el botón vuelva a estar habilitado
```

**Error esperado (HTTP 401):**
```
"El usuario y/o contraseña son incorrectos."
```

**Error genérico (otros HTTP errors):**
```
"Ocurrió un error al iniciar sesión. Intenta nuevamente."
```

---

### F1.3 — Registro exitoso → redirige a `/tickets`

**Actor:** Nuevo usuario (email no registrado)  
**Precondición:** El email y username no existen en el sistema.

```
Pasos:
1. Navegar a /register
2. Verificar que el título sea "Crear cuenta en TicketSystem"
3. Ingresar username válido en #username (mínimo 3 caracteres)
4. Ingresar email único en #email
5. Ingresar contraseña válida en #password (mínimo 8 chars)
6. Ingresar la misma contraseña en #confirmPassword
7. Click en button[type="submit"]
8. Esperar que desaparezca el spinner
9. Verificar que la URL actual sea /tickets
10. Verificar que el Navbar muestre el username recién creado
```

**POST body enviado al backend:**
```json
{ "username": "nuevouser", "email": "nuevo@test.com", "password": "MiPassword1!" }
```

> **Nota:** `confirmPassword` es solo validación frontend, NO se envía al backend.

---

### F1.4 — Registro con contraseñas no coincidentes → error frontend

**Actor:** Anónimo  
**Precondición:** Ninguna. La validación ocurre antes de cualquier petición HTTP.

```
Pasos:
1. Navegar a /register
2. Ingresar username y email válidos
3. Ingresar contraseña en #password: "Password1!"
4. Ingresar contraseña diferente en #confirmPassword: "Password2!"
5. Click en button[type="submit"]
6. Verificar que .auth-error sea visible inmediatamente (sin petición HTTP)
7. Verificar el texto del error
8. Verificar que la URL siga siendo /register
9. Verificar que NO se realizó ninguna petición al backend
```

**Error esperado (solo frontend, sin HTTP):**
```
"Las contraseñas no coinciden"
```

---

### F1.5 — Registro con email duplicado → error 409

**Actor:** Anónimo  
**Precondición:** El email o username ya existe en la base de datos.

```
Pasos:
1. Navegar a /register
2. Ingresar username o email ya existente en el sistema
3. Ingresar contraseñas coincidentes y válidas
4. Click en button[type="submit"]
5. Esperar respuesta del backend
6. Verificar que .auth-error sea visible
7. Verificar el texto del error
8. Verificar que la URL siga siendo /register
```

**Error esperado (HTTP 409):**
```
"El usuario o el correo ya están registrados."
```

---

### F1.6 — Logout → sesión eliminada, redirige a `/login`

**Actor:** Usuario autenticado (USER o ADMIN)  
**Precondición:** Sesión activa.

```
Pasos:
1. Hacer login exitoso (sesión activa en /tickets)
2. Localizar el botón de logout en el Navbar (.navbar__link--logout)
3. Click en "Cerrar Sesión"
4. Verificar que la URL sea /login
5. Verificar que el Navbar ya no sea visible (o no muestre datos de usuario)
6. (Opcional) Intentar navegar a /tickets manualmente
7. Verificar que redirige de vuelta a /login (sesión inválida)
```

**POST al backend en logout:**
```
POST http://localhost:8003/api/auth/logout/
```

- El backend invalida las cookies.  
- El `AuthContext` setea `user = null`.

---

## 6. Mensajes de error esperados

| Flujo | Origen | Mensaje exacto |
|---|---|---|
| F1.2 | HTTP 401 backend | `"El usuario y/o contraseña son incorrectos."` |
| F1.2 | Otro error HTTP | `"Ocurrió un error al iniciar sesión. Intenta nuevamente."` |
| F1.4 | Frontend (sin HTTP) | `"Las contraseñas no coinciden"` |
| F1.4 | Frontend (sin HTTP) | `"La contraseña debe tener al menos 8 caracteres"` |
| F1.5 | HTTP 409 backend | `"El usuario o el correo ya están registrados."` |
| F1.5 | Otro error HTTP | `"Ocurrió un error al crear la cuenta. Intenta nuevamente."` |

> El elemento que muestra los errores es siempre `.auth-error` (un `div` con borde rojo).

---

## 7. Endpoints de la API

| Método | URL | Descripción | Body |
|---|---|---|---|
| `GET` | `http://localhost:8003/api/auth/me/` | Verifica sesión activa | — |
| `POST` | `http://localhost:8003/api/auth/login/` | Login | `{ email, password }` |
| `POST` | `http://localhost:8003/api/auth/` | Registro de nuevo usuario | `{ username, email, password }` |
| `POST` | `http://localhost:8003/api/auth/logout/` | Logout | — |
| `POST` | `http://localhost:8003/api/auth/refresh/` | Refresco de token (automático) | — |

> Todos los clientes usan `withCredentials: true`. Las cookies se gestionan automáticamente por el navegador.

---

## 8. Validaciones frontend (sin petición HTTP)

Estas validaciones ocurren en el manejador `handleSubmit` **antes** de llamar al backend:

| Campo | Regla | Mensaje |
|---|---|---|
| `password` ≠ `confirmPassword` | No coinciden | `"Las contraseñas no coinciden"` |
| `password.length < 8` | Demasiado corta | `"La contraseña debe tener al menos 8 caracteres"` |

> Los atributos HTML `required`, `minLength`, y `type="email"` activan validación nativa del navegador antes de que el evento `submit` llegue al handler de React.

---

## 9. Estructura sugerida POM + PageFactory

```
e2e/
├── pages/
│   ├── LoginPage.ts
│   │     - emailInput
│   │     - passwordInput
│   │     - submitButton
│   │     - errorMessage
│   │     - registerLink
│   │     + fillForm(email, password)
│   │     + submit()
│   │     + getErrorText(): string
│   │     + waitForRedirectToTickets()
│   │
│   ├── RegisterPage.ts
│   │     - usernameInput
│   │     - emailInput
│   │     - passwordInput
│   │     - confirmPasswordInput
│   │     - submitButton
│   │     - errorMessage
│   │     - loginLink
│   │     + fillForm(username, email, password, confirmPassword)
│   │     + submit()
│   │     + getErrorText(): string
│   │     + waitForRedirectToTickets()
│   │
│   ├── NavBarComponent.ts
│   │     - logoutButton
│   │     - usernameDisplay
│   │     - adminBadge
│   │     + logout()
│   │     + getUsernameText(): string
│   │     + isAdminBadgeVisible(): boolean
│   │
│   └── TicketListPage.ts
│         - pageContainer
│         + isLoaded(): boolean
│
├── factory/
│   └── PageFactory.ts
│         + getLoginPage(driver): LoginPage
│         + getRegisterPage(driver): RegisterPage
│         + getNavBar(driver): NavBarComponent
│         + getTicketListPage(driver): TicketListPage
│
├── fixtures/
│   └── users.ts
│         USER_CREDENTIALS   = { email: '...', password: '...' }
│         ADMIN_CREDENTIALS  = { email: '...', password: '...' }
│         NEW_USER           = { username: '...', email: '...', password: '...' }
│         INVALID_CREDENTIALS = { email: '...', password: 'wrong' }
│
└── tests/
    └── auth.spec.ts
          describe('F1 - Autenticación')
            ✓ F1.1 - Login exitoso como USER → /tickets
            ✓ F1.1 - Login exitoso como ADMIN → /tickets
            ✓ F1.2 - Login con credenciales incorrectas → error
            ✓ F1.3 - Registro exitoso → /tickets
            ✓ F1.4 - Registro contraseñas no coincidentes → error frontend
            ✓ F1.5 - Registro email duplicado → error 409
            ✓ F1.6 - Logout → /login
```

---

## 10. Datos de prueba

```typescript
// fixtures/users.ts

export const USER_CREDENTIALS = {
  email: 'user@test.com',
  password: 'UserPass1!',
};

export const ADMIN_CREDENTIALS = {
  email: 'admin@test.com',
  password: 'AdminPass1!',
};

// Usar email único en cada ejecución para evitar conflictos en F1.3
export const generateNewUser = () => ({
  username: `testuser_${Date.now()}`,
  email: `testuser_${Date.now()}@test.com`,
  password: 'NewUser1!',
  confirmPassword: 'NewUser1!',
});

export const DUPLICATE_USER = {
  username: 'user',               // ya existe
  email: 'user@test.com',         // ya existe
  password: 'Password1!',
  confirmPassword: 'Password1!',
};

export const MISMATCHED_PASSWORDS = {
  username: 'mismatchuser',
  email: 'mismatch@test.com',
  password: 'Password1!',
  confirmPassword: 'OtherPass2@',
};

export const INVALID_CREDENTIALS = {
  email: 'user@test.com',
  password: 'wrongpassword',
};
```

---

## Notas adicionales para el implementador

1. **Estado inicial de la app:** Al cargar, React llama a `GET /api/auth/me/` para verificar la sesión. Si hay cookie válida, el usuario queda autenticado automáticamente. Tener en cuenta este comportamiento en pruebas que requieran partir sin sesión.

2. **Loading state:** Al hacer submit, el botón se deshabilita y aparece `.spinner`. Esperar a que el botón vuelva a estar habilitado O a que ocurra la redirección antes de hacer assertions.

3. **Modo oscuro/claro:** El botón de tema (`.navbar__theme-toggle`) es independiente de la autenticación. No afecta los flujos.

4. **Mobile / hamburger:** En viewports pequeños, el menú se colapsa y aparece `.navbar__hamburger`. Si se hacen pruebas en mobile, hacer click en el hamburguesa antes de interactuar con los links del Navbar.

5. **Cookies HttpOnly:** No es posible leer ni setear el token JWT directamente desde JavaScript. Para saltar el login en tests que no prueban autenticación, hacer un `POST /api/auth/login/` vía la API del cliente HTTP del framework de testing (Playwright: `request.post(...)`) y mantener el contexto del navegador.
