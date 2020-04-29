package com.gogreencleaner.main.model.Login.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse extends BaseResponse {

    @SerializedName("result")
    List<LoginResponseResult> result;

    public List<LoginResponseResult> getResult() {
        return result;
    }

    public void setResult(List<LoginResponseResult> result) {
        this.result = result;
    }
}
