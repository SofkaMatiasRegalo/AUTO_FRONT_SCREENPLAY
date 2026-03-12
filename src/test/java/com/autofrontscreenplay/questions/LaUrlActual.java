package com.autofrontscreenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

/**
 * Question: LaUrlActual
 * Responsabilidad ÚNICA: consultar la URL actual del navegador.
 */
public class LaUrlActual implements Question<String> {

    private LaUrlActual() {}

    public static LaUrlActual delNavegador() {
        return new LaUrlActual();
    }

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    }
}
