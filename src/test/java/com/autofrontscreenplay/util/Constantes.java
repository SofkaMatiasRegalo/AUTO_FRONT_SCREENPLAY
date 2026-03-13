package com.autofrontscreenplay.util;

public class Constantes {

    private Constantes() {}
    public static final String BASE_URL                          = "http://localhost:3000";
    public static final String SYSTEM_PROPERTY_BASE_URL         = "webdriver.base.url";
    public static final String PATH_LOGIN                        = "/login";
    public static final String PATH_REGISTER                     = "/register";
    public static final String PATH_TICKETS                      = "/tickets";

    public static final String SELECTOR_EMAIL_INPUT              = "#email";
    public static final String SELECTOR_PASSWORD_INPUT           = "#password";
    public static final String SELECTOR_SUBMIT_BUTTON            = "//button[@type='submit']";
    public static final String SELECTOR_AUTH_ERROR               = ".auth-error";
    public static final String SELECTOR_SPINNER                  = ".spinner";
    public static final String SELECTOR_SECONDARY_BUTTON         = ".btn-secondary";
    public static final String SELECTOR_AUTH_TITLE               = ".auth-title";

    public static final String SELECTOR_USERNAME_INPUT           = "#username";
    public static final String SELECTOR_CONFIRM_PASSWORD_INPUT   = "#confirmPassword";

    public static final String SELECTOR_NAVBAR                   = ".navbar";
    public static final String SELECTOR_NAVBAR_USERNAME          = ".navbar__username";
    public static final String SELECTOR_NAVBAR_LOGOUT            = ".navbar__link--logout";
    public static final String SELECTOR_NAVBAR_ADMIN_BADGE       = ".navbar__admin-badge";
    public static final String SELECTOR_NAVBAR_TICKETS           = "a[href='/tickets'].navbar__link";
    public static final String SELECTOR_NAVBAR_NEW_TICKET        = "a[href='/tickets/new'].navbar__link";
    public static final String SELECTOR_NAVBAR_NOTIFICATIONS     = "a[href='/notifications'].navbar__link";
    public static final String SELECTOR_NAVBAR_ASSIGNMENTS       = "a[href='/assignments'].navbar__link";

    public static final String ACTOR_VISITANTE                   = "Visitante";
    public static final String CONTEXTO_REGISTRATION_DATA         = "registrationData";

    public static final String REGISTERED_USERNAME              = "Sofkau132";
    public static final String REGISTERED_EMAIL                  = "Sofkau132@test.com";
    public static final String REGISTERED_PASSWORD               = "NewUser1!1";

    public static final String REGISTRO_NUEVO_USUARIO_PREFIX     = "Sofkau132";
    public static final String REGISTRO_NUEVO_USUARIO_DOMINIO    = "@test.com";
    public static final String REGISTRO_NUEVO_USUARIO_PASSWORD   = "NewUser1!1";
}
