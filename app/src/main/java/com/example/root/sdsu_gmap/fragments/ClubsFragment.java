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

                String ErrorCode = Response.get("ErrorCode").toString();
                if (ErrorCode.equals("0")) {
                    try {
                        HashMap<String, Object> map = (HashMap<String, Object>) Response.get("ClubAnnouncements");

                        List<ClubsAnnouncement> list = new ArrayList<>();
                        for (int i = 0; i < map.size(); i++) {
                            HashMap<String, Object> jsonData = (HashMap<String, Object>) map.get("JSONArray" + i);
                            ClubsAnnouncement task = new ClubsAnnouncement(jsonData.get("clubannouncement_id").toString(), jsonData.get("myvoteid").toString(), jsonData.get("seen").toString(), jsonData.get("name").toString(), jsonData.get("time").toString(), jsonData.get("text").toString(), jsonData.get("color").toString());

                            List<String> pollData = new ArrayList<>();
                            HashMap<String, Object> pollDataMap = (HashMap<String, Object>) jsonData.get("polldata");
                            for (int j = 0; j < pollDataMap.size(); j++) {
                                pollData.add(pollDataMap.get("" + (j + 1)).toString());
                            }

                            List<Integer> pollAnswers = new ArrayList<>();
                            HashMap<String, Object> pollAnswersMap = (HashMap<String, Object>) jsonData.get("pollanswers");
                            for (int j = 0; j < pollAnswersMap.size(); j++) {
                                pollAnswers.add(Integer.parseInt(pollAnswersMap.get("" + (j + 1)).toString()));
                            }

                            task.setPollAnswers(pollAnswers);
                            task.setPollData(pollData);

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
}
