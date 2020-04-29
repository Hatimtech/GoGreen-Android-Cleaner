package com.gogreencleaner.main.model.PastAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class PastAssignmentResponseResult {

    @SerializedName("payment_key")
    private String orderId;
    @SerializedName("status")
    private String status;
    @SerializedName("count_who_rated")
    private String count_who_rated;
    @SerializedName("rating")
    private String rating;
    @SerializedName("car_id")
    private String carId;
    @SerializedName("brand")
    private String carBrand;
    @SerializedName("reg_no")
    private String plateNumber;
    @SerializedName("model")
    private String carModel;
    @SerializedName("services")
    private String serviceType;
    @SerializedName("color")
    private String carColor;
    @SerializedName("parking_number")
    private String parkingNumber;
    @SerializedName("apartment_number")
    private String apartmentNumber;
    @SerializedName("name")
    private String streetName;
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
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

    public String getCount_who_rated() {
        return count_who_rated;
    }

    public void setCount_who_rated(String count_who_rated) {
        this.count_who_rated = count_who_rated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
}
