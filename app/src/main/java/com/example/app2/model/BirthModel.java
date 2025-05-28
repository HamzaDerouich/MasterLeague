package com.example.app2.model;

public class BirthModel
{
    private String date;

    private String place;

    private String country;

    public BirthModel()
    {

    }

    public BirthModel(String date, String place, String country) {
        this.date = date;
        this.place = place;
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "BirthModel{" +
                "date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
