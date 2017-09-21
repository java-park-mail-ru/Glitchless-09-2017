package ru.glitchless.models;

public class UserModel {
    private String login;
    private String password;
    private String email;

    @SuppressWarnings("unused")
    public UserModel() {
    }

    public UserModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String userEmail) {
        this.email = userEmail;
        return this;
    }

}
