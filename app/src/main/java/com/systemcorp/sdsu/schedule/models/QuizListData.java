package com.systemcorp.sdsu.schedule.models;

/**
 * Created by giorgi on 2/18/18.
 */

public class QuizListData {
    String course;
    double time = 1;
    String title;
    boolean active;
    int id;

    public QuizListData() {
    }

    public QuizListData(String course, double time, String title, String active, String id) {
        this.course = course;
        this.time = time;
        this.title = title;
        this.active = Integer.parseInt(active) == 1;
        this.id = Integer.parseInt(id);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
