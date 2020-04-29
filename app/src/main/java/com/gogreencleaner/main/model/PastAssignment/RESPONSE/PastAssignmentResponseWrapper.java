package com.gogreencleaner.main.model.PastAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class PastAssignmentResponseWrapper {

    @SerializedName("data")
    PastAssignmentResponse response;

    public PastAssignmentResponse getResponse() {
        return response;
    }

    public void setResponse(PastAssignmentResponse response) {
        this.response = response;
    }
}
