package com.personal.ncasilla.lecheymielganado.models;

/**
 * Created by Ney Casilla on 12/1/2018.
 */

public class User {
    private String email;
    private String username;
    private String password;
    private String completeName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

}
