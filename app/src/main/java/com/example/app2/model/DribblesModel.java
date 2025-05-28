package com.example.app2.model;

public class DribblesModel {
    private int attempts;
    private int success;
    private Object past;

    public DribblesModel()
    {

    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public Object getPast() {
        return past;
    }

    public void setPast(Object past) {
        this.past = past;
    }

    @Override
    public String toString() {
        return "DribblesModel{" +
                "attempts=" + attempts +
                ", success=" + success +
                ", past=" + past +
                '}';
    }
}
