package com.gogreencleaner.main.model.PaymentCollectionStatus.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class PaymentCollectionStatusResponseWrapper {



    @SerializedName("data")
    PaymentCollectionStatusResponse response;

    public PaymentCollectionStatusResponse getResponse() {
        return response;
    }

    public void setResponse(PaymentCollectionStatusResponse response) {
        this.response = response;
    }
}
