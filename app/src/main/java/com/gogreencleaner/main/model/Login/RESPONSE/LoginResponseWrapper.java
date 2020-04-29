package com.gogreencleaner.main.model.Login.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class LoginResponseWrapper {

    @SerializedName("data")
    LoginResponse response;

    public LoginResponse getResponse() {
        return response;
    }

    public void setResponse(LoginResponse response) {
        this.response = response;
    }
}
