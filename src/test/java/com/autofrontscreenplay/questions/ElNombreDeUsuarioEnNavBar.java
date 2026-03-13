package com.autofrontscreenplay.questions;

import com.autofrontscreenplay.ui.NavBarUI;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

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
