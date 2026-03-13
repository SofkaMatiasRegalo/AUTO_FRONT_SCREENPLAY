package com.autofrontscreenplay.questions;

import com.autofrontscreenplay.ui.NavBarUI;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Visibility;

public class LaNavBarEsVisible implements Question<Boolean> {

    private LaNavBarEsVisible() {}

    public static LaNavBarEsVisible enPantalla() {
        return new LaNavBarEsVisible();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return Visibility.of(NavBarUI.NAVBAR_CONTAINER).answeredBy(actor);
    }
}
