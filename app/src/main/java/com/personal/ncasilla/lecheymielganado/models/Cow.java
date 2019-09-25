package com.personal.ncasilla.lecheymielganado.models;

import android.graphics.Bitmap;

/**
 * Created by Ney Casilla on 12/1/2018.
 */

public class Cow {
    private int id;
    private String nameCode;
    private String gender;
    private String age;
    private String color;
    private String image;
    private String characteristic;
    private Cow[] calfs;

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
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public Cow[] getCalfs() {
        return calfs;
    }

    public void setCalfs(Cow[] calfs) {
        this.calfs = calfs;
    }
}
