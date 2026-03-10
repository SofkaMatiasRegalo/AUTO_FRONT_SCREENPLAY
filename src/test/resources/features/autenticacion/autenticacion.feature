#language: es
Característica: Autenticación de usuarios
  Como usuario del sistema TicketSystem
  Quiero poder iniciar sesión, registrarme y cerrar sesión
  Para acceder a mis tickets de forma segura

  # ═══════════════════════════════════════════════════
  # HAPPY PATH — Flujos exitosos
  # ═══════════════════════════════════════════════════

  @critico @happy-path @login
  Escenario: Login exitoso como usuario registrado
    Dado que un usuario con rol "USER" no tiene sesión activa
    Cuando inicia sesión con credenciales válidas
    Entonces es redirigido al listado de tickets
    Y el nombre del usuario es visible en la barra de navegación

  @critico @happy-path @login
  Escenario: Login exitoso como administrador del sistema
    Dado que un usuario con rol "ADMIN" no tiene sesión activa
    Cuando inicia sesión con credenciales de administrador
    Entonces es redirigido al listado de tickets
    Y el nombre del administrador es visible en la barra de navegación

  @critico @happy-path @registro
  Escenario: Registro exitoso con datos únicos
    Dado que un visitante no tiene cuenta en el sistema
    Cuando completa el formulario de registro con datos válidos y únicos
    Entonces es redirigido al listado de tickets
    Y su nombre de usuario es visible en la barra de navegación

  @critico @happy-path @logout
  Escenario: Logout invalida la sesión del usuario
    Dado que el usuario ha iniciado sesión en el sistema
    Cuando cierra su sesión desde la barra de navegación
    Entonces es redirigido a la pantalla de inicio de sesión
    Y no puede acceder al listado de tickets sin autenticarse nuevamente

  # ═══════════════════════════════════════════════════
  # ERROR PATH — Manejo de errores
  # ═══════════════════════════════════════════════════

  @error-path @login
  Escenario: Login con credenciales incorrectas muestra mensaje de error
    Dado que un usuario no tiene sesión activa
    Cuando intenta iniciar sesión con credenciales incorrectas
    Entonces el sistema muestra el mensaje "El usuario y/o contraseña son incorrectos."
    Y el usuario permanece en la pantalla de inicio de sesión

  @error-path @registro
  Escenario: Registro con contraseñas no coincidentes muestra error inmediato
    Dado que un visitante no tiene cuenta en el sistema
    Cuando completa el formulario de registro con contraseñas que no coinciden
    Entonces el sistema muestra el mensaje "Las contraseñas no coinciden"
    Y el usuario permanece en la pantalla de registro
    Y no se realiza ninguna petición al servidor

  @error-path @registro
  Escenario: Registro con un email ya existente en el sistema muestra error
    Dado que un visitante intenta registrarse con datos de un usuario ya existente
    Cuando intenta crear la cuenta con esos datos duplicados
    Entonces el sistema muestra el mensaje "El usuario o el correo ya están registrados."
    Y el usuario permanece en la pantalla de registro
