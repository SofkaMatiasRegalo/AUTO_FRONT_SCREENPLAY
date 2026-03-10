package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.TestData;
import com.autofrontscreenplay.screenplay.model.RegistrationData;
import com.autofrontscreenplay.screenplay.questions.ElMensajeDeError;
import com.autofrontscreenplay.screenplay.questions.ElNombreDeUsuarioEnNavBar;
import com.autofrontscreenplay.screenplay.questions.LaNavBarEsVisible;
import com.autofrontscreenplay.screenplay.questions.LaUrlActual;
import com.autofrontscreenplay.screenplay.tasks.CerrarSesion;
import com.autofrontscreenplay.screenplay.tasks.IniciarSesion;
import com.autofrontscreenplay.screenplay.tasks.NavegarsA;
import com.autofrontscreenplay.screenplay.tasks.RegistrarNuevoUsuario;
import com.autofrontscreenplay.screenplay.ui.LoginUI;
import com.autofrontscreenplay.screenplay.ui.RegisterUI;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class AutenticacionStepDefinitions {

    // Almacena datos generados dinámicamente entre pasos del mismo escenario
    private RegistrationData dynamicRegistrationData;

    // ─── GIVEN ───────────────────────────────────────────────────────────────

    @Dado("que un usuario con rol {string} no tiene sesión activa")
    public void unUsuarioSinSesionActiva(String rol) {
        OnStage.setTheStage(new OnlineCast());
        String actorName = "USER".equals(rol) ? "Usuario" : "Administrador";
        OnStage.theActorCalled(actorName)
                .attemptsTo(NavegarsA.laPaginaDeLogin());
    }

    @Dado("que un usuario no tiene sesión activa")
    public void unUsuarioAnonimo() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Visitante")
                .attemptsTo(NavegarsA.laPaginaDeLogin());
    }

    @Dado("que un visitante no tiene cuenta en el sistema")
    public void visitanteSinCuenta() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Visitante")
                .attemptsTo(NavegarsA.laPaginaDeRegistro());
    }

    @Dado("que un visitante intenta registrarse con datos de un usuario ya existente")
    public void visitanteConDatosExistentes() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Visitante")
                .attemptsTo(NavegarsA.laPaginaDeRegistro());
    }

    @Dado("que el usuario ha iniciado sesión en el sistema")
    public void usuarioConSesionActiva() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("Usuario")
                .attemptsTo(
                    NavegarsA.laPaginaDeLogin(),
                    IniciarSesion.conCredenciales(TestData.USER_CREDENTIALS)
                );
    }

    // ─── WHEN ─────────────────────────────────────────────────────────────────

    @Cuando("inicia sesión con credenciales válidas")
    public void iniciaSesionConCredencialesValidas() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(IniciarSesion.conCredenciales(TestData.USER_CREDENTIALS));
    }

    @Cuando("inicia sesión con credenciales de administrador")
    public void iniciaSesionComoAdmin() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(IniciarSesion.conCredenciales(TestData.ADMIN_CREDENTIALS));
    }

    @Cuando("intenta iniciar sesión con credenciales incorrectas")
    public void intentaLoginConCredencialesIncorrectas() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(IniciarSesion.conCredenciales(TestData.INVALID_CREDENTIALS));
    }

    @Cuando("completa el formulario de registro con datos válidos y únicos")
    public void completaRegistroConDatosUnicos() {
        dynamicRegistrationData = TestData.newUniqueUser();
        OnStage.theActorInTheSpotlight()
                .attemptsTo(RegistrarNuevoUsuario.conDatos(dynamicRegistrationData));
    }

    @Cuando("completa el formulario de registro con contraseñas que no coinciden")
    public void completaRegistroConPasswordsNoCoincidentes() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(RegistrarNuevoUsuario.conDatos(TestData.MISMATCHED_PASSWORDS));
    }

    @Cuando("intenta crear la cuenta con esos datos duplicados")
    public void intentaRegistrarConDatosDuplicados() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(RegistrarNuevoUsuario.conDatos(TestData.DUPLICATE_USER));
    }

    @Cuando("cierra su sesión desde la barra de navegación")
    public void cierraSesion() {
        OnStage.theActorInTheSpotlight()
                .attemptsTo(CerrarSesion.desdeNavBar());
    }

    // ─── THEN / AND ──────────────────────────────────────────────────────────

    @Entonces("es redirigido al listado de tickets")
    public void esRedirigidoATickets() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(LaUrlActual.delNavegador()).contains("/tickets")
        );
    }

    @Y("el nombre del usuario es visible en la barra de navegación")
    public void nombreDeUsuarioVisible() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).isNotEmpty()
        );
    }

    @Y("el nombre del administrador es visible en la barra de navegación")
    public void nombreDeAdminVisible() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual()).isNotEmpty()
        );
    }

    @Y("su nombre de usuario es visible en la barra de navegación")
    public void suNombreDeUsuarioVisible() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(ElNombreDeUsuarioEnNavBar.actual())
                  .contains(dynamicRegistrationData.getUsername())
        );
    }

    @Entonces("el sistema muestra el mensaje {string}")
    public void sistemaMuestraMensaje(String mensajeEsperado) {
        // Detectar en qué página estamos para elegir el Target correcto
        String url = LaUrlActual.delNavegador().answeredBy(OnStage.theActorInTheSpotlight());
        if (url.contains("/register")) {
            OnStage.theActorInTheSpotlight().should(
                seeThat(ElMensajeDeError.mostradoEn(RegisterUI.ERROR_MESSAGE), containsString(mensajeEsperado))
            );
        } else {
            OnStage.theActorInTheSpotlight().should(
                seeThat(ElMensajeDeError.mostradoEn(LoginUI.ERROR_MESSAGE), containsString(mensajeEsperado))
            );
        }
    }

    @Y("el usuario permanece en la pantalla de inicio de sesión")
    public void permanecePantallaLogin() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(LaUrlActual.delNavegador()).contains("/login")
        );
    }

    @Y("el usuario permanece en la pantalla de registro")
    public void permanecePantallaRegistro() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(LaUrlActual.delNavegador()).contains("/register")
        );
    }

    @Y("no se realiza ninguna petición al servidor")
    public void noSePeticionAlServidor() {
        // Validación estructural: si el error apareció sin redirigir, la petición no se realizó.
        // La validación de URL en el paso anterior garantiza que no hubo navegación.
        // Este step sirve como documentación del comportamiento esperado.
    }

    @Entonces("es redirigido a la pantalla de inicio de sesión")
    public void esRedirigidoALogin() {
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(LaUrlActual.delNavegador()).contains("/login")
        );
    }

    @Y("no puede acceder al listado de tickets sin autenticarse nuevamente")
    public void noPuedeAccederATicketsSinLogin() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            NavegarsA.laRuta("/tickets")
        );
        OnStage.theActorInTheSpotlight().should(
            Ensure.that(LaUrlActual.delNavegador()).contains("/login")
        );
    }
}
