package com.gogreencleaner.main.model.DashBoard.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class BasicInfo {

    @SerializedName("count_who_rated")
    private String count_who_rated;
    @SerializedName("rating")
    private String rating;
    @SerializedName("city_name")
    private String city;
    @SerializedName("locality_name")
    private String street;
    @SerializedName("image_string")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
