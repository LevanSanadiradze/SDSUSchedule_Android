package com.example.root.sdsu_gmap.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.CoursesListAdapter;

/**
 * Created by giorgi on 1/13/18.
 */

public class CoursesFragment extends Fragment {

    private ListView listView;
    private CoursesListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        adapter = new CoursesListAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return view;
    }
}
