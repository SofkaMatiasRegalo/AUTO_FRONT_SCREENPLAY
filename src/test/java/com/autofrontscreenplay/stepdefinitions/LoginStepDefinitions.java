package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.UsuarioRegistradoContexto;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.model.UserCredentials;
import com.autofrontscreenplay.questions.ElNombreDeUsuarioEnNavBar;
import com.autofrontscreenplay.tasks.IniciarSesion;
import com.autofrontscreenplay.util.Constantes;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class LoginStepDefinitions {

    private RegistrationData registrationData;

    @Given("que un usuario no tiene sesión activa")
    public void unUsuarioSinSesionActiva() {
        registrationData = UsuarioRegistradoContexto.obtenerRequerido();
        OnStage.theActorCalled(Constantes.ACTOR_VISITANTE)
                .attemptsTo(NavegarsA.laPaginaDeLogin());
    }

    @When("inicia sesión con credenciales válidas")
    public void iniciaSesionConDatos() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(
                    IniciarSesion.conCredenciales(UserCredentials.of(registrationData.getEmail(), registrationData.getPassword()))
                );
    }

    @And("el nombre del usuario es visible en la barra de navegación")
    public void nombreDeUsuarioVisible() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).contains(registrationData.getUsername())
        );
    }
}
