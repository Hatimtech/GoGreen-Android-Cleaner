package com.gogreencleaner.main.model.PaymentAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class PaymentAssignmentResponseWrapper {

    @SerializedName("data")
    PaymentAssignmentResponse response;

    public PaymentAssignmentResponse getResponse() {
        return response;
    }

    public void setResponse(PaymentAssignmentResponse response) {
        this.response = response;
    }
}
