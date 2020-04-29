package com.gogreencleaner.main.model.UpdateAttendedStatus.REQUEST;

import com.google.gson.annotations.SerializedName;

public class UpdateAttendedStatusRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("id")
    private String id;
    @SerializedName("job_done_date")
    private String job_done_date;
    @SerializedName("attendent")
    private String attendent;
    @SerializedName("feedback")
    private String feedback;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob_done_date() {
        return job_done_date;
    }

    public void setJob_done_date(String job_done_date) {
        this.job_done_date = job_done_date;
    }

    public String getAttendent() {
        return attendent;
    }

    public void setAttendent(String attendent) {
        this.attendent = attendent;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
