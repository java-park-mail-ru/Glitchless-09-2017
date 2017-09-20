package ru.glitchless.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    @JsonProperty("login")
    private String loginOrEmail;
    private String password;

    public UserModel() {
    }


    public UserModel(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
