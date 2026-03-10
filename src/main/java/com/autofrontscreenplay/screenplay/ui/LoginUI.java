package com.autofrontscreenplay.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * UI Layer — LoginPage (/login)
 * Responsabilidad ÚNICA: exponer localizadores (Targets) de la pantalla de login.
 * PROHIBIDO: lógica de negocio, aserciones o acciones.
 */
public class LoginUI {

    private LoginUI() {}

    public static final Target EMAIL_INPUT =
            Target.the("campo email").locatedBy("#email");

    public static final Target PASSWORD_INPUT =
            Target.the("campo contraseña").locatedBy("#password");

    public static final Target SUBMIT_BUTTON =
            Target.the("botón iniciar sesión").locatedBy("button[type='submit']");

    public static final Target ERROR_MESSAGE =
            Target.the("mensaje de error de autenticación").locatedBy(".auth-error");

    public static final Target SPINNER =
            Target.the("spinner de carga").locatedBy(".spinner");

    public static final Target REGISTER_LINK =
            Target.the("link crear cuenta nueva").locatedBy(".btn-secondary");

    public static final Target PAGE_TITLE =
            Target.the("título de la página de login").locatedBy(".auth-title");
}
