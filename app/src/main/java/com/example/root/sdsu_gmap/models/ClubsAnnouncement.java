package com.example.root.sdsu_gmap.models;

/**
 * Created by giorgi on 1/13/18.
 */

public class ClubsAnnouncement {
    private String name;
    private String time;
    private String text;
    private String color;

    public ClubsAnnouncement() {
    }

    public ClubsAnnouncement(String name, String time, String text, String color) {
        this.name = name;
        this.time = time;
        this.text = text;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
