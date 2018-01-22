package com.systemcorp.sdsu.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.systemcorp.sdsu.schedule.font.FontsOverride;
import com.systemcorp.sdsu.schedule.models.Course;

import java.net.CookieManager;
import java.util.ArrayList;
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

    public void clearCookies(Activity activity) {
        String FILENAME = Constants.COOKIES_FILE_NAME;

        activity.deleteFile(FILENAME);
    }

    public void logout(final Activity activity) {
        new NetworkCommunicator(Constants.HOST + "logout.php", new ArrayList<String>(), App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> success) {
                super.onPostExecute(success);

                App.get().setCookies("");
                App.get().setCourses(null);
                App.get().clearCookies(activity);

                Intent mStartActivity = new Intent(activity, MainActivity.class);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(activity, mPendingIntentId, mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);


                activity.finishAffinity();


                System.exit(0);
            }
        }.execute();
    }
}
