package com.gogreencleaner.main.model.PaymentCollectionStatus.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentCollectionStatusResponse extends BaseResponse {

    @SerializedName("result")
    List<PaymentCollectionStatusResponseResult> result;

    public List<PaymentCollectionStatusResponseResult> getResult() {
        return result;
    }

    public void setResult(List<PaymentCollectionStatusResponseResult> result) {
        this.result = result;
    }
}
