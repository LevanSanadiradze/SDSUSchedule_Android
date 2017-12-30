package com.example.root.sdsu_gmap;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

    }
}
