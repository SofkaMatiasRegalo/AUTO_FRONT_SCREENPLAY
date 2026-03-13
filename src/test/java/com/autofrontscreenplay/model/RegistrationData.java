package com.autofrontscreenplay.model;

public class RegistrationData {

    private final String username;
    private final String email;
    private final String password;
    private final String confirmPassword;

    private RegistrationData(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public static RegistrationData of(String username, String email, String password, String confirmPassword) {
        return new RegistrationData(username, email, password, confirmPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
