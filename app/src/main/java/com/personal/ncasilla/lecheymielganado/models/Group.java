package com.personal.ncasilla.lecheymielganado.models;

/**
 * Created by Ney Casilla on 12/1/2018.
 */

public class Group {

    private String name;
    private User[] users;
    private Cow[] cows;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Cow[] getCows() {
        return cows;
    }

    public void setCows(Cow[] cows) {
        this.cows = cows;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
