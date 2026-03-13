package com.autofrontscreenplay.ui;

import com.autofrontscreenplay.util.Constantes;
import net.serenitybdd.screenplay.targets.Target;

public class LoginUI {

    private LoginUI() {}

    public static final Target EMAIL_INPUT =
            Target.the("campo email").locatedBy(Constantes.SELECTOR_EMAIL_INPUT);

    public static final Target PASSWORD_INPUT =
            Target.the("campo contraseña").locatedBy(Constantes.SELECTOR_PASSWORD_INPUT);

    public static final Target SUBMIT_BUTTON =
            Target.the("botón iniciar sesión").locatedBy(Constantes.SELECTOR_SUBMIT_BUTTON);

    public static final Target SPINNER =
            Target.the("spinner de carga").locatedBy(Constantes.SELECTOR_SPINNER);

    public static final Target REGISTER_LINK =
            Target.the("link crear cuenta nueva").locatedBy(Constantes.SELECTOR_SECONDARY_BUTTON);

    public static final Target PAGE_TITLE =
            Target.the("título de la página de login").locatedBy(Constantes.SELECTOR_AUTH_TITLE);
}
