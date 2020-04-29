package com.gogreencleaner.main.model.CurrentAssignment.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class CurrentAssignmentResponseResult {

    @SerializedName("orders_id")
    private String orderId;
    @SerializedName("car_id")
    private String carId;
    @SerializedName("brand")
    private String carBrand;
    @SerializedName("reg_no")
    private String plateNumber;
    @SerializedName("model")
    private String carModel;
    @SerializedName("services")
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
    @SerializedName("team_id")
    private String teamId;
    @SerializedName("payment_key")
    private String paymentKey;
    @SerializedName("one_time_service_date")
    private String service_date;
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
    @SerializedName("user_name")
    private String user_Name;
    @SerializedName("city")
    private String user_city;
    @SerializedName("street")
    private String user_street;
    @SerializedName("locality")
    private String user_locality;
    @SerializedName("phone_number")
    private String user_phone;


    public String getCar_type() {
        return car_type;
    }

    public String getDays() {
        return days;
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

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_street() {
        return user_street;
    }

    public void setUser_street(String user_street) {
        this.user_street = user_street;
    }

    public String getUser_locality() {
        return user_locality;
    }

    public void setUser_locality(String user_locality) {
        this.user_locality = user_locality;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    boolean expanded = true;

        public boolean isExpanded () {
        return expanded;
    }

        public void setExpanded ( boolean expanded){
        this.expanded = expanded;
    }

        public void setCar_type (String car_type){
        this.car_type = car_type;
    }

        public void setDays (String days){
        this.days = days;
    }

        public void setService_date (String service_date){
        this.service_date = service_date;
    }

        public String getOrderId () {
        return orderId;
    }

        public void setOrderId (String orderId){
        this.orderId = orderId;
    }

        public String getCarId () {
        return carId;
    }

        public void setCarId (String carId){
        this.carId = carId;
    }

        public String getCarBrand () {
        return carBrand;
    }

        public void setCarBrand (String carBrand){
        this.carBrand = carBrand;
    }

        public String getPlateNumber () {
        return plateNumber;
    }

        public void setPlateNumber (String plateNumber){
        this.plateNumber = plateNumber;
    }

        public String getCarModel () {
        return carModel;
    }

        public void setCarModel (String carModel){
        this.carModel = carModel;
    }

        public String getService_type () {
        return service_type;
    }

        public void setService_type (String service_type){
        this.service_type = service_type;
    }

        public String getCarColor () {
        return carColor;
    }

        public void setCarColor (String carColor){
        this.carColor = carColor;
    }

        public String getParkingNumber () {
        return parkingNumber;
    }

        public void setParkingNumber (String parkingNumber){
        this.parkingNumber = parkingNumber;
    }

        public String getApartmentNumber () {
        return apartmentNumber;
    }

        public void setApartmentNumber (String apartmentNumber){
        this.apartmentNumber = apartmentNumber;
    }
    }
