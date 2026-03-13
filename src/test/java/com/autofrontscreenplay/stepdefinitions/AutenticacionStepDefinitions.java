package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.TestData;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.model.UserCredentials;
import com.autofrontscreenplay.questions.ElNombreDeUsuarioEnNavBar;
import com.autofrontscreenplay.questions.LaUrlActual;
import com.autofrontscreenplay.tasks.CerrarSesion;
import com.autofrontscreenplay.tasks.IniciarSesion;
import com.autofrontscreenplay.tasks.RegistrarNuevoUsuario;
import com.autofrontscreenplay.util.Constantes;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class AutenticacionStepDefinitions {

    private RegistrationData registrationData;

    @Given("que un usuario accede por primera vez al sistema")
    public void usuarioAccedePorPrimeraVez() {
        OnStage.theActorCalled(Constantes.ACTOR_VISITANTE)
                .attemptsTo(NavegarsA.laPaginaDeRegistro());
    }

    @When("se registra con datos de usuario válidos y únicos")
    public void seRegistraConDatosValidosYUnicos() {
        registrationData = TestData.newUniqueUser();
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavegarsA.laPaginaDeRegistro(),
                RegistrarNuevoUsuario.conDatos(registrationData)
        );
        OnStage.theActorInTheSpotlight().remember(Constantes.CONTEXTO_REGISTRATION_DATA, registrationData);
    }

    @Then("es redirigido al listado de tickets")
    public void esRedirigidoAlListadoDeTickets() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(LaUrlActual.delNavegador()).contains(Constantes.PATH_TICKETS)
        );
    }

    @And("su nombre de usuario es visible en la barra de navegación")
    public void suNombreDeUsuarioEsVisibleEnLaBarraDeNavegacion() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).contains(registrationData.getUsername())
        );
    }

    @When("cierra su sesión desde la barra de navegación")
    public void cierraSuSesionDesdeLaBarraDeNavegacion() {
        OnStage.theActorInTheSpotlight().attemptsTo(CerrarSesion.desdeNavBar());
    }

    @Then("es redirigido a la pantalla de inicio de sesión")
    public void esRedirigidoALaPantallaDeInicioDeSesion() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(LaUrlActual.delNavegador()).contains(Constantes.PATH_LOGIN)
        );
    }

    @And("que un usuario no tiene sesión activa")
    public void queUnUsuarioNoTieneSesionActiva() {
        registrationData = OnStage.theActorInTheSpotlight().recall(Constantes.CONTEXTO_REGISTRATION_DATA);
        if (registrationData == null) {
            throw new IllegalStateException("No hay datos de registro en memoria del actor para este escenario.");
        }
        OnStage.theActorInTheSpotlight().attemptsTo(NavegarsA.laPaginaDeLogin());
    }

    @When("inicia sesión con credenciales válidas")
    public void iniciaSesionConCredencialesValidas() {
        RegistrationData data = registrationData != null
                ? registrationData
                : OnStage.theActorInTheSpotlight().recall(Constantes.CONTEXTO_REGISTRATION_DATA);

        if (data == null) {
            throw new IllegalStateException("No hay credenciales en memoria del actor para iniciar sesión.");
        }

        OnStage.theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(UserCredentials.of(data.getEmail(), data.getPassword()))
        );
    }

    @And("el nombre del usuario es visible en la barra de navegación")
    public void elNombreDelUsuarioEsVisibleEnLaBarraDeNavegacion() {
        RegistrationData data = registrationData != null
                ? registrationData
                : OnStage.theActorInTheSpotlight().recall(Constantes.CONTEXTO_REGISTRATION_DATA);

        if (data == null) {
            throw new IllegalStateException("No hay datos de usuario en memoria para validar la navegación.");
        }

        OnStage.theActorInTheSpotlight().attemptsTo(
                Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).contains(data.getUsername())
        );
    }
}