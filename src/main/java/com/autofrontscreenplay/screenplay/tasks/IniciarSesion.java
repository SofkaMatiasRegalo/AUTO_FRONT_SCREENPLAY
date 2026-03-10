package com.autofrontscreenplay.screenplay.tasks;

import com.autofrontscreenplay.screenplay.model.UserCredentials;
import com.autofrontscreenplay.screenplay.ui.LoginUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.model.annotations.Step;

/**
 * Task: IniciarSesion
 * Responsabilidad ÚNICA: llenar el formulario de login y enviar las credenciales.
 * No contiene localizadores (delegados a LoginUI) ni aserciones (delegadas a Questions).
 */
public class IniciarSesion implements Task {

    private final UserCredentials credentials;

    protected IniciarSesion(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public static IniciarSesion conCredenciales(UserCredentials credentials) {
        return Tasks.instrumented(IniciarSesion.class, credentials);
    }

    public static IniciarSesion conCredenciales(String email, String password) {
        return Tasks.instrumented(IniciarSesion.class, UserCredentials.of(email, password));
    }

    @Step("{0} inicia sesión con el email '#credentials.email'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Enter.theValue(credentials.getEmail()).into(LoginUI.EMAIL_INPUT),
            Enter.theValue(credentials.getPassword()).into(LoginUI.PASSWORD_INPUT),
            Click.on(LoginUI.SUBMIT_BUTTON)
        );
    }
}
