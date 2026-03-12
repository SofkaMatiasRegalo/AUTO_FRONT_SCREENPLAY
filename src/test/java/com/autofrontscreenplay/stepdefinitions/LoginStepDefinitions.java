package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.TestData;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.questions.ElNombreDeUsuarioEnNavBar;
import com.autofrontscreenplay.tasks.IniciarSesion;
import com.autofrontscreenplay.util.Constantes;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class LoginStepDefinitions {

    @Given("que un usuario no tiene sesión activa")
    public void unUsuarioSinSesionActiva() {
        OnStage.theActorCalled(Constantes.ACTOR_VISITANTE)
                .attemptsTo(NavegarsA.laPaginaDeLogin());
    }

    @When("inicia sesión con credenciales válidas")
    public void iniciaSesionConDatos() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(IniciarSesion.conCredenciales(TestData.REGISTERED_USER_CREDENTIALS));
    }

    @And("el nombre del usuario es visible en la barra de navegación")
    public void nombreDeUsuarioVisible() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).contains(Constantes.REGISTERED_USERNAME)
        );
    }
}
