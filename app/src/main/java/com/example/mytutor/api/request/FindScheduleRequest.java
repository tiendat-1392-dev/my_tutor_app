package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindScheduleRequest {

    @Expose
    @SerializedName("type")
    private Integer type;

    @Expose
    @SerializedName("subject_id")
    private String subjectId;

    @Expose
    @SerializedName("price")
    private Integer price;

    @Expose
    @SerializedName("num_sessions")
    private Integer numberSession;

    @Expose
    @SerializedName("day")
    private List<Integer> days;

    @Expose
    @SerializedName("hour")
    private List<Integer> hours;

    public FindScheduleRequest() {
    }

    public FindScheduleRequest(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNumberSession() {
        return numberSession;
    }

    public void setNumberSession(Integer numberSession) {
        this.numberSession = numberSession;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours(List<Integer> hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "FindScheduleRegisterRequest{" +
                "type='" + type + '\'' +
                '}';
    }
}
