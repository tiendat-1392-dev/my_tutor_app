package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateScheduleRequest {
    @Expose
    @SerializedName("user_id")
    private String user_id;

    @Expose
    @SerializedName("subject_id")
    private String subject_id;

    @Expose
    @SerializedName("price")
    private Integer price;

    @Expose
    @SerializedName("num_sessions")
    private Integer numSessions;

    @Expose
    @SerializedName("day")
    private List<Integer> days;

    @Expose
    @SerializedName("hour")
    private List<Integer> hours;

    public CreateScheduleRequest() {
    }

    public CreateScheduleRequest(String user_id, String subject_id, Integer price, Integer numSessions,
                                 List<Integer> days, List<Integer> hours) {
        this.user_id = user_id;
        this.subject_id = subject_id;
        this.price = price;
        this.numSessions = numSessions;
        this.days = days;
        this.hours = hours;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(Integer numSessions) {
        this.numSessions = numSessions;
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
        return "ScheduleRegisterRequest{" +
                "user_id='" + user_id + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", price=" + price +
                ", numSessions=" + numSessions +
                ", day=" + days +
                ", hour=" + hours +
                '}';
    }
}
