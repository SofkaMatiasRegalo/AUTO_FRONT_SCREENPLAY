package com.autofrontscreenplay.ui;

import com.autofrontscreenplay.util.Constantes;
import net.serenitybdd.screenplay.targets.Target;

public class NavBarUI {

    private NavBarUI() {}

    public static final Target NAVBAR_CONTAINER =
            Target.the("contenedor de la barra de navegación").locatedBy(Constantes.SELECTOR_NAVBAR);

    public static final Target USERNAME_DISPLAY =
            Target.the("nombre del usuario en la navbar").locatedBy(Constantes.SELECTOR_NAVBAR_USERNAME);

    public static final Target LOGOUT_BUTTON =
            Target.the("botón cerrar sesión").locatedBy(Constantes.SELECTOR_NAVBAR_LOGOUT);
    public static final Target TICKETS_LINK =
            Target.the("enlace a tickets").locatedBy(Constantes.SELECTOR_NAVBAR_TICKETS);

    public static final Target NEW_TICKET_LINK =
            Target.the("enlace a crear ticket").locatedBy(Constantes.SELECTOR_NAVBAR_NEW_TICKET);

}
