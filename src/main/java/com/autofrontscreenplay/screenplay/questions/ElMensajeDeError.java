package com.autofrontscreenplay.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.questions.Text;

/**
 * Question: ElMensajeDeError
 * Responsabilidad ÚNICA: leer el texto del elemento de error dado un Target.
 */
public class ElMensajeDeError implements Question<String> {

    private final Target errorTarget;

    private ElMensajeDeError(Target errorTarget) {
        this.errorTarget = errorTarget;
    }

    public static ElMensajeDeError mostradoEn(Target errorTarget) {
        return new ElMensajeDeError(errorTarget);
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(errorTarget).answeredBy(actor);
    }
}
