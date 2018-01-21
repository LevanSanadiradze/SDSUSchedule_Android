package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.CustomDialog;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.adapters.TasksListAdapter;
import com.systemcorp.sdsu.schedule.models.TasksData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by giorgi on 1/15/18.
 */

public class TasksFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private TasksListAdapter adapter;
    private ImageView add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        adapter = new TasksListAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listview);
        add = (ImageView) view.findViewById(R.id.add);

        add.setOnClickListener(this);
        listView.setAdapter(adapter);

        getData();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                addTask();
                break;
        }
    }

    private void addTask() {
        CustomDialog dialog = new CustomDialog(getActivity(), new onResponse() {
            @Override
            public void onResponse(boolean response) {
                if (response) {
                    getData();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void getData() {
        new NetworkCommunicator(Constants.HOST + "getTasks.php", new ArrayList<String>(), App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(getActivity(), "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                String ErrorCode = Response.get("ErrorCode").toString();
                if (ErrorCode.equals("0")) {
                    try {
                        HashMap<String, Object> map = (HashMap<String, Object>) Response.get("Tasks");
                        List<TasksData> list = new ArrayList<>();
                        for (int i = 0; i < map.size(); i++) {
                            HashMap<String, Object> jsonData = (HashMap<String, Object>) map.get("JSONArray" + i);
                            TasksData task = new TasksData(jsonData.get("Taskid").toString(), jsonData.get("time").toString(), jsonData.get("title").toString(), jsonData.get("text").toString());
                            list.add(task);
                        }

                        adapter.updateData(list);
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public interface onResponse {
        public void onResponse(boolean response);
    }
}
