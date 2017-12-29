package com.example.root.sdsu_gmap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String StoredCookies = getCookiesOnInternalStorage();

        if(!StoredCookies.equals(""))
        {
            Intent LoggedInActivity = new Intent(getBaseContext(), LoggedInActivity.class);
            LoggedInActivity.putExtra("Cookies", StoredCookies);
            startActivity(LoggedInActivity);
        }
    }

    public void Registration(View v)
    {
        Intent RegActivity = new Intent(this, RegistrationActivity.class);
        startActivity(RegActivity);
    }

    public void Login(View v)
    {
        final EditText login = (EditText) findViewById(R.id.LoginField);
        final EditText pass = (EditText) findViewById(R.id.PasswordField);

        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Email=" + login.getText());
        parameters.add("Password=" + pass.getText());

        NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "login.php", parameters, "");
        try {

            Pair<Object, CookieManager> data = NC.execute().get();
            HashMap<String, Object> Response = (HashMap<String, Object>) data.first;
            CookieManager cookieManager = data.second;

            String ErrorCode = Response.get("ErrorCode").toString();
            String Status = Response.get("Status").toString();

            if(ErrorCode.equals("0") && Status.equals("0"))
            {
                String StoredCookies = saveCookiesOnInternalStorage(cookieManager);

                Intent LoggedInActivity = new Intent(getBaseContext(), LoggedInActivity.class);
                LoggedInActivity.putExtra("Cookies", StoredCookies);
                startActivity(LoggedInActivity);
            }
            else if(ErrorCode.equals("1"))
            {
                //TODO
            }
            else if(ErrorCode.equals("2"))
            {
                //TODO;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String saveCookiesOnInternalStorage(CookieManager cookieManager)
    {
        String FILENAME = "Cookies";
        String value = "";

        if (cookieManager.getCookieStore().getCookies().size() > 0) {
            value = TextUtils.join(";",  cookieManager.getCookieStore().getCookies());
        }

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            fos.write(value.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;

    }

    private String getCookiesOnInternalStorage()
    {
        String FILENAME = "Cookies";
        String value = "";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));

            value = br.readLine();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;

    }
}
