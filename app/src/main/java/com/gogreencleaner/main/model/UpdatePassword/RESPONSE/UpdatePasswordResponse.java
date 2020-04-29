package com.gogreencleaner.main.model.UpdatePassword.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdatePasswordResponse extends BaseResponse {

    @SerializedName("result")
    List<UpdatePasswordResponseResult> result;

    public List<UpdatePasswordResponseResult> getResult() {
        return result;
    }

    public void setResult(List<UpdatePasswordResponseResult> result) {
        this.result = result;
    }
}
