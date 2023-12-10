package com.example.mytutor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Account {

    @Expose
    @SerializedName("_id")
    private String id;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

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

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("money")
    private Integer money;

    @Expose
    @SerializedName("role")
    private Integer role;

    @Expose
    @SerializedName("createdAt")
    private Date createdAt;

    @Expose
    @SerializedName("accessToken")
    private String accessToken;

    public Account() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
