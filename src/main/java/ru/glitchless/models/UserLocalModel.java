package ru.glitchless.models;

import org.mindrot.jbcrypt.BCrypt;

public class UserLocalModel {
    private final String loginOrEmail;
    private String passwordBCrypt;

    public UserLocalModel(String loginOrEmail, String password, String salt) {
        this.loginOrEmail = loginOrEmail;
        this.passwordBCrypt = BCrypt.hashpw(password, salt);
    }

    public String getLoginOrEmail(){
        return loginOrEmail;
    }

    public void setPassword(String password, String salt) {
        this.passwordBCrypt = BCrypt.hashpw(password, salt);
    }

    public boolean comparePassword(String password){
        return BCrypt.checkpw(password, passwordBCrypt);
    }

    @Override
    public int hashCode() {
        return loginOrEmail.hashCode() * passwordBCrypt.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserLocalModel &&
                loginOrEmail.equals(((UserLocalModel) obj).loginOrEmail) &&
                passwordBCrypt.equals(((UserLocalModel) obj).passwordBCrypt);
    }
}
