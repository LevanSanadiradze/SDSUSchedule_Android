package com.example.root.sdsu_gmap.models;

/**
 * Created by giorgi on 1/19/18.
 */

public class MenuItem {
    private int icon;
    private String title;

    public MenuItem() {
    }

    public MenuItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
