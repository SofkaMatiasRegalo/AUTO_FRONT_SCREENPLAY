package com.autofrontscreenplay.fixtures;

import java.util.concurrent.atomic.AtomicReference;

import com.autofrontscreenplay.model.RegistrationData;

public final class UsuarioRegistradoContexto {

    private static final AtomicReference<RegistrationData> ULTIMO_USUARIO_REGISTRADO = new AtomicReference<>();

    private UsuarioRegistradoContexto() {}

    public static void guardar(RegistrationData registrationData) {
        ULTIMO_USUARIO_REGISTRADO.set(registrationData);
    }

    public static RegistrationData obtener() {
        return ULTIMO_USUARIO_REGISTRADO.get();
    }

    public static RegistrationData obtenerRequerido() {
        RegistrationData registrationData = obtener();
        if (registrationData == null) {
            throw new IllegalStateException("No existe un usuario compartido desde registro. Ejecuta primero el escenario de registro.");
        }
        return registrationData;
    }

    public static void limpiar() {
        ULTIMO_USUARIO_REGISTRADO.set(null);
    }
}