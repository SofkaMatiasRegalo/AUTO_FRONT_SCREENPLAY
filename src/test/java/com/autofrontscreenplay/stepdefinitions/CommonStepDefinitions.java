package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.questions.LaUrlActual;
import com.autofrontscreenplay.util.Constantes;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class CommonStepDefinitions {

    @Then("es redirigido al listado de tickets")
    public void esRedirigidoATickets() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            Ensure.that(LaUrlActual.delNavegador()).contains(Constantes.PATH_TICKETS)
        );
    }
}
