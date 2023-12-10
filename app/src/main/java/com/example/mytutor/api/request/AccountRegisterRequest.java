package com.example.mytutor.api.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountRegisterRequest {

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
    @SerializedName("role")
    private Integer role;

    public AccountRegisterRequest() {
    }

    public AccountRegisterRequest(String username, String password, Integer role, String name, Integer gender,
                                  String dob, String phone, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreateAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob='" + dob + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
