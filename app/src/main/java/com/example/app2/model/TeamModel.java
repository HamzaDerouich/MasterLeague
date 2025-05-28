package com.example.app2.model;

public class TeamModel {

    private int id;

    private String name;
    private String logo;

    public TeamModel()
    {

    }

    // Getters and Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    // ToString

    @Override
    public String toString() {
        return "TeamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
