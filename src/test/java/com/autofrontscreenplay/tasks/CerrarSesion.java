package com.autofrontscreenplay.tasks;

import com.autofrontscreenplay.ui.NavBarUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;

public class CerrarSesion implements Task {

    protected CerrarSesion() {}

    public static CerrarSesion desdeNavBar() {
        return Tasks.instrumented(CerrarSesion.class);
    }

    @Step("{0} cierra su sesión desde la barra de navegación")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Click.on(NavBarUI.LOGOUT_BUTTON)
        );
    }
}
