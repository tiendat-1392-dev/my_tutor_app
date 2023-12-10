package com.example.mytutor.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAPI {
    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private Object data;

    public ResponseAPI() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseAPI{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
