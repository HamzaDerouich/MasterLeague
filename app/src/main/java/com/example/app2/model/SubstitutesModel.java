package com.example.app2.model;

public class SubstitutesModel {

    private int in;
    private int out;
    private int bench;

    public SubstitutesModel()
    {

    }

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public int getBench() {
        return bench;
    }

    public void setBench(int bench) {
        this.bench = bench;
    }

    @Override
    public String toString() {
        return "SubstitutesModel{" +
                "in=" + in +
                ", out=" + out +
                ", bench=" + bench +
                '}';
    }
}
