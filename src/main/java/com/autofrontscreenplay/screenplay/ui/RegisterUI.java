package com.autofrontscreenplay.screenplay.ui;

import net.serenitybdd.screenplay.targets.Target;

/**
 * UI Layer — RegisterPage (/register)
 * Responsabilidad ÚNICA: exponer localizadores (Targets) de la pantalla de registro.
 * PROHIBIDO: lógica de negocio, aserciones o acciones.
 */
public class RegisterUI {

    private RegisterUI() {}

    public static final Target USERNAME_INPUT =
            Target.the("campo nombre de usuario").locatedBy("#username");

    public static final Target EMAIL_INPUT =
            Target.the("campo email").locatedBy("#email");

    public static final Target PASSWORD_INPUT =
            Target.the("campo contraseña").locatedBy("#password");

    public static final Target CONFIRM_PASSWORD_INPUT =
            Target.the("campo confirmar contraseña").locatedBy("#confirmPassword");

    public static final Target SUBMIT_BUTTON =
            Target.the("botón crear cuenta").locatedBy("button[type='submit']");

    public static final Target ERROR_MESSAGE =
            Target.the("mensaje de error de registro").locatedBy(".auth-error");

    public static final Target SPINNER =
            Target.the("spinner de carga").locatedBy(".spinner");

    public static final Target LOGIN_LINK =
            Target.the("link iniciar sesión").locatedBy(".btn-secondary");

    public static final Target PAGE_TITLE =
            Target.the("título de la página de registro").locatedBy(".auth-title");
}
