package com.systemcorp.sdsu.schedule.models;

/**
 * Created by giorgi on 1/15/18.
 */

public class TasksData {
    private int id;
    private String time;
    private String title;
    private String details;

    public TasksData() {
    }

    public TasksData(String id, String time, String title, String details) {
        this.id = Integer.parseInt(id);
        this.time = time;
        this.title = title;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
