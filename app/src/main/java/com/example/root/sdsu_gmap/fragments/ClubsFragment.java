package com.example.root.sdsu_gmap.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.sdsu_gmap.App;
import com.example.root.sdsu_gmap.Constants;
import com.example.root.sdsu_gmap.NetworkCommunicator;
import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.ClubsListAdapter;
import com.example.root.sdsu_gmap.models.ClubsAnnouncement;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        getData();

        return view;
    }

    private void getData() {
        new NetworkCommunicator(Constants.HOST + "getClubAnnouncements.php", new ArrayList<String>(), App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(getActivity(), "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                ClubsAnnouncement announcement = new ClubsAnnouncement(Response.get("name").toString(), Response.get("time").toString(), Response.get("text").toString(), Response.get("color").toString());
                List<ClubsAnnouncement> llist=new ArrayList<>();
                llist.add(announcement);
                adapter.updateData(llist);
            }
        }.execute();
    }
}
