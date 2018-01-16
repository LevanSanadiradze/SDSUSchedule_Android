package com.example.root.sdsu_gmap;

import android.app.Application;

import com.example.root.sdsu_gmap.models.Course;

import java.util.List;

/**
 * Created by giorgi on 1/12/18.
 */

public class App extends Application {
    private static App instance;
    private String cookies;
    private List<Course> courses;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static App get() {
        return instance;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
