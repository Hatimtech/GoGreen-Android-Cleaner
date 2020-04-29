package com.gogreencleaner.main.model.UpdatePassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpdatePasswordResponseWrapper {



    @SerializedName("data")
    UpdatePasswordResponse response;

    public UpdatePasswordResponse getResponse() {
        return response;
    }

    public void setResponse(UpdatePasswordResponse response) {
        this.response = response;
    }
}
