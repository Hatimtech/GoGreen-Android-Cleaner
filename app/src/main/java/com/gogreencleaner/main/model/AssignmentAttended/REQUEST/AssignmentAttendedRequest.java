package com.gogreencleaner.main.model.AssignmentAttended.REQUEST;

import com.google.gson.annotations.SerializedName;

public class AssignmentAttendedRequest {

    @SerializedName("method")
    private String method;
    @SerializedName("app_key")
    private String appKey;
    @SerializedName("cleaner_id")
    private String cleanerId;
    @SerializedName("attendent")
    private String attended;
    @SerializedName("car_id")
    private String carID;
    @SerializedName("reason")
    private String feedback;
    @SerializedName("car_type")
    private String car_type;
    @SerializedName("days")
    private String days;
    @SerializedName("team_id")
    private String teamId;
    @SerializedName("payment_key")
    private String paymentKey;
    @SerializedName("one_time_service_date")
    private String  service_date;
    @SerializedName("package_type")
    private String packageType;
    @SerializedName("expiry_date")
    private String expiry_date;
    @SerializedName("booked_package_id")
    private String booked_package_id;
    @SerializedName("purchase_date")
    private String purchase_date;
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("service_type")
    private String services_type;

    public String getServices_type() {
        return services_type;
    }

    public void setServices_type(String services_type) {
        this.services_type = services_type;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getBooked_package_id() {
        return booked_package_id;
    }

    public void setBooked_package_id(String booked_package_id) {
        this.booked_package_id = booked_package_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
