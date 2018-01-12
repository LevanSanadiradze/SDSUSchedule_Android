package com.example.root.sdsu_gmap;

import android.app.Application;

/**
 * Created by giorgi on 1/12/18.
 */

public class App extends Application {
    private static App instance;
    private String cookies;

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
}
