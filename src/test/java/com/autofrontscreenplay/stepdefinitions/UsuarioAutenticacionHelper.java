package com.autofrontscreenplay.stepdefinitions;

import com.autofrontscreenplay.fixtures.TestData;
import com.autofrontscreenplay.fixtures.UsuarioRegistradoContexto;
import com.autofrontscreenplay.hooks.NavegarsA;
import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.tasks.RegistrarNuevoUsuario;
import net.serenitybdd.screenplay.actors.OnStage;

public final class UsuarioAutenticacionHelper {

    private UsuarioAutenticacionHelper() {}

    public static RegistrationData crearUsuarioUnicoYRegistrarlo() {
        RegistrationData registrationData = TestData.newUniqueUser();
        OnStage.theActorInTheSpotlight().attemptsTo(
            NavegarsA.laPaginaDeRegistro(),
            RegistrarNuevoUsuario.conDatos(registrationData)
        );
        UsuarioRegistradoContexto.guardar(registrationData);
        return registrationData;
    }
}