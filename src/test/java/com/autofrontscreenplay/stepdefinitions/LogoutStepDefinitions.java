package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.UsuarioRegistradoContexto;
import com.autofrontscreenplay.model.UserCredentials;
import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.questions.LaUrlActual;
import com.autofrontscreenplay.tasks.CerrarSesion;
import com.autofrontscreenplay.tasks.IniciarSesion;
import com.autofrontscreenplay.util.Constantes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class LogoutStepDefinitions {

    private RegistrationData registrationData;

    @Given("que un usuario tiene una sesión activa")
    public void unUsuarioConSesionActiva() {
        registrationData = UsuarioRegistradoContexto.obtenerRequerido();
        OnStage.theActorCalled(Constantes.ACTOR_VISITANTE)
                .attemptsTo(
                    NavegarsA.laPaginaDeLogin(),
                    IniciarSesion.conCredenciales(UserCredentials.of(registrationData.getEmail(), registrationData.getPassword()))
                );
    }

    @When("cierra su sesión desde la barra de navegación")
    public void cierraSesion() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(CerrarSesion.desdeNavBar());
    }

    @Then("es redirigido a la pantalla de inicio de sesión")
    public void esRedirigidoALogin() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            Ensure.that(LaUrlActual.delNavegador()).contains(Constantes.PATH_LOGIN)
        );
    }

}
