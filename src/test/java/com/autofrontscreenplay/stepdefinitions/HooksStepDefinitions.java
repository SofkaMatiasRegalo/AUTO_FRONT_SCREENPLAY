package com.autofrontscreenplay.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

/**
 * Cucumber Hooks para gestión del ciclo de vida del Actor y el Stage.
 */
public class HooksStepDefinitions {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After
    public void limpiarEscenario() {
        OnStage.drawTheCurtain();
    }
}
