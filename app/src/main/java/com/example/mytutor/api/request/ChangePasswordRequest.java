package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("old_password")
    private String old_password;

    @Expose
    @SerializedName("new_password")
    private String new_password;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String _id, String old_password, String new_password) {
        this._id = _id;
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "_id='" + _id + '\'' +
                ", old_password='" + old_password + '\'' +
                ", new_password='" + new_password + '\'' +
                '}';
    }
}
