package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoveScheduleRequest {

    @Expose
    @SerializedName("schedule_id")
    private String schedule_id;

    public RemoveScheduleRequest() {
    }

    public RemoveScheduleRequest(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    @Override
    public String toString() {
        return "RemoveScheduleRequest{" +
                "schedule_id='" + schedule_id + '\'' +
                '}';
    }
}
