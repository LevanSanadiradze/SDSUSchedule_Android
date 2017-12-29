package com.example.root.sdsu_gmap;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void Register(View v)
    {
        final EditText email = (EditText) findViewById(R.id.emailField);
        final EditText pwd = (EditText) findViewById(R.id.passwordField);

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Email=" + email.getText());
        parameters.add("Password=" + pwd.getText());

        NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "registration.php", parameters, "");
        try {
            HashMap<String, Object> Response = (HashMap<String, Object>) NC.execute().get().first;
            String ErrorCode = Response.get("ErrorCode").toString();
            String Status = Response.get("Status").toString();

            if(ErrorCode.equals("0") && Status.equals("0"))
                finish();
            else if(ErrorCode.equals("1"))
                return;//TODO
            else if(ErrorCode.equals("2"))
                return;//TODO
            else if(ErrorCode.equals("3"))
                return;//TODO

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
