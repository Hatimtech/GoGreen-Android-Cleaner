package com.gogreencleaner.main.model.PastAssignment.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastAssignmentResponse extends BaseResponse{

    @SerializedName("result")
    List<PastAssignmentResponseResult> result;

    public List<PastAssignmentResponseResult> getResult() {
        return result;
    }

    public void setResult(List<PastAssignmentResponseResult> result) {
        this.result = result;
    }
}
