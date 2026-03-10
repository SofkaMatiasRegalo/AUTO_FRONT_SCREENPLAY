package com.autofrontscreenplay.screenplay.tasks;

import com.autofrontscreenplay.screenplay.model.RegistrationData;
import com.autofrontscreenplay.screenplay.ui.RegisterUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.model.annotations.Step;

/**
 * Task: RegistrarNuevoUsuario
 * Responsabilidad ÚNICA: completar y enviar el formulario de registro.
 * No contiene localizadores (delegados a RegisterUI) ni aserciones.
 */
public class RegistrarNuevoUsuario implements Task {

    private final RegistrationData data;

    protected RegistrarNuevoUsuario(RegistrationData data) {
        this.data = data;
    }

    public static RegistrarNuevoUsuario conDatos(RegistrationData data) {
        return Tasks.instrumented(RegistrarNuevoUsuario.class, data);
    }

    @Step("{0} completa el formulario de registro con el usuario '#data.username'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Enter.theValue(data.getUsername()).into(RegisterUI.USERNAME_INPUT),
            Enter.theValue(data.getEmail()).into(RegisterUI.EMAIL_INPUT),
            Enter.theValue(data.getPassword()).into(RegisterUI.PASSWORD_INPUT),
            Enter.theValue(data.getConfirmPassword()).into(RegisterUI.CONFIRM_PASSWORD_INPUT),
            Click.on(RegisterUI.SUBMIT_BUTTON)
        );
    }
}
