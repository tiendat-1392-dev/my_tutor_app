package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptScheduleRequest {

    @Expose
    @SerializedName("user_id")
    private String user_id;

    @Expose
    @SerializedName("schedule_id")
    private String schedule_id;

    public AcceptScheduleRequest() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    @Override
    public String toString() {
        return "AcceptRequest{" +
                "user_id='" + user_id + '\'' +
                ", schedule_id='" + schedule_id + '\'' +
                '}';
    }
}
