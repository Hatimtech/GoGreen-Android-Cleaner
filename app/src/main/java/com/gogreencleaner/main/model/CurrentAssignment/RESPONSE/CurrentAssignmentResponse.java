package com.gogreencleaner.main.model.CurrentAssignment.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentAssignmentResponse extends BaseResponse{

    @SerializedName("result")
    List<CurrentAssignmentResponseResult> result;

    public List<CurrentAssignmentResponseResult> getResult() {
        return result;
    }

    public void setResult(List<CurrentAssignmentResponseResult> result) {
        this.result = result;
    }
}
