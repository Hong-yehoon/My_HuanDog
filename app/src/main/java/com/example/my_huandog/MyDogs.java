package com.example.my_huandog;

public class MyDogs {

    private String dogImage;
    private String dogName;
    private String dogAge;
    private String dogWeight;
    private String dogGender;
    private String dogSort;
    private String dogSocial;
    private String dogAddr;

    public MyDogs(String dogName, String dogAge, String dogWeight, String dogGender, String dogSort, String dogSocial, String dogAddr) {
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogWeight = dogWeight;
        this.dogGender = dogGender;
        this.dogSort = dogSort;
        this.dogSocial = dogSocial;
        this.dogAddr = dogAddr;


    }

    public MyDogs (String dogName){
        this.dogName = dogName;

    }


    public String getDogImage() {
        return dogImage;
    }

    public void setDogImage(String dogImage) {
        this.dogImage = dogImage;
    }


    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogAge() {
        return dogAge;
    }

    public void setDogAge(String dogAge) {
        this.dogAge = dogAge;
    }

    public String getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(String dogWeight) {
        this.dogWeight = dogWeight;
    }

    public String getDogGender() {
        return dogGender;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public String getDogSort() {
        return dogSort;
    }

    public void setDogSort(String dogSort) {
        this.dogSort = dogSort;
    }

    public String getDogSociety() {
        return dogSocial;
    }

    public void setDogSociety(String dogSociety) {
        this.dogSocial = dogSociety;
    }

    public String getDogAddr() {
        return dogAddr;
    }

    public void setDogAddr(String dogAddr) {
        this.dogAddr = dogAddr;
    }
}
