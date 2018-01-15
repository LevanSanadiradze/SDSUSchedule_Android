package com.example.root.sdsu_gmap;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.sdsu_gmap.fragments.AnnouncementsFragment;
import com.example.root.sdsu_gmap.fragments.ClubsFragment;
import com.example.root.sdsu_gmap.fragments.CoursesFragment;
import com.example.root.sdsu_gmap.fragments.TasksFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import ge.sanapps.tabbedscrollview.TabbedHorizontalScrollView;
import ge.sanapps.tabbedscrollview.TabbedVerticalScrollView;

public class LoggedInActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public StudentInformation studentInfo;
    private String Cookies = "";

    private AlertDialog eDialog;
    private MapView mapView;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;

    private HashMap<String, Integer> CourseColors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getEventColors();

        Cookies = App.get().getCookies();
        loadStudentSchedule();

        fragmentManager = getFragmentManager();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getEventColors() {
        String FILENAME = Constants.COURSE_COLORS_FILE_NAME;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));

            String value = null;
            while ((value = br.readLine()) != null) {
                String[] temp = value.split(",");
                Integer color = Integer.parseInt(temp[1]);
                CourseColors.put(temp[0], color);
                Constants.SCHEDULE_EVENT_COLORS.remove(color);
            }
            br.close();

        } catch (IOException e) {
            return;
        }
    }

    private Integer generateEventColor(String CourseID) {
        String FILENAME = Constants.COURSE_COLORS_FILE_NAME;

        if (Constants.SCHEDULE_EVENT_COLORS.size() == 0)
            return null;

        Collections.shuffle(Constants.SCHEDULE_EVENT_COLORS);

        Integer color = Constants.SCHEDULE_EVENT_COLORS.remove(0);
        String value = CourseID + "," + color + System.getProperty("line.separator");

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);

            fos.write(value.getBytes());

            CourseColors.put(CourseID, color);
            fos.close();

        } catch (IOException e) {
            return null;
        }

        return color;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//todo change to schedule fragment
            if (fragmentManager.findFragmentByTag("ANNOUNCEMENT") != null && fragmentManager.findFragmentByTag("ANNOUNCEMENT").isVisible())
                super.onBackPressed();
            else {
                AnnouncementsFragment fragment = new AnnouncementsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.tabscontainer, fragment, "ANNOUNCEMENT")
//                    .addToBackStack("ANNOUNCEMENT")
                        .commit();
                navigationView.getMenu().getItem(0).setChecked(true);
            }

        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_in, menu);
        return true;
    }*/

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

        for (int i = 0; i < tabsContainer.getChildCount(); i++) {
            tabsContainer.getChildAt(i).setVisibility(View.GONE);
        }

        if (id == R.id.Schedule_menuitem) {
            tabsContainer.findViewById(R.id.Schedule_tab).setVisibility(View.VISIBLE);
        } else if (id == R.id.Announcements_menuitem) {
//            tabsContainer.findViewById(R.id.Announcements_tab).setVisibility(View.VISIBLE);
            AnnouncementsFragment fragment = new AnnouncementsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.tabscontainer, fragment, "ANNOUNCEMENT")//todo delete tag
                    .commit();
        } else if (id == R.id.Courses_menuitem) {
//            tabsContainer.findViewById(R.id.Courses_tab).setVisibility(View.VISIBLE);
            CoursesFragment fragment = new CoursesFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.tabscontainer, fragment)
                    .commit();
        } else if (id == R.id.Tasks_menuitem) {
//            tabsContainer.findViewById(R.id.Tasks_tab).setVisibility(View.VISIBLE);
            TasksFragment fragment = new TasksFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.tabscontainer, fragment)
                    .commit();
        } else if (id == R.id.Events_menuitem) {
            tabsContainer.findViewById(R.id.Events_tab).setVisibility(View.VISIBLE);
        } else if (id == R.id.Clubs_menuitem) {
//            tabsContainer.findViewById(R.id.Clubs_tab).setVisibility(View.VISIBLE);
            ClubsFragment fragment = new ClubsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.tabscontainer, fragment)
                    .commit();
        } else if (id == R.id.Contact_menuitem) {
            tabsContainer.findViewById(R.id.Contact_tab).setVisibility(View.VISIBLE);
        } else if (id == R.id.Settings_menuitem) {
            tabsContainer.findViewById(R.id.Settings_tab).setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadStudentSchedule() {
        final Context ctx = this;

        new NetworkCommunicator(Constants.HOST + "getStudentSchedule.php", new ArrayList<String>(), Cookies) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(ctx, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                if (Response.get("ErrorCode").equals("-1"))
                    finish();
                else if (Response.get("ErrorCode").equals("0")) {
                    if (Response.get("Schedule").equals("")) {
                        AlertDialog.Builder builder;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Light_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(ctx);
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
                    } else {
                        studentInfo = ScheduleFileParser.parseScheduleFile((HashMap<String, Object>) Response.get("Schedule"));
                        AddInfoToViews();
                    }
                } else {
                    Toast.makeText(ctx, "Unexpected error: " + Response.get("ErrorCode"), Toast.LENGTH_LONG).show();
                }
            }
        }.execute();


    }

    private void uploadStudentSchedule(String content) {
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

        if (Response.get("ErrorCode").equals("-1"))
            finish();
        else if (Response.get("ErrorCode").equals("0")) {
            loadStudentSchedule();
        } else {
            Toast.makeText(this, "Unexpeced error: " + Response.get("ErrorCode"),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void openFilePicker(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);


        try {
            startActivityForResult(Intent.createChooser(intent, "Upload a file"), Constants.FILE_PICKER_FOR_SCHEDULE_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Please install a file manager", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.FILE_PICKER_FOR_SCHEDULE_CODE:
                if (resultCode == RESULT_OK) {
                    uploadStudentSchedule(ReadScheduleFile(data.getData()));
                }
                break;
        }
    }

    private String ReadScheduleFile(Uri path) {
        StringBuilder content = new StringBuilder();

        try {
            InputStream is = getContentResolver().openInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;

            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private void AddInfoToViews() {
        AddScheduleInfoToScrollViews();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.getMenu().getItem(0).setChecked(true);

        View headerView = navigationView.getHeaderView(0);

        TextView StudentNameTextView = headerView.findViewById(R.id.StudentNameTextView);
        String StudentName = studentInfo.getFirstName() + " ";
        StudentName += !studentInfo.getMiddleName().equals("") ? studentInfo.getMiddleName() + " " : "";
        StudentName += studentInfo.getLastName();
        StudentNameTextView.setText(StudentName);
    }

    private void AddScheduleInfoToScrollViews() {


        final float scale = LoggedInActivity.this.getResources().getDisplayMetrics().density;
        int pixels = (int) (Constants.TIME_SIDE_WIDTH * scale + 0.5f);

        Date curTime = Calendar.getInstance().getTime();

        LayoutInflater inflater = LayoutInflater.from(this);

        TableLayout scheduleDays = findViewById(R.id.ScheduleDaysTable);

        LinearLayout scheduleTimesContainer = findViewById(R.id.ScheduleTimesContainer);

        final int numberOfColumns = 21;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        int dayWidth = (int) Math.floor((width - Constants.TIME_SIDE_WIDTH) / Constants.DAYS_SHOWN_IN_SCHEDULE_CALENDAR);
        int hourHeight = (int) Math.floor(height / Constants.HOURS_SHOWN_IN_SCHEDULE_CALENDAR);

        ViewGroup.LayoutParams scheduleTimesContainerLP = scheduleTimesContainer.getLayoutParams();
        scheduleTimesContainerLP.width = pixels;
        scheduleTimesContainer.setLayoutParams(scheduleTimesContainerLP);

        int paddingormargintop = (int) Math.round(hourHeight / 3.0);

        scheduleTimesContainer.setPadding(0, paddingormargintop, 0, 0);

        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).setStepSize(hourHeight);
        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).setScrollStrength(Constants.CALENDAR_VERTICAL_SCROLLBAR_STRENGTH);
        ((TabbedVerticalScrollView) findViewById(R.id.ScheduleVerticalScrollView)).requestDisallowInterceptTouchEvent(true);


        for (int h = Constants.SCHEDULE_START_HOUR; h <= Constants.SCHEDULE_END_HOUR; h++) {
            View inflatedLayout = inflater.inflate(R.layout.schedule_calendar_time, null, false);

            String HourString = h + ":00";
            HourString = HourString.length() == 4 ? "0" + HourString : HourString;

            ((TextView) inflatedLayout.findViewById(R.id.TimeTextView)).setText(HourString);

            scheduleTimesContainer.addView(inflatedLayout);

            ViewGroup.LayoutParams LP = (inflatedLayout.findViewById(R.id.ScheduleTimeContainer)).getLayoutParams();
            LP.width = pixels;
            LP.height = hourHeight;
            inflatedLayout.findViewById(R.id.ScheduleTimeContainer).setLayoutParams(LP);
        }

        ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).setStepSize(dayWidth);
        ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).setScrollStrength(Constants.CALENDAR_HORIZONTAL_SCROLLBAR_STRENGTH);
        findViewById(R.id.ScheduleHorizontalScrollView).post(new Runnable() {
            @Override
            public void run() {
                ((TabbedHorizontalScrollView) findViewById(R.id.ScheduleHorizontalScrollView)).MoveTo(((numberOfColumns - 1) / 2) - 1, false);
            }
        });

        HashMap<String, ArrayList<Pair<Integer, Integer>>> LecturesByDay = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            String day = "";
            String dayInArr = "";
            switch (i) {
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

            for (int c = 0; c < studentInfo.getCourses().size(); c++) {
                for (int l = 0; l < studentInfo.getCourses().get(c).getLectures().size(); l++) {
                    if (studentInfo.getCourses().get(c).getLectures().get(l).getDay().equals(day)) {
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

                    if (compare1 != 0)
                        return compare1;
                    else {
                        return studentInfo.getCourses().get(p1.first).getLectures().get(p1.second).getStartMinute() -
                                studentInfo.getCourses().get(p2.first).getLectures().get(p2.second).getStartMinute();
                    }
                }
            });

            LecturesByDay.put(dayInArr, tempLectures);
        }


        for (int d = -((numberOfColumns - 1) / 2); d <= ((numberOfColumns - 1) / 2); d++) {
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
            LP.width = dayWidth - 2 * Constants.EVENT_MARGIN_ONE_SIDE;
            LP.height = paddingormargintop;
            inflatedLayout.findViewById(R.id.ScheduleDayContainer).setLayoutParams(LP);

            ConstraintLayout.LayoutParams DIC = (ConstraintLayout.LayoutParams) (inflatedLayout.findViewById(R.id.DayInfoContainer)).getLayoutParams();
            DIC.setMargins(Constants.EVENT_MARGIN_ONE_SIDE, 0, Constants.EVENT_MARGIN_ONE_SIDE, 0);
            inflatedLayout.findViewById(R.id.ScheduleDayContainer).setLayoutParams(LP);

            ArrayList<Pair<Integer, Integer>> tempAL = LecturesByDay.get(DayName);

            int previousEndTime = Constants.SCHEDULE_START_HOUR * 60;

            if (tempAL != null)
                for (int e = 0; e < tempAL.size(); e++) {
                    View placeholderView = new View(this);
                    ((LinearLayout) inflatedLayout.findViewById(R.id.DayInfoContainer)).addView(placeholderView);

                    final Lecture tempLecture = studentInfo.getCourses().get(tempAL.get(e).first).getLectures().get(tempAL.get(e).second);

                    ViewGroup.LayoutParams placeholderViewLP = placeholderView.getLayoutParams();
                    placeholderViewLP.width = dayWidth - 2 * Constants.EVENT_MARGIN_ONE_SIDE;
                    placeholderViewLP.height = (int) Math.round((((tempLecture.getStartHour() * 60.0 + tempLecture.getStartMinute()) - previousEndTime) / 60.0) * hourHeight);
                    placeholderView.setLayoutParams(placeholderViewLP);

                    final Course tempCourse = studentInfo.getCourses().get(tempAL.get(e).first);

                    View eventLayout = inflater.inflate(R.layout.schedule_calendar_event, null, false);


                    Integer EventColor = CourseColors.get(tempCourse.getScheduleID());
                    if (EventColor == null)
                        EventColor = generateEventColor(tempCourse.getScheduleID());

                    if (EventColor != null) {
                        GradientDrawable shape = (GradientDrawable) eventLayout.getBackground();
//                        eventLayout.setBackgroundColor(EventColor);
                        shape.setColor(EventColor);
                    }
                    eventLayout.setLongClickable(true);
                    eventLayout.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            openEventDialog(tempLecture, tempCourse);
                            return true;
                        }
                    });

                    ((TextView) eventLayout.findViewById(R.id.CourseNameTextView)).setText(studentInfo.getCourses().get(tempAL.get(e).first).getCourse());

                    ((TextView) eventLayout.findViewById(R.id.LectureBuildingTextView)).setText(tempLecture.getPartner() + " " + tempLecture.getBuilding() + " " + tempLecture.getRoom());

                    ((LinearLayout) inflatedLayout.findViewById(R.id.DayInfoContainer)).addView(eventLayout);

                    ConstraintLayout.LayoutParams eventLP = (ConstraintLayout.LayoutParams) (eventLayout.findViewById(R.id.ScheduleEventContainer)).getLayoutParams();
                    eventLP.width = dayWidth - 2 * Constants.EVENT_MARGIN_ONE_SIDE;
                    eventLP.height = (int) Math.round((((tempLecture.getEndHour() * 60.0 + tempLecture.getEndMinute()) - (tempLecture.getStartHour() * 60.0 + tempLecture.getStartMinute())) / 60.0) * hourHeight);
                    eventLayout.findViewById(R.id.ScheduleEventContainer).setLayoutParams(eventLP);


                    previousEndTime = (tempLecture.getEndHour() * 60 + tempLecture.getEndMinute());

                }

            View placeholderView = new View(this);
            ((LinearLayout) inflatedLayout.findViewById(R.id.DayInfoContainer)).addView(placeholderView);

            ViewGroup.LayoutParams placeholderViewLP = placeholderView.getLayoutParams();
            placeholderViewLP.width = dayWidth - 2 * Constants.EVENT_MARGIN_ONE_SIDE;
            placeholderViewLP.height = (int) Math.round((((Constants.SCHEDULE_END_HOUR * 60 + 60) - previousEndTime) / 60.0) * hourHeight);
            placeholderView.setLayoutParams(placeholderViewLP);
        }

    }

    private String getWeekday(Calendar date, String Pattern) {
        SimpleDateFormat dayFormat = new SimpleDateFormat(Pattern, Locale.US);
        return dayFormat.format(date.getTime());
    }

    private void openEventDialog(final Lecture lecture, Course course) {
        AlertDialog.Builder edBuilder = new AlertDialog.Builder(this);
        final View edView = getLayoutInflater().inflate(R.layout.dialog_lecture, null);
        ((TextView) edView.findViewById(R.id.CourseName_DialogTitle)).setText(course.getCourse());

        final Context ctx = this;

        mapView = ((MapView) edView.findViewById(R.id.Location_MapView));
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {

                MapsInitializer.initialize(ctx);

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                ArrayList<String> parameters = new ArrayList<>();
                parameters.add("building=" + lecture.getBuilding());

                NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "getBuildingLocation.php", parameters, Cookies);
                new NetworkCommunicator(Constants.HOST + "getBuildingLocation.php", parameters, Cookies) {
                    @Override
                    protected void onPostExecute(Pair<Object, CookieManager> data) {
                        super.onPostExecute(data);

                        if (data == null) {
                            Toast.makeText(ctx, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                        String ErrorCode = Response.get("ErrorCode").toString();

                        if (ErrorCode.equals("0") && !Response.get("Latitude").equals("") && !Response.get("Longitude").equals("")) {
                            final double latitude = Double.parseDouble(Response.get("Latitude").toString());
                            final double longitude = Double.parseDouble(Response.get("Longitude").toString());

                            ((TextView) edView.findViewById(R.id.Location_OpenMapsBtn)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    openGoogleMapsApp(latitude, longitude);
                                }
                            });


                            googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(lecture.getBuilding()));

                            CameraPosition location = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(16).bearing(0).tilt(0).build();
                            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(location));
                        }
                    }
                }.execute();
            }
        });

        edBuilder.setView(edView);
        eDialog = edBuilder.create();
        eDialog.show();
    }

    private void openGoogleMapsApp(double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void closeEventDialog(View v) {
        eDialog.cancel();
    }

    public void expand_Notifications_ERL(View v) {
        View body = ((View) v.getParent()).findViewById(R.id.Notifications_ERL);
        body.setVisibility(body.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    public void expand_Tasks_ERL(View v) {
        View body = ((View) v.getParent()).findViewById(R.id.Tasks_ERL);
        body.setVisibility(body.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    public void expand_Location_ERL(View v) {
        View body = ((View) v.getParent()).findViewById(R.id.Location_ECL);
        body.setVisibility(body.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
}
