package com.example.app2.model;

public class GoalsModel {
    private int total;
    private int conceded;
    private int assists;
    private Object saves;

    public GoalsModel()
    {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getConceded() {
        return conceded;
    }

    public void setConceded(int conceded) {
        this.conceded = conceded;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public Object getSaves() {
        return saves;
    }

    public void setSaves(Object saves) {
        this.saves = saves;
    }

    @Override
    public String toString() {
        return "GoalsModel{" +
                "total=" + total +
                ", conceded=" + conceded +
                ", assists=" + assists +
                ", saves=" + saves +
                '}';
    }
}
