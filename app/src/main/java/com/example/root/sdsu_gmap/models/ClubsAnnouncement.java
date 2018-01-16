package com.example.root.sdsu_gmap.models;

import java.util.List;

/**
 * Created by giorgi on 1/13/18.
 */

public class ClubsAnnouncement {
    private int id;
    private String votedId;
    private boolean seen;
    private String name;
    private String time;
    private String text;
    private String color;
    private List<String> pollData;
    private List<Integer> pollAnswers;

    public ClubsAnnouncement() {
    }

    public ClubsAnnouncement(String id, String votedId, String seen, String name, String time, String text, String color) {
        this.id = Integer.parseInt(id);
        this.votedId = votedId;
        this.seen = Integer.parseInt(seen) == 1;
        this.name = name;
        this.time = time;
        this.text = text;
        this.color = color;
    }

    public ClubsAnnouncement(String id, String votedId, String seen, String name, String time, String text, String color, List<String> pollData, List<Integer> pollAnswers) {
        this.id = Integer.parseInt(id);
        this.votedId = votedId;
        this.seen = Integer.parseInt(seen) == 1;
        this.name = name;
        this.time = time;
        this.text = text;
        this.color = color;
        this.pollData = pollData;
        this.pollAnswers = pollAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVotedId() {
        return votedId;
    }

    public void setVotedId(String votedId) {
        this.votedId = votedId;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
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

    public List<String> getPollData() {
        return pollData;
    }

    public void setPollData(List<String> pollData) {
        this.pollData = pollData;
    }

    public List<Integer> getPollAnswers() {
        return pollAnswers;
    }

    public void setPollAnswers(List<Integer> pollAnswers) {
        this.pollAnswers = pollAnswers;
    }
}
