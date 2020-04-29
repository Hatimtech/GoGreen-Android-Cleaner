package com.gogreencleaner.main.model.Assignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class AssignmentResponseResult {

    @SerializedName("order_id")
    private String orderId;
    @SerializedName("car_id")
    private String carId;
    @SerializedName("brand")
    private String carBrand;
    @SerializedName("reg_no")
    private String plateNumber;
    @SerializedName("model")
    private String carModel;
    @SerializedName("service_type")
    private String service_type;
    @SerializedName("color")
    private String carColor;
    @SerializedName("parking_number")
    private String parkingNumber;
    @SerializedName("apartment_number")
    private String apartmentNumber;
    @SerializedName("car_type")
    private String car_type;
    @SerializedName("days")
    private String days;
    @SerializedName("one_time_service_date")
    private String service_date;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_phone_number")
    private String user_phone_number;
    @SerializedName("user_city")
    private String user_city;
    @SerializedName("user_locality")
    private String user_locality;
    @SerializedName("user_street")
    private String user_street;
    @SerializedName("package_type")
    private String packageType;

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getParkingNumber() {
        return parkingNumber;
    }

    public void setParkingNumber(String parkingNumber) {
        this.parkingNumber = parkingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
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

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_locality() {
        return user_locality;
    }

    public void setUser_locality(String user_locality) {
        this.user_locality = user_locality;
    }

    public String getUser_street() {
        return user_street;
    }

    public void setUser_street(String user_street) {
        this.user_street = user_street;
    }
}
