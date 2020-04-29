package com.gogreencleaner.main.model.FgPassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class FgPasswordResponseResult {


    @SerializedName("id")
    private String UserID;
    @SerializedName("name")
    private String name;
    @SerializedName("phone_number")
    private String phoneNumber;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
