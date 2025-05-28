package com.example.app2.model;

public class ShotsModel {
    private int total;

    private int on;

    public ShotsModel()
    {

    }

    // Getters and Setters


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    @Override
    public String toString() {
        return "ShotsModel{" +
                "total=" + total +
                ", on=" + on +
                '}';
    }
}
