package com.systemcorp.sdsu.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private EditText email;
    private EditText password;
    private TextView forgotPassword;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        App.get().setCookies(getCookiesOnInternalStorage());

        if (!App.get().getCookies().equals("")) {
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/administration");
            Intent LoggedInActivity = new Intent(getBaseContext(), LoggedInActivity.class);
            startActivity(LoggedInActivity);
            finish();
        }
    }

    private void initView() {
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "Please enter Red Id and Password", Toast.LENGTH_LONG).show();
                } else {
                    Login();
                }
                break;
            case R.id.register:
                Intent RegActivity = new Intent(this, RegistrationActivity.class);
                startActivity(RegActivity);
                break;
            case R.id.forgot_password:
                Intent forgotIntent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotIntent);
                break;
        }
    }

    private void Login() {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Email=" + email.getText().toString().trim());
        parameters.add("Password=" + password.getText().toString().trim());

        new NetworkCommunicator(Constants.HOST + "login.php", parameters, "") {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(MainActivity.this, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;
                CookieManager cookieManager = data.second;

                String ErrorCode = Response.get("ErrorCode").toString();
                String Status = Response.get("Status").toString();

                if (ErrorCode.equals("0") && Status.equals("0")) {
                    App.get().setCookies(saveCookiesOnInternalStorage(cookieManager));

                    Intent LoggedInActivity = new Intent(getBaseContext(), LoggedInActivity.class);
                    startActivity(LoggedInActivity);
                    finish();
                } else if (ErrorCode.equals("1")) {
                    //TODO
                } else if (ErrorCode.equals("2")) {
                    //TODO;
                }
            }
        }.execute();
    }

    private String saveCookiesOnInternalStorage(CookieManager cookieManager) {
        String FILENAME = Constants.COOKIES_FILE_NAME;
        String value = "";

        if (cookieManager.getCookieStore().getCookies().size() > 0) {
            value = TextUtils.join(";", cookieManager.getCookieStore().getCookies());
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

    private String getCookiesOnInternalStorage() {
        String FILENAME = Constants.COOKIES_FILE_NAME;
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
