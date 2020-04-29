package com.gogreencleaner.main.model.CurrentAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CurrentAssignmentResponseWrapper {

    @SerializedName("data")
    CurrentAssignmentResponse response;

    public CurrentAssignmentResponse getResponse() {
        return response;
    }

    public void setResponse(CurrentAssignmentResponse response) {
        this.response = response;
    }
}
