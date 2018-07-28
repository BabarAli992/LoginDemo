package com.example.latitude.logindemo.Model;

public class Sys {

    private int type;
    private int id;
    private double country;
    private String message;
    private double sunrise;
    private double sunset;

    public Sys(int type, int id, String message, double sunrise, double sunset, double country) {
        this.type = type;
        this.id = id;
        this.country=country;
        this.message = message;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public double getCountry() {
        return country;
    }

    public void setCountry(double country) {
        this.country = country;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }
}
