package com.gogreencleaner.main.model.UpdateAttendedStatus.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class UpdateAttendedStatusResponseWrapper {



    @SerializedName("data")
    UpdateAttendedStatusResponse response;

    public UpdateAttendedStatusResponse getResponse() {
        return response;
    }

    public void setResponse(UpdateAttendedStatusResponse response) {
        this.response = response;
    }
}
