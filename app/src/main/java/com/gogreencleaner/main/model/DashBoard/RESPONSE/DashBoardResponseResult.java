package com.gogreencleaner.main.model.DashBoard.RESPONSE;

import com.google.gson.annotations.SerializedName;

public class DashBoardResponseResult {



    @SerializedName("basick_info")
     BasicInfo basicInfo ;
    @SerializedName("completed")
    private String completed_task;
    @SerializedName("today")
    private String today_task;
    @SerializedName("remaining")
    private String remaining_task;


    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getCompleted_task() {
        return completed_task;
    }

    public void setCompleted_task(String completed_task) {
        this.completed_task = completed_task;
    }

    public String getToday_task() {
        return today_task;
    }

    public void setToday_task(String today_task) {
        this.today_task = today_task;
    }

    public String getRemaining_task() {
        return remaining_task;
    }

    public void setRemaining_task(String remaining_task) {
        this.remaining_task = remaining_task;
    }
}
