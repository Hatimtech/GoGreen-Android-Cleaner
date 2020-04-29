package com.gogreencleaner.main.model.FgPassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class FgPasswordResponseWrapper {

    @SerializedName("data")
    FgPasswordResponse response;

    public FgPasswordResponse getResponse() {
        return response;
    }

    public void setResponse(FgPasswordResponse response) {
        this.response = response;
    }
}
