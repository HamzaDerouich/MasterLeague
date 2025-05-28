package com.example.app2.model;

public class GamesModel {
    private int appearences;
    private int lineups;
    private int minutes;
    private Integer number;

    private String position;
    private String rating;
    private boolean captain;

    public GamesModel()
    {

    }

    // Getters and Setters


    public int getAppearences() {
        return appearences;
    }

    public void setAppearences(int appearences) {
        this.appearences = appearences;
    }

    public int getLineups() {
        return lineups;
    }

    public void setLineups(int lineups) {
        this.lineups = lineups;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    @Override
    public String toString() {
        return "GamesModel{" +
                "appearences=" + appearences +
                ", lineups=" + lineups +
                ", minutes=" + minutes +
                ", number=" + number +
                ", position='" + position + '\'' +
                ", rating='" + rating + '\'' +
                ", captain=" + captain +
                '}';
    }
}
