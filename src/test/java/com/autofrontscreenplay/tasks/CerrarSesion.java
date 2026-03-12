package com.autofrontscreenplay.tasks;

import com.autofrontscreenplay.ui.NavBarUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.annotations.Step;

/**
 * Task: CerrarSesion
 * Responsabilidad ÚNICA: hacer click en el botón de logout desde la navbar.
 * Dispara POST /api/auth/logout/ a través de la acción del frontend.
 */
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
