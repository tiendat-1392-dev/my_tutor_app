package com.example.mytutor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Schedule {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("tutor_id")
    private Account tutor;

    @Expose
    @SerializedName("student_id")
    private Account student;

    @Expose
    @SerializedName("subject_id")
    private Subject subject;

    @Expose
    @SerializedName("time")
    private List<String> times;

    @Expose
    @SerializedName("day")
    private List<Integer> days;

    @Expose
    @SerializedName("hour")
    private List<Integer> hours;

    @Expose
    @SerializedName("is_accepted")
    private boolean isAccepted;

    @Expose
    @SerializedName("price")
    private Integer price;

    @Expose
    @SerializedName("num_sessions")
    private Integer numberSession;

    @Expose
    @SerializedName("type")
    private Integer type;

    @Expose
    @SerializedName("createdAt")
    private Date createdAt;

    public Schedule() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getTutor() {
        return tutor;
    }

    public void setTutor(Account tutor) {
        this.tutor = tutor;
    }

    public Account getStudent() {
        return student;
    }

    public void setStudent(Account student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", tutor=" + tutor +
                ", student=" + student +
                ", subject=" + subject +
                ", times=" + times +
                ", days=" + days +
                ", hours=" + hours +
                ", isAccepted=" + isAccepted +
                ", price=" + price +
                ", numberSession=" + numberSession +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
