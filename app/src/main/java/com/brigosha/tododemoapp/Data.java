package com.brigosha.tododemoapp;

public class Data {

    private String description;
    private Boolean isCompleted;

    public Data(String description, Boolean isCompleted) {
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
