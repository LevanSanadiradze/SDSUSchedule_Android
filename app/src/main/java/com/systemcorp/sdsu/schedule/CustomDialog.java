package com.systemcorp.sdsu.schedule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.systemcorp.sdsu.schedule.adapters.SubjectsListAdapter;
import com.systemcorp.sdsu.schedule.fragments.TasksFragment;
import com.systemcorp.sdsu.schedule.helper.Utils;

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
    private ListView listView;
    private SubjectsListAdapter adapter;
    private TasksFragment.onResponse response;
    private LinearLayout linearLayout;

    private String date = "";
    private String title = "";

    public CustomDialog(@NonNull Context context, TasksFragment.onResponse response) {
        super(context);
        this.context = context;
        this.response = response;
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
        listView = (ListView) findViewById(R.id.listview);
        linearLayout = (LinearLayout) findViewById(R.id.textLayout);

        subject.setOnClickListener(this);
        deadline.setOnClickListener(this);
        done.setOnClickListener(this);
        close.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        adapter = new SubjectsListAdapter(context);
        listView.setAdapter(adapter);

        adapter.updateData(App.get().getCourses());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setVisibility(View.GONE);

                title = adapter.getItem(i).getCourse();

                subject.setText(title);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subject:
                if (listView.getVisibility() == View.VISIBLE)
                    listView.setVisibility(View.GONE);
                else
                    listView.setVisibility(View.VISIBLE);
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
            case R.id.textLayout:
                text.requestFocus();
                text.requestFocusFromTouch();
                Utils.showKeyboard(context);
                break;
        }
    }

    private void addTask() {
        ArrayList<String> parameters = new ArrayList<>();
        parameters.add("title=" + title); //todo add title
        parameters.add("text=" + text.getText().toString().trim());
        parameters.add("deadline=" + date);
        new NetworkCommunicator(Constants.HOST + "addTask.php", parameters, App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> success) {
                super.onPostExecute(success);

                //todo
                response.onResponse(true);
                dismiss();
            }
        }.execute();
    }
}
