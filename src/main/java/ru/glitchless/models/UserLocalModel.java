package ru.glitchless.models;

import org.mindrot.jbcrypt.BCrypt;

public class UserLocalModel {
    private final String login;
    private String passwordBCrypt;
    private String email;

    public UserLocalModel(String login, String password, String salt) {
        this.login = login;
        setPassword(password, salt);
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password, String salt) {
        this.passwordBCrypt = BCrypt.hashpw(password, salt);
    }

    public boolean comparePassword(String password) {
        return BCrypt.checkpw(password, passwordBCrypt);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return login.hashCode() * passwordBCrypt.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserLocalModel
                && login.equals(((UserLocalModel) obj).login)
                && passwordBCrypt.equals(((UserLocalModel) obj).passwordBCrypt);
    }
}
