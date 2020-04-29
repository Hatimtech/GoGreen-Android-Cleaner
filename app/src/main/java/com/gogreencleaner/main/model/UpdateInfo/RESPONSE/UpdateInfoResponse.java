package com.gogreencleaner.main.model.UpdateInfo.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateInfoResponse extends BaseResponse {

    @SerializedName("result")
    List<UpdateInfoResponseResult> result;

    public List<UpdateInfoResponseResult> getResult() {
        return result;
    }

    public void setResult(List<UpdateInfoResponseResult> result) {
        this.result = result;
    }
}
