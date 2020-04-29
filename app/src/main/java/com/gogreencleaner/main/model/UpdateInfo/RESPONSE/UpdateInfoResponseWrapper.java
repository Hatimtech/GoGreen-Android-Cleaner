package com.gogreencleaner.main.model.UpdateInfo.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpdateInfoResponseWrapper {

    @SerializedName("data")
    UpdateInfoResponse response;

    public UpdateInfoResponse getResponse() {
        return response;
    }

    public void setResponse(UpdateInfoResponse response) {
        this.response = response;
    }
}
