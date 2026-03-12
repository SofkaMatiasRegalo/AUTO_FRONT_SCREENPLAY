package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.TestData;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.questions.ElNombreDeUsuarioEnNavBar;
import com.autofrontscreenplay.tasks.RegistrarNuevoUsuario;
import com.autofrontscreenplay.util.Constantes;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class RegistroStepDefinitions {

    @Given("que un usuario accede por primera vez al sistema")
    public void usuarioAccedePorPrimeraVez() {
        OnStage.theActorCalled(Constantes.ACTOR_VISITANTE)
                .attemptsTo(NavegarsA.laPaginaDeRegistro());
    }

    @When("se registra con datos de usuario válidos y únicos")
    public void seRegistraConDatos() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(RegistrarNuevoUsuario.conDatos(TestData.REGISTERED_USER_DATA));
    }

    @And("su nombre de usuario es visible en la barra de navegación")
    public void suNombreDeUsuarioVisible() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).contains(Constantes.REGISTERED_USERNAME)
        );
    }
}
