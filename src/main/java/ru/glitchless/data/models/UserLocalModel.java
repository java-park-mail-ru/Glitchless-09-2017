package ru.glitchless.data.models;

public class UserLocalModel {
    private int localId;
    private final String login;
    private String passwordBCrypt;
    private String email;

    public UserLocalModel(String login, String password) {
        this.login = login;
        this.passwordBCrypt = password;
    }

    public UserLocalModel(int localId, String login, String password, String email) {
        this.login = login;
        this.passwordBCrypt = password;
        this.email = email;
        this.localId = localId;
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
