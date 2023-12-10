package com.example.mytutor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("name")
    private String name;

    public Subject() {
    }

    public Subject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "_id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
