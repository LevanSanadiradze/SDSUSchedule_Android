package com.example.root.sdsu_gmap.helper;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Davit Beradze
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(Editable email) {
        if (email == null)
            return false;

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email.toString());
        return matcher.find();
    }

    public static boolean validatePassword(Editable password) {
        if (password == null)
            return false;

        //  return password.toString().matches("^(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
        return password.toString().length() >= 8;
    }

    public static boolean validatePasswords(Editable password, Editable rPassword) {
        if (password == null)
            return false;
        if (rPassword == null)
            return false;

        if (!validatePassword(password))
            return false;

        if (!validatePassword(rPassword))
            return false;

        return password.toString().equals(rPassword.toString());
    }

    public static boolean validateName(Editable name) {
        if (name == null)
            return false;

        return name.toString().length() >= 1;
    }


    public static boolean validateEmpty(Editable id) {
        if (id == null || id.toString() == null)
            return false;

        return id.toString().length() >= 1;
    }

    public static String getDay(String day) {
        switch (day) {
            case "M":
                return "Monday";
            case "T":
                return "Tuesday";
            case "W":
                return "Wednesday";
            case "Th":
                return "Thursday";
            case "F":
                return "Friday";
            case "S":
                return "Saturday";
            default:
                return "";
        }
    }

    public static String formatDate(String time) {
        String formattedDate = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);

            SimpleDateFormat format = new SimpleDateFormat("MMM dd");
            formattedDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String elapsedTime(String apiDate) {
        return elapsedTimeFromApiDate(apiDate, "yyyy-MM-dd HH:mm:ss");
    }

    private static SimpleDateFormat getDateFormat(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        dateFormat.setTimeZone(TimeZone.getDefault());

        return dateFormat;
    }

    private static Date fixDateWithOffset(String format, String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(date);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US);
            dateFormatter.setTimeZone(TimeZone.getDefault());
            String fixed = dateFormatter.format(value);

            return dateFormatter.parse(fixed);

        } catch (ParseException e) {
            return null;
        }

    }

    private static String elapsedTimeFromApiDate(String apiDate, String dateFormat) {
        try {
            Date from = fixDateWithOffset(dateFormat, apiDate);

            // milliseconds
            long different = new Date().getTime() - from.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long monthInMilli = daysInMilli * 30;
            long yearInMilli = monthInMilli * 12;

            long elapsedYears = different / yearInMilli;

            long elapsedMonths = different / monthInMilli;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedYears > 0)
                return elapsedYears + " year";
            if (elapsedMonths > 0)
                return elapsedMonths + " mon";
            if (elapsedDays > 0)
                return elapsedDays + " d";
            if (elapsedHours > 0)
                return elapsedHours + " hrs";
            if (elapsedMinutes > 0)
                return elapsedMinutes + " min";
            if (elapsedSeconds > 0)
                return elapsedSeconds + " sec";

            else
                return "now";

        } catch (Exception e) {
            Log.e(TAG, "error", e);
            return "";
        }
    }

    public static void showKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
