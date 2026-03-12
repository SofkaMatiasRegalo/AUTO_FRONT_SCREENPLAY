Feature: Cierre de sesión de usuarios
  Como usuario autenticado en TicketSystem
  Quiero poder cerrar mi sesión activa
  Para proteger el acceso a mi cuenta

  @critico @happy-path @logout
  Scenario: Cierre de sesión exitoso desde la barra de navegación
    Given que un usuario tiene una sesión activa
    When cierra su sesión desde la barra de navegación
    Then es redirigido a la pantalla de inicio de sesión
