package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.adapters.CoursesListAdapter;

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

        adapter.updateData(App.get().getCourses());

        return view;
    }
}
