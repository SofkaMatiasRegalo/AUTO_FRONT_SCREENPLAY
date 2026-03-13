package com.autofrontscreenplay.fixtures;

import com.autofrontscreenplay.model.RegistrationData;
import com.autofrontscreenplay.util.Constantes;

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

}
