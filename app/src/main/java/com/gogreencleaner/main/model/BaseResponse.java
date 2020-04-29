package com.gogreencleaner.main.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status")
    int status;

    @SerializedName("resCode")
    int resCode;

    @SerializedName("message")
    String message;

    public boolean isSuccess() {
        if (status == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
