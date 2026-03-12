Feature: Registro de nuevos usuarios
  Como visitante del sistema TicketSystem
  Quiero poder crear una cuenta nueva
  Para acceder a mis tickets de forma segura

  @critico @happy-path @registro
  Scenario: Registro exitoso con datos únicos
    Given que un usuario accede por primera vez al sistema
    When se registra con datos de usuario válidos y únicos
    Then es redirigido al listado de tickets
    And su nombre de usuario es visible en la barra de navegación
