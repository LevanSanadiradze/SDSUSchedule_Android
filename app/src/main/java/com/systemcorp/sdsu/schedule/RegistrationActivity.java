package com.systemcorp.sdsu.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private CheckBox checkBox;
    private Button register;
    private TextView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initView();
    }

    private void initView() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        register = (Button) findViewById(R.id.register);
        terms = (TextView) findViewById(R.id.terms);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                if (TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(password.getText().toString().trim())
                        || !password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())
                        || !checkBox.isChecked()) {
                    Toast.makeText(RegistrationActivity.this, "Please enter details", Toast.LENGTH_LONG).show();
                } else {
                    Register();
                }
                break;
        }
    }

//    private void Register() {
//
//        ArrayList<String> parameters = new ArrayList<>();
//        parameters.add("Email=" + email.getText());
//        parameters.add("Password=" + password.getText());
//
//        NetworkCommunicator NC = new NetworkCommunicator(Constants.HOST + "registration.php", parameters, "");
//        try {
//            HashMap<String, Object> Response = (HashMap<String, Object>) NC.execute().get().first;
//            String ErrorCode = Response.get("ErrorCode").toString();
//            String Status = Response.get("Status").toString();
//
//            if (ErrorCode.equals("0") && Status.equals("0"))
//                finish();
//            else if (ErrorCode.equals("1"))
//                return;//TODO
//            else if (ErrorCode.equals("2"))
//                return;//TODO
//            else if (ErrorCode.equals("3"))
//                return;//TODO
//
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//    }

    private void Register() {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("Email=" + email.getText());
        parameters.add("Password=" + password.getText());

        new NetworkCommunicator(Constants.HOST + "registration.php", parameters, "") {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(RegistrationActivity.this, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                String ErrorCode = Response.get("ErrorCode").toString();
                String Status = Response.get("Status").toString();

                if (ErrorCode.equals("0") && Status.equals("0")) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (ErrorCode.equals("1"))
                    return;//TODO
                else if (ErrorCode.equals("2"))
                    return;//TODO
                else if (ErrorCode.equals("3"))
                    return;//TODO
            }
        }.execute();
    }
}
