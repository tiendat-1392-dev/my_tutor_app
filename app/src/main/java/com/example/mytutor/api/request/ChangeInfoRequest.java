package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeInfoRequest {

    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("gender")
    private Integer gender;

    @Expose
    @SerializedName("date_of_birth")
    private String dob;

    @Expose
    @SerializedName("phone")
    private String phone;

    public ChangeInfoRequest() {
    }

    public ChangeInfoRequest(String _id, String name, Integer gender, String dob, String phone) {
        this._id = _id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ChangeInfoRequest{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
