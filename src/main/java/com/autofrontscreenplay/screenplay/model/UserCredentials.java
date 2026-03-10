package com.autofrontscreenplay.screenplay.model;

/**
 * Modelo de datos: credenciales de login.
 * Agrupa email y contraseña como unidad de datos de prueba.
 */
public class UserCredentials {

    private final String email;
    private final String password;

    private UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials of(String email, String password) {
        return new UserCredentials(email, password);
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
