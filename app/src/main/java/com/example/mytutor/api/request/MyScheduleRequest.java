package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyScheduleRequest {

    @Expose
    @SerializedName("_id")
    private String _id;

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

    public MyScheduleRequest() {
    }

    public MyScheduleRequest(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
        return "MyScheduleRequest{" +
                "_id='" + _id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", price=" + price +
                ", numberSession=" + numberSession +
                ", days=" + days +
                ", hours=" + hours +
                '}';
    }
}
