package com.example.root.sdsu_gmap.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.ClubsListAdapter;

/**
 * Created by giorgi on 1/13/18.
 */

public class ClubsFragment extends Fragment {

    private ListView listView;
    private ClubsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clubs, container, false);

        adapter = new ClubsListAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        return view;
    }
}
