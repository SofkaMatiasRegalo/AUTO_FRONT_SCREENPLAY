Feature: Flujo completo de autenticacion de usuarios
  Como visitante del sistema TicketSystem
  Quiero poder registrarme, cerrar sesion e iniciar sesion
  Para validar el flujo principal de autenticacion de forma independiente

  @critico @happy-path @autenticacion
  Scenario: Flujo end-to-end de autenticacion exitoso
    Given que un usuario accede por primera vez al sistema
    When se registra con datos de usuario válidos y únicos
    Then es redirigido al listado de tickets
    And su nombre de usuario es visible en la barra de navegación
    When cierra su sesión desde la barra de navegación
    Then es redirigido a la pantalla de inicio de sesión
    And que un usuario no tiene sesión activa
    When inicia sesión con credenciales válidas
    Then es redirigido al listado de tickets
    And el nombre del usuario es visible en la barra de navegación
