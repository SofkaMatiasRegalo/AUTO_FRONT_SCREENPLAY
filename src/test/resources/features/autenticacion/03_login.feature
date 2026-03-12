Feature: Inicio de sesión de usuarios
  Como usuario registrado del sistema TicketSystem
  Quiero poder iniciar sesión con mis credenciales
  Para acceder a mis tickets de forma segura

  @critico @happy-path @login
  Scenario: Login exitoso como usuario registrado
    Given que un usuario no tiene sesión activa
    When inicia sesión con credenciales válidas
    Then es redirigido al listado de tickets
    And el nombre del usuario es visible en la barra de navegación
