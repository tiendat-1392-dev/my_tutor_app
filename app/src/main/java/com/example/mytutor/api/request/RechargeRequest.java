package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeRequest {

    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("money")
    private Integer money;

    public RechargeRequest() {
    }

    public RechargeRequest(String _id, Integer money) {
        this._id = _id;
        this.money = money;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "RechargeRequest{" +
                "_id='" + _id + '\'' +
                ", money=" + money +
                '}';
    }
}
