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

    @SuppressWarnings("unused")
    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

}
