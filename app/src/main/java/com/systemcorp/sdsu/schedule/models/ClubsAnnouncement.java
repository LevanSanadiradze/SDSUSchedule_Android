package com.systemcorp.sdsu.schedule.models;

import java.util.List;

/**
 * Created by giorgi on 1/13/18.
 */

public class ClubsAnnouncement {
    private int id;
    private boolean seen;
    private String name;
    private String time;
    private String text;
    private String color;

    public int getId() {
        return id;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public List<PollDataClass> getPollData() {
        return pollData;
    }

    private List<PollDataClass> pollData;


    public ClubsAnnouncement(int id, boolean seen, String name, String time, String text, String color, List<PollDataClass> pollData) {
        this.id = id;
        this.seen = seen;
        this.name = name;
        this.time = time;
        this.text = text;
        this.color = color;
        this.pollData = pollData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPollData(List<PollDataClass> pollData) {
        this.pollData = pollData;
    }
}