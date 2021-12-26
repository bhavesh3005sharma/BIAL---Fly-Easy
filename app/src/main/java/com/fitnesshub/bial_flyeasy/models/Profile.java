package com.fitnesshub.bial_flyeasy.models;

import com.fitnesshub.bial_flyeasy.models.CheckList;

import java.util.ArrayList;

public class Profile {
    String name;
    String pictureUrl;
    int age;
    String gender;
    String address;
    String airport;
    String phoneNumber;
    String aadharCard;
    ArrayList<CheckList> checkListArrayList;

    public Profile(String name, String pictureUrl, int age, String gender, String address, String airport, String phoneNumber, String aadharCard, ArrayList<CheckList> checkListArrayList) {
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.airport = airport;
        this.phoneNumber = phoneNumber;
        this.aadharCard = aadharCard;
        this.checkListArrayList = checkListArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAadharCard() {
        return aadharCard;
    }

    public void setAadharCard(String aadharCard) {
        this.aadharCard = aadharCard;
    }

    public ArrayList<CheckList> getCheckListArrayList() {
        return checkListArrayList;
    }

    public void setCheckListArrayList(ArrayList<CheckList> checkListArrayList) {
        this.checkListArrayList = checkListArrayList;
    }
}
