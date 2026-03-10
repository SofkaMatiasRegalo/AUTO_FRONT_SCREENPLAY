package com.autofrontscreenplay.screenplay.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.model.annotations.Step;

/**
 * Task: NavegarsA
 * Responsabilidad ÚNICA: navegar a una ruta relativa de la aplicación.
 * Centraliza la navegación evitando URLs hardcodeadas en los step definitions.
 */
public class NavegarsA implements Task {

    private final String path;

    protected NavegarsA(String path) {
        this.path = path;
    }

    public static NavegarsA laPaginaDeLogin() {
        return Tasks.instrumented(NavegarsA.class, "/login");
    }

    public static NavegarsA laPaginaDeRegistro() {
        return Tasks.instrumented(NavegarsA.class, "/register");
    }

    public static NavegarsA laRuta(String path) {
        return Tasks.instrumented(NavegarsA.class, path);
    }

    @Step("{0} navega a la ruta '#path'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url(System.getProperty("webdriver.base.url", "http://localhost:5173") + path)
        );
    }
}
