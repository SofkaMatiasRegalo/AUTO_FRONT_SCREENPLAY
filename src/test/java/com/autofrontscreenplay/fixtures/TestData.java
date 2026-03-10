package com.autofrontscreenplay.fixtures;

import com.autofrontscreenplay.screenplay.model.RegistrationData;
import com.autofrontscreenplay.screenplay.model.UserCredentials;

/**
 * Catálogo centralizado de datos de prueba para F1 - Autenticación.
 */
public final class TestData {

    private TestData() {}

    // ─── Usuarios existentes ───────────────────────────────────────────────────
    public static final UserCredentials USER_CREDENTIALS =
            UserCredentials.of("user@test.com", "UserPass1!");

    public static final UserCredentials ADMIN_CREDENTIALS =
            UserCredentials.of("admin@test.com", "AdminPass1!");

    public static final UserCredentials INVALID_CREDENTIALS =
            UserCredentials.of("user@test.com", "wrongpassword");

    // ─── Datos de registro duplicado (ya existe en el sistema) ────────────────
    public static final RegistrationData DUPLICATE_USER =
            RegistrationData.of("user", "user@test.com", "Password1!", "Password1!");

    // ─── Datos de registro con contraseñas que no coinciden ───────────────────
    public static final RegistrationData MISMATCHED_PASSWORDS =
            RegistrationData.of("mismatchuser", "mismatch@test.com", "Password1!", "OtherPass2@");

    // ─── Generación dinámica de usuarios únicos para evitar conflictos ────────
    public static RegistrationData newUniqueUser() {
        long ts = System.currentTimeMillis();
        return RegistrationData.of(
                "testuser_" + ts,
                "testuser_" + ts + "@test.com",
                "NewUser1!",
                "NewUser1!"
        );
    }
}
