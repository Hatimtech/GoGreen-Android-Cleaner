package com.gogreencleaner.main.model.PaymentAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class PaymentAssignmentResponseResult {


    @SerializedName("id")
    private String orderId;
    @SerializedName("payment_status")
    private String status;
    @SerializedName("net_paid")
    private String amount;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("brand")
    private String carBrand;
    @SerializedName("reg_no")
    private String plateNumber;
    @SerializedName("model")
    private String carModel;
    @SerializedName("service_type")
    private String serviceType;
    @SerializedName("color")
    private String carColor;
    @SerializedName("street_name")
    private String street_name;
    @SerializedName("apartment_number")
    private String apartmentNumber;
    @SerializedName("username")
    private String user_Name;
    @SerializedName("phone_number")
    private String user_phone;
    @SerializedName("partial_payment")
    private String partial_payment;


    public String getPartial_payment() {
        return partial_payment;
    }

    public void setPartial_payment(String partial_payment) {
        this.partial_payment = partial_payment;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    boolean expanded = false;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
