package com.gogreencleaner.main.model.PaymentAssignment.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentAssignmentResponse extends BaseResponse{

    @SerializedName("result")
    List<PaymentAssignmentResponseResult> result;

    public List<PaymentAssignmentResponseResult> getResult() {
        return result;
    }

    public void setResult(List<PaymentAssignmentResponseResult> result) {
        this.result = result;
    }
}
