package com.example.root.sdsu_gmap.models;

/**
 * Created by giorgi on 1/15/18.
 */

public class TasksData {
    private String title;
    private String details;

    public TasksData() {
    }

    public TasksData(String title, String details) {
        this.title = title;
        this.details = details;
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
