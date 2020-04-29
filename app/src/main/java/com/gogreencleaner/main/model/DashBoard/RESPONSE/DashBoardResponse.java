package com.gogreencleaner.main.model.DashBoard.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardResponse extends BaseResponse{

    @SerializedName("result")
    DashBoardResponseResult result;

    public DashBoardResponseResult getResult() {
        return result;
    }

    public void setResult(DashBoardResponseResult result) {
        this.result = result;
    }
}
