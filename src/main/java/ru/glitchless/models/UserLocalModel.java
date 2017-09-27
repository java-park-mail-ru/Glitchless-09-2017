package ru.glitchless.models;

public class UserLocalModel {
    private final String login;
    private String passwordBCrypt;
    private String email;

    public UserLocalModel(String login, String password) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordBCrypt() {
        return passwordBCrypt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
