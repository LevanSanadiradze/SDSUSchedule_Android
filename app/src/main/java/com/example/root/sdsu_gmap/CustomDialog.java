package com.example.root.sdsu_gmap;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by giorgi on 1/15/18.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private Button subject;
    private Button deadline;
    private Button done;
    private Button close;
    private EditText text;

    private String date;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_tasks);

        subject = (Button) findViewById(R.id.subject);
        deadline = (Button) findViewById(R.id.deadline);
        done = (Button) findViewById(R.id.done);
        close = (Button) findViewById(R.id.CloseButton);
        text = (EditText) findViewById(R.id.editText);

        subject.setOnClickListener(this);
        deadline.setOnClickListener(this);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject:

                break;
            case R.id.deadline:
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date = i + "-" + (i1 + 1) + "-" + i2;
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.CloseButton:
                dismiss();
                break;
            case R.id.done:
                addTask();
                break;
        }
    }

    private void addTask() {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("title="); //todo add title
        parameters.add("text=" + text.getText().toString().trim());
        parameters.add("deadline=" + date);
        new NetworkCommunicator(Constants.HOST + "addTask.php", parameters, App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> success) {
                super.onPostExecute(success);

                //todo
            }
        }.execute();
    }
}
