package com.personal.ncasilla.lecheymielganado.models;

/**
 * Created by Ney Casilla on 12/1/2018.
 */

public class Group {
    private User[] users;
    private Cows[] cows;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Cows[] getCows() {
        return cows;
    }

    public void setCows(Cows[] cows) {
        this.cows = cows;
    }
}
