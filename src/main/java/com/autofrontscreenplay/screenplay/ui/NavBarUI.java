package com.autofrontscreenplay.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * UI Layer — NavBarComponent (global, post-autenticación)
 * Responsabilidad ÚNICA: exponer localizadores (Targets) de la barra de navegación.
 * PROHIBIDO: lógica de negocio, aserciones o acciones.
 */
public class NavBarUI {

    private NavBarUI() {}

    public static final Target NAVBAR_CONTAINER =
            Target.the("contenedor de la barra de navegación").locatedBy(".navbar");

    public static final Target USERNAME_DISPLAY =
            Target.the("nombre del usuario en la navbar").locatedBy(".navbar__username");

    public static final Target LOGOUT_BUTTON =
            Target.the("botón cerrar sesión").locatedBy(".navbar__link--logout");

    public static final Target ADMIN_BADGE =
            Target.the("badge de administrador").locatedBy(".navbar__admin-badge");

    public static final Target TICKETS_LINK =
            Target.the("enlace a tickets").locatedBy("a[href='/tickets'].navbar__link");

    public static final Target NEW_TICKET_LINK =
            Target.the("enlace a crear ticket").locatedBy("a[href='/tickets/new'].navbar__link");

    public static final Target NOTIFICATIONS_LINK =
            Target.the("enlace a notificaciones (solo admin)").locatedBy("a[href='/notifications'].navbar__link");

    public static final Target ASSIGNMENTS_LINK =
            Target.the("enlace a asignaciones (solo admin)").locatedBy("a[href='/assignments'].navbar__link");
}
