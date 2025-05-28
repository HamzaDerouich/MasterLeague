package com.example.app2.model;

public class FoulsModel {
    private int drawn;
    private int committed;

    public FoulsModel()
    {

    }

    public int getDrawn() {
        return drawn;
    }

    public void setDrawn(int drawn) {
        this.drawn = drawn;
    }

    public int getCommitted() {
        return committed;
    }

    public void setCommitted(int committed) {
        this.committed = committed;
    }

    @Override
    public String toString() {
        return "FoulsModel{" +
                "drawn=" + drawn +
                ", committed=" + committed +
                '}';
    }
}
