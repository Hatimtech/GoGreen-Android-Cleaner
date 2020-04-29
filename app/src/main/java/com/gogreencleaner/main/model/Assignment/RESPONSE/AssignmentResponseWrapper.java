package com.gogreencleaner.main.model.Assignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AssignmentResponseWrapper {



    @SerializedName("data")
    AssignmentResponse response;

    public AssignmentResponse getResponse() {
        return response;
    }

    public void setResponse(AssignmentResponse response) {
        this.response = response;
    }
}
