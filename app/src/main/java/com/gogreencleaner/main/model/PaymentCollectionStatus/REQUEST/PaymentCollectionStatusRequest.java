package com.gogreencleaner.main.model.PaymentCollectionStatus.REQUEST;

import com.google.gson.annotations.SerializedName;

public class PaymentCollectionStatusRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("cleaner_id")
    private String cleanerId;
    @SerializedName("id")
    private String orderId;
    @SerializedName("partial_cash")
    private String partial_payment;
    @SerializedName("user_id")
    private String user_id;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPartial_payment() {
        return partial_payment;
    }

    public void setPartial_payment(String partial_payment) {
        this.partial_payment = partial_payment;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
