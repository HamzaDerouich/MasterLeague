package com.example.app2.model;

public class DuelsModel
{
    private int total;
    private int won;

    public DuelsModel()
    {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    @Override
    public String toString() {
        return "DuelsModel{" +
                "total=" + total +
                ", won=" + won +
                '}';
    }
}
