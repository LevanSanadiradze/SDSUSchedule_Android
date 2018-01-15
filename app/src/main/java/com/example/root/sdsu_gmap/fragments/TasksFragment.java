package com.example.root.sdsu_gmap.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.TasksListAdapter;

/**
 * Created by giorgi on 1/15/18.
 */

public class TasksFragment extends Fragment {

    private ListView listView;
    private TasksListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        adapter = new TasksListAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return view;
    }
}
