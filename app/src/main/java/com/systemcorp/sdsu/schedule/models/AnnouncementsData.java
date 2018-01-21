package com.systemcorp.sdsu.schedule.models;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsData {
    private int id;
    private String date;
    private String title;
    private String text;
    private boolean seen;

    public AnnouncementsData() {
    }

    public AnnouncementsData(String date, String title, String text, String seen, String id) {
        this.date = date;
        this.title = title;
        this.text = text;
        this.id = Integer.parseInt(id);
        this.seen = Integer.parseInt(seen) == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
