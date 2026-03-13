package com.autofrontscreenplay.hooks;

import com.autofrontscreenplay.util.Constantes;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.annotations.Step;

public class NavegarsA implements Task {

    private final String path;

    protected NavegarsA(String path) {
        this.path = path;
    }

    public static NavegarsA laPaginaDeLogin() {
        return Tasks.instrumented(NavegarsA.class, Constantes.PATH_LOGIN);
    }

    public static NavegarsA laPaginaDeRegistro() {
        return Tasks.instrumented(NavegarsA.class, Constantes.PATH_REGISTER);
    }

    public static NavegarsA laRuta(String path) {
        return Tasks.instrumented(NavegarsA.class, path);
    }

    @Step("{0} navega a la ruta '#path'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url(System.getProperty(Constantes.SYSTEM_PROPERTY_BASE_URL, Constantes.BASE_URL) + path)
        );
    }
}
