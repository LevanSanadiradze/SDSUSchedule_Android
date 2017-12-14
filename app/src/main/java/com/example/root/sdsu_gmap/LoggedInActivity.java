package com.example.root.sdsu_gmap;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class LoggedInActivity extends AppCompatActivity {

    StudentInformation studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        ScheduleScrollViewController.CreateListeners((HorizontalScrollView) findViewById(R.id.ScheduleScrollView));
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
                    ReadScheduleFile(data.getData());
                }
                break;
        }
    }

    private void ReadScheduleFile(Uri path)
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

            this.studentInfo = ScheduleFileParser.parseScheduleFile(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
