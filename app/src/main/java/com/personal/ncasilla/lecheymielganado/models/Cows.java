package com.personal.ncasilla.lecheymielganado.models;

/**
 * Created by Ney Casilla on 12/1/2018.
 */

public class Cows {
    private int id;
    private String nameCode;
    private String gender;
    private String age;
    private String Color;
    private String Characteristic;
    private Cows[] calfs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getCharacteristic() {
        return Characteristic;
    }

    public void setCharacteristic(String characteristic) {
        Characteristic = characteristic;
    }

    public Cows[] getCalfs() {
        return calfs;
    }

    public void setCalfs(Cows[] calfs) {
        this.calfs = calfs;
    }
}
