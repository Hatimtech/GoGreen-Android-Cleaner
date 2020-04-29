package com.gogreencleaner.main.model.AssignmentAttended.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AssignmentAttendedResponseWrapper {



    @SerializedName("data")
    AssignmentAttendedResponse response;

    public AssignmentAttendedResponse getResponse() {
        return response;
    }

    public void setResponse(AssignmentAttendedResponse response) {
        this.response = response;
    }
}
