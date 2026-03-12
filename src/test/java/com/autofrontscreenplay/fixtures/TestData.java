package com.autofrontscreenplay.fixtures;

import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.model.UserCredentials;
import com.autofrontscreenplay.util.Constantes;

/**
 * TestData: catálogo centralizado de datos de prueba para F1 — Autenticación.
 *
 * - Credenciales estáticas: referencian usuarios pre-existentes en el entorno de pruebas.
 * - newUniqueUser(): genera datos únicos con timestamp para evitar colisiones en F1.1.
 */
public final class TestData {

    private TestData() {}

        public static RegistrationData newUniqueUser() {
        long ts = System.currentTimeMillis();
        return RegistrationData.of(
                Constantes.REGISTRO_NUEVO_USUARIO_PREFIX + ts,
                Constantes.REGISTRO_NUEVO_USUARIO_PREFIX + ts + Constantes.REGISTRO_NUEVO_USUARIO_DOMINIO,
                Constantes.REGISTRO_NUEVO_USUARIO_PASSWORD,
                Constantes.REGISTRO_NUEVO_USUARIO_PASSWORD
        );
    }

    public static final UserCredentials REGISTERED_USER_CREDENTIALS =
            UserCredentials.of(Constantes.REGISTERED_EMAIL, Constantes.REGISTERED_PASSWORD);

    public static final RegistrationData REGISTERED_USER_DATA =
            RegistrationData.of(
                Constantes.REGISTERED_USERNAME,
                Constantes.REGISTERED_EMAIL,
                Constantes.REGISTERED_PASSWORD,
                Constantes.REGISTERED_PASSWORD
            );
    
}
