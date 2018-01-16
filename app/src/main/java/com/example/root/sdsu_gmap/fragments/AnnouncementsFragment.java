package com.example.root.sdsu_gmap.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.root.sdsu_gmap.App;
import com.example.root.sdsu_gmap.Constants;
import com.example.root.sdsu_gmap.NetworkCommunicator;
import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.adapters.AnnouncementsAdapter;
import com.example.root.sdsu_gmap.models.AnnouncementsData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsFragment extends Fragment {

    private ExpandableListView listView;
    private AnnouncementsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);

        adapter = new AnnouncementsAdapter(getActivity());
        listView = (ExpandableListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                //todo change background color

                return false;
            }
        });

        getData();

        return view;
    }

    private void getData() {
        new NetworkCommunicator(Constants.HOST + "getAnnouncements.php", new ArrayList<String>(), App.get().getCookies()) {
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
                        HashMap<String, Object> map = (HashMap<String, Object>) Response.get("Announcements");
                        List<AnnouncementsData> list = new ArrayList<>();
                        for (int i = 0; i < map.size(); i++) {
                            HashMap<String, Object> jsonData = (HashMap<String, Object>) map.get("JSONArray" + i);
                            AnnouncementsData announcement = new AnnouncementsData(jsonData.get("time").toString(), jsonData.get("title").toString(), jsonData.get("text").toString(), jsonData.get("seen").toString(), jsonData.get("Announcementid").toString());
                            list.add(announcement);
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
}
