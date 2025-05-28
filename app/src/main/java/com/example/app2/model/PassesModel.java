package com.example.app2.model;

public class PassesModel
{
    private int total;
    private int key;
    private Object accuracy;

    public PassesModel()
    {

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Object accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return "PassesModel{" +
                "total=" + total +
                ", key=" + key +
                ", accuracy=" + accuracy +
                '}';
    }
}
