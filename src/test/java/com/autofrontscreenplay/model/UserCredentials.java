package com.autofrontscreenplay.model;

/**
 * Value object: UserCredentials
 * Encapsula las credenciales de autenticación (email + password).
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
