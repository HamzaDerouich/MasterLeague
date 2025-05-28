package com.example.app2.model;

public class TacklesModel
{
    private int total;
    private int blocks;
    private int interceptions;

    public TacklesModel()
    {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public int getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(int interceptions) {
        this.interceptions = interceptions;
    }

    @Override
    public String toString() {
        return "TacklesModel{" +
                "total=" + total +
                ", blocks=" + blocks +
                ", interceptions=" + interceptions +
                '}';
    }
}
