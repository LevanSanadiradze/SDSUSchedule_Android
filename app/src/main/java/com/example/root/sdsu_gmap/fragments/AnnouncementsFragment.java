package com.example.root.sdsu_gmap.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.AnnouncementsAdapter;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsFragment extends Fragment {

    private ExpandableListView listView;
    private AnnouncementsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_announcements,container,false);

        adapter=new AnnouncementsAdapter(getActivity());
        listView=(ExpandableListView)view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return view;
    }
}
