package com.example.root.sdsu_gmap;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.Calendar;

import ge.sanapps.tabbedscrollview.TabbedHorizontalScrollView;
import ge.sanapps.tabbedscrollview.TabbedVerticalScrollView;

public class LoggedInActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public StudentInformation studentInfo;
    private String Cookies = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cookies = getIntent().getStringExtra("Cookies");
        loadStudentSchedule();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        RelativeLayout tabsContainer = findViewById(R.id.tabscontainer);

        for (int i = 0; i < tabsContainer.getChildCount(); i++)
        {
            tabsContainer.getChildAt(i).setVisibility(View.GONE);
        }

        //TODO
        if (id == R.id.Announcements_menuitem) {
            tabsContainer.findViewById(R.id.Announcements_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Courses_menuitem) {
            tabsContainer.findViewById(R.id.Courses_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Tasks_menuitem) {
            tabsContainer.findViewById(R.id.Tasks_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Events_menuitem) {
            tabsContainer.findViewById(R.id.Events_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Clubs_menuitem) {
            tabsContainer.findViewById(R.id.Clubs_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Contact_menuitem) {
            tabsContainer.findViewById(R.id.Contact_tab).setVisibility(View.VISIBLE);
        }
        else if (id == R.id.Settings_menuitem) {
            tabsContainer.findViewById(R.id.Settings_tab).setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadStudentSchedule()
    {
        NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "getStudentSchedule.php", new ArrayList<String>(), Cookies);

        Pair<Object, CookieManager> data = null;
        try {
            data = NC.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

        if(Response.get("ErrorCode").equals("-1"))
            finish();
        else if(Response.get("ErrorCode").equals("0"))
        {
            if(Response.get("Schedule").equals(""))
            {
                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }

                builder.setTitle("Schedule file missing!")
                        .setMessage("You do not have a schedule file uploaded! You can upload it now, or later in the Settings.")
                        .setPositiveButton("Upload Now", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                openFilePicker(null);
                            }
                        })
                        .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else
            {
                this.studentInfo = ScheduleFileParser.parseScheduleFile((HashMap<String, Object>) Response.get("Schedule"));
                AddInfoToViews();
            }
        }
        else
        {
            Toast.makeText(this, "Unexpected error: " + Response.get("ErrorCode"), Toast.LENGTH_LONG).show();
        }

    }

    private void uploadStudentSchedule(String content)
    {
        ArrayList<String> post = new ArrayList<>();
        post.add("ScheduleData=" + content);

        NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "saveStudentSchedule.php", post, Cookies);

        Pair<Object, CookieManager> data = null;
        try {
            data = NC.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

        if(Response.get("ErrorCode").equals("-1"))
            finish();
        else if(Response.get("ErrorCode").equals("0"))
        {
            loadStudentSchedule();
        }
        else
        {
            Toast.makeText(this, "Unexpeced error: " + Response.get("ErrorCode"),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openFilePicker(View v)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);


        try {
            startActivityForResult(Intent.createChooser(intent, "Upload a file"), Constants.FILE_PICKER_FOR_SCHEDULE_CODE);
        }
        catch (ActivityNotFoundException e)
        {
            Toast.makeText(this, "Please install a file manager", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case Constants.FILE_PICKER_FOR_SCHEDULE_CODE:
                if(resultCode == RESULT_OK)
                {
                    uploadStudentSchedule(ReadScheduleFile(data.getData()));
                }
                break;
        }
    }

    private String ReadScheduleFile(Uri path)
    {
        StringBuilder content = new StringBuilder();

        try {
            InputStream is = getContentResolver().openInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;

            while((line = br.readLine()) != null)
            {
                content.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private void AddInfoToViews()
    {
        AddScheduleInfoToScrollViews();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView StudentNameTextView = headerView.findViewById(R.id.StudentNameTextView);
        String StudentName = studentInfo.getFirstName() + " ";
        StudentName += !studentInfo.getMiddleName().equals("") ? studentInfo.getMiddleName() + " " : "";
        StudentName += studentInfo.getLastName();
        StudentNameTextView.setText(StudentName);


    }

    private void AddScheduleInfoToScrollViews()
    {
        Date curTime = Calendar.getInstance().getTime();

        LayoutInflater inflater = LayoutInflater.from(this);

        TableLayout scheduleDays = findViewById(R.id.ScheduleDaysTable);

        LinearLayout scheduleTimesContainer = findViewById(R.id.ScheduleTimesContainer);

        final int numberOfColumns = 21;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        int dayWidth = (int) Math.floor(width / 4);
        int hourHeight = (int) Math.floor(height / 12);

        ViewGroup.LayoutParams scheduleTimesContainerLP = scheduleTimesContainer.getLayoutParams();
        scheduleTimesContainerLP.width = dayWidth;
        scheduleTimesContainer.setLayoutParams(scheduleTimesContainerLP);

        int paddingormargintop = (int) Math.round(hourHeight / 3.0);

        scheduleTimesContainer.setPadding(0, paddingormargintop, 0, 0);

        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).setStepSize(hourHeight);
        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).setScrollStrength(5);
        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).requestDisallowInterceptTouchEvent(true);


        for(int h = Constants.SCHEDULE_START_HOUR; h <= Constants.SCHEDULE_END_HOUR; h++)
        {
            View inflatedLayout = inflater.inflate(R.layout.schedule_calendar_time, null, false);

            String HourString = h + ":00";
            HourString = HourString.length() == 4 ? "0" + HourString : HourString;

            ((TextView) inflatedLayout.findViewById(R.id.TimeTextView)).setText(HourString);

            scheduleTimesContainer.addView(inflatedLayout);

            ViewGroup.LayoutParams LP = (inflatedLayout.findViewById(R.id.ScheduleTimeContainer)).getLayoutParams();
            LP.width = dayWidth;
            LP.height = hourHeight;
            inflatedLayout.findViewById(R.id.ScheduleTimeContainer).setLayoutParams(LP);
        }

        ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).setStepSize(dayWidth);
        ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).setScrollStrength(3);
        findViewById(R.id.ScheduleHorizontalScrollView).post(new Runnable() {
            @Override
            public void run() {
                ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).MoveTo(((numberOfColumns - 1) / 2) - 1, false);
            }
        });

        HashMap<String, ArrayList<Pair<Integer, Integer>>> LecturesByDay = new HashMap<>();

        for(int i = 0; i < 7; i++)
        {
            String day = "";
            String dayInArr = "";
            switch(i)
            {
                case 0:
                    day = "M";
                    dayInArr = "MON";
                    break;
                case 1:
                    day = "T";
                    dayInArr = "TUE";
                    break;
                case 2:
                    day = "W";
                    dayInArr = "WED";
                    break;
                case 3:
                    day = "Th";
                    dayInArr = "THU";
                    break;
                case 4:
                    day = "F";
                    dayInArr = "FRI";
                    break;
                case 5:
                    day = "S";
                    dayInArr = "SAT";
                    break;
            }

            ArrayList<Pair<Integer, Integer>> tempLectures = new ArrayList<>();

            for(int c = 0; c < studentInfo.getCourses().size(); c++)
            {
                for(int l = 0; l < studentInfo.getCourses().get(c).getLectures().size(); l++) {
                    if (studentInfo.getCourses().get(c).getLectures().get(l).getDay().equals(day))
                    {
                        Pair<Integer, Integer> lec = new Pair<>(c, l);
                        tempLectures.add(lec);
                    }
                }
            }

            Collections.sort(tempLectures, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    int compare1 = studentInfo.getCourses().get(p1.first).getLectures().get(p1.second).getStartHour() -
                                   studentInfo.getCourses().get(p2.first).getLectures().get(p2.second).getStartHour();

                    if(compare1 != 0)
                        return compare1;
                    else
                    {
                        return studentInfo.getCourses().get(p1.first).getLectures().get(p1.second).getStartMinute() -
                                studentInfo.getCourses().get(p2.first).getLectures().get(p2.second).getStartMinute();
                    }
                }
            });

            LecturesByDay.put(dayInArr, tempLectures);
        }


        for(int d = -((numberOfColumns - 1) / 2); d <= ((numberOfColumns - 1) / 2); d++)
        {
            Calendar tempcal = Calendar.getInstance();
            tempcal.setTime(curTime);
            tempcal.add(Calendar.DAY_OF_MONTH, d);

            String DayName = getWeekday(tempcal, "E").toUpperCase();
            String DateString = tempcal.get(Calendar.DAY_OF_MONTH) + " / " + (tempcal.get(Calendar.MONTH) + 1);

            TableRow tempTableRow = (TableRow) scheduleDays.getChildAt(0);

            View inflatedLayout = inflater.inflate(R.layout.schedule_calendar_day, null, false);

            ((TextView) inflatedLayout.findViewById(R.id.DayNameTextView)).setText(DayName);
            ((TextView) inflatedLayout.findViewById(R.id.DateTextView)).setText(DateString);

            tempTableRow.addView(inflatedLayout);

            ViewGroup.LayoutParams LP = (inflatedLayout.findViewById(R.id.ScheduleDayContainer)).getLayoutParams();
            LP.width = dayWidth;
            LP.height = paddingormargintop;
            inflatedLayout.findViewById(R.id.ScheduleDayContainer).setLayoutParams(LP);

            ArrayList<Pair<Integer, Integer>> tempAL = LecturesByDay.get(DayName);

            int previousEndTime = Constants.SCHEDULE_START_HOUR * 60;

            if(tempAL != null)
                for(int e = 0; e < tempAL.size(); e++)
                {
                    View placeholderView = new View(this);
                    ((LinearLayout) inflatedLayout.findViewById(R.id.DayInfoContainer)).addView(placeholderView);

                    Lecture tempLecture = studentInfo.getCourses().get(tempAL.get(e).first).getLectures().get(tempAL.get(e).second);

                    ViewGroup.LayoutParams placeholderViewLP = placeholderView.getLayoutParams();
                    placeholderViewLP.width = dayWidth;
                    placeholderViewLP.height = (int) Math.round((((tempLecture.getStartHour() * 60.0 + tempLecture.getStartMinute()) - previousEndTime) / 60.0) * hourHeight);
                    placeholderView.setLayoutParams(placeholderViewLP);



                    View eventLayout = inflater.inflate(R.layout.schedule_calendar_event, null, false);
                    ((TextView) eventLayout.findViewById(R.id.CourseNameTextView)).setText(studentInfo.getCourses().get(tempAL.get(e).first).getCourse());

                    ((TextView) eventLayout.findViewById(R.id.LectureBuildingTextView)).setText(tempLecture.getPartner() + " " + tempLecture.getBuilding() + " " + tempLecture.getRoom());

                    ((LinearLayout) inflatedLayout.findViewById(R.id.DayInfoContainer)).addView(eventLayout);

                    ConstraintLayout.LayoutParams eventLP = (ConstraintLayout.LayoutParams) (eventLayout.findViewById(R.id.ScheduleEventContainer)).getLayoutParams();
                    eventLP.width = dayWidth;
                    eventLP.height = (int) Math.round((((tempLecture.getEndHour() * 60.0 + tempLecture.getEndMinute()) - (tempLecture.getStartHour() * 60.0 + tempLecture.getStartMinute())) / 60.0) * hourHeight);
                    eventLayout.findViewById(R.id.ScheduleEventContainer).setLayoutParams(eventLP);


                    previousEndTime = (tempLecture.getEndHour() * 60 + tempLecture.getEndMinute());

                }
        }

    }

    private String getWeekday(Calendar date, String Pattern)
    {
        SimpleDateFormat dayFormat = new SimpleDateFormat(Pattern, Locale.US);
        return dayFormat.format(date.getTime());
    }
}
