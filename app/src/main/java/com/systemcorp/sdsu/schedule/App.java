package com.systemcorp.sdsu.schedule;

import android.app.Application;

import com.systemcorp.sdsu.schedule.font.FontsOverride;
import com.systemcorp.sdsu.schedule.models.Course;

import java.util.List;

/**
 * Created by giorgi on 1/12/18.
 */

public class App extends Application {
    private static App instance;
    private String cookies;
    private FontsOverride fonts;
    private List<Course> courses;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        fonts = new FontsOverride(this);
        fonts.loadFonts();
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
