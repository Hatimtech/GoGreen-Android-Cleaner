package com.gogreencleaner.main.model.AssignmentAttended.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentAttendedResponse extends BaseResponse {

    @SerializedName("result")
    List<AssignmentAttendedResponseResult> result;

    public List<AssignmentAttendedResponseResult> getResult() {
        return result;
    }

    public void setResult(List<AssignmentAttendedResponseResult> result) {
        this.result = result;
    }
}
