package com.gogreencleaner.main.model.Assignment.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentResponse extends BaseResponse {

    @SerializedName("result")
    List<AssignmentResponseResult> result;

    public List<AssignmentResponseResult> getResult() {
        return result;
    }

    public void setResult(List<AssignmentResponseResult> result) {
        this.result = result;
    }
}
