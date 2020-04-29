package com.gogreencleaner.main.model.PaymentAssignment.REQUEST;

import com.google.gson.annotations.SerializedName;

public class PaymentAssignmentRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("cleaner_id")
    private String cleanerId;

    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
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

}
