package com.example.app2.model;

public class PenaltyModel
{
    private Object won;
    private Object commited;
    private int scored;
    private int missed;
    private Object saved;

    public PenaltyModel()
    {

    }

    public Object getWon() {
        return won;
    }

    public void setWon(Object won) {
        this.won = won;
    }

    public Object getCommited() {
        return commited;
    }

    public void setCommited(Object commited) {
        this.commited = commited;
    }

    public int getScored() {
        return scored;
    }

    public void setScored(int scored) {
        this.scored = scored;
    }

    public int getMissed() {
        return missed;
    }

    public void setMissed(int missed) {
        this.missed = missed;
    }

    public Object getSaved() {
        return saved;
    }

    public void setSaved(Object saved) {
        this.saved = saved;
    }

    @Override
    public String toString() {
        return "PenaltyModel{" +
                "won=" + won +
                ", commited=" + commited +
                ", scored=" + scored +
                ", missed=" + missed +
                ", saved=" + saved +
                '}';
    }
}
