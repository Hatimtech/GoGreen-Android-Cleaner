package com.gogreencleaner.main.model.DashBoard.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class DashBoardResponseWrapper {

    @SerializedName("data")
    DashBoardResponse response;

    public DashBoardResponse getResponse() {
        return response;
    }

    public void setResponse(DashBoardResponse response) {
        this.response = response;
    }
}
