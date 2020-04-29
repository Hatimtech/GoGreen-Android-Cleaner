package com.gogreencleaner.main.model.ChangePassword.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePasswordResponse extends BaseResponse {

    @SerializedName("result")
    List<ChangePasswordResponseResult> result;

    public List<ChangePasswordResponseResult> getResult() {
        return result;
    }

    public void setResult(List<ChangePasswordResponseResult> result) {
        this.result = result;
    }
}
