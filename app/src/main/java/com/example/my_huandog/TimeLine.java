package com.example.my_huandog;

public class TimeLine {
    private String dogName;
    private String walkTime;

    public TimeLine(String dogName, String walkTime){

        this.dogName = dogName;
        this.walkTime = walkTime;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }
}
