package ru.glitchless.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    @JsonProperty("login")
    private String login;
    private String password;
    @JsonProperty("email")
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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String userEmail) {
        this.email = userEmail;
        return this;
    }

}
