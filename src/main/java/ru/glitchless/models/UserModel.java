package ru.glitchless.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    @JsonProperty("login")
    private String loginOrEmail;
    private String password;
    private String email;

    @SuppressWarnings("unused")
    public UserModel() {
    }

    public UserModel(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }

    public String getLoginOrEmail() {
        return loginOrEmail;
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
