package com.example.root.sdsu_gmap;


import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by root on 11/3/17.
 */

public class Constants {

    public static final String HOST = "http://schedule.sdsu.edu.ge/scheduleapp/";
    public static final int CONNECTION_TIMEOUT_MS = 3000;

    public static final int FILE_PICKER_FOR_SCHEDULE_CODE = 10;

    public static final int SCHEDULE_START_HOUR = 8;
    public static final int SCHEDULE_END_HOUR = 22;

    public static final int CALENDAR_VERTICAL_SCROLLBAR_STRENGTH = 6;
    public static final int CALENDAR_HORIZONTAL_SCROLLBAR_STRENGTH = 4;

    public static final String COOKIES_FILE_NAME = "Cookies";
    public static final String COURSE_COLORS_FILE_NAME = "CourseColors";

    public static final int EVENT_MARGIN_ONE_SIDE = 10; //in px

    public static final int TIME_SIDE_WIDTH = 250; //in px

    public static final int DAYS_SHOWN_IN_SCHEDULE_CALENDAR = 3;
    public static final int HOURS_SHOWN_IN_SCHEDULE_CALENDAR = 12;


    public static ArrayList<Integer> SCHEDULE_EVENT_COLORS = new ArrayList<>(Arrays.asList(
            Color.rgb(0, 151, 167),
            Color.rgb(230,74,25),
            Color.rgb(121,85,72),
            Color.rgb(81,45,168),
            Color.rgb(131,150,255),
            Color.rgb(255,87,34),
            Color.rgb(255,82,82),
            Color.rgb(124,77,255),
            Color.rgb(255,160,0),
            Color.rgb(205,220,57),
            Color.rgb(255,64,129),
            Color.rgb(68,138,255),
            Color.rgb(76,175,80),
            Color.rgb(141,242,255),
            Color.rgb(62,193,181),
            Color.rgb(255,129,90)
    ));

}