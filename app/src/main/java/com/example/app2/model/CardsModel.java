package com.example.app2.model;

public class CardsModel
{
    private int yellow;
    private int yellowred;
    private int red;

    public CardsModel()
    {

    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getYellowred() {
        return yellowred;
    }

    public void setYellowred(int yellowred) {
        this.yellowred = yellowred;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    @Override
    public String toString() {
        return "CardsModel{" +
                "yellow=" + yellow +
                ", yellowred=" + yellowred +
                ", red=" + red +
                '}';
    }
}
