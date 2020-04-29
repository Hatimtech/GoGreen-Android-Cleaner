package com.gogreencleaner.main.model.FgPassword.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FgPasswordResponse extends BaseResponse{

    @SerializedName("result")
    List<FgPasswordResponseResult> result;

    public List<FgPasswordResponseResult> getResult() {
        return result;
    }

    public void setResult(List<FgPasswordResponseResult> result) {
        this.result = result;
    }
}
