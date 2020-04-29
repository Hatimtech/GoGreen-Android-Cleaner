package com.gogreencleaner.main.model.UpdateAttendedStatus.RESPONSE;

import com.gogreencleaner.main.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateAttendedStatusResponse extends BaseResponse {

    @SerializedName("result")
    List<UpdateAttendedStatusResponseResult> result;

    public List<UpdateAttendedStatusResponseResult> getResult() {
        return result;
    }

    public void setResult(List<UpdateAttendedStatusResponseResult> result) {
        this.result = result;
    }
}
