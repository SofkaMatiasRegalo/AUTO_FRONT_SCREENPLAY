package com.autofrontscreenplay.screenplay.questions;

import com.autofrontscreenplay.screenplay.ui.NavBarUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question: ElNombreDeUsuarioEnNavBar
 * Responsabilidad ÚNICA: leer el nombre de usuario visible en la barra de navegación.
 */
public class ElNombreDeUsuarioEnNavBar implements Question<String> {

    private ElNombreDeUsuarioEnNavBar() {}

    public static ElNombreDeUsuarioEnNavBar actual() {
        return new ElNombreDeUsuarioEnNavBar();
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(NavBarUI.USERNAME_DISPLAY).answeredBy(actor);
    }
}
