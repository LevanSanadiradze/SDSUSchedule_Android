package com.systemcorp.sdsu.schedule.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.adapters.ClubsListAdapter;
import com.systemcorp.sdsu.schedule.models.ClubsAnnouncement;
import com.systemcorp.sdsu.schedule.models.PollDataClass;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    HashMap<String, Object> cAnnouncements = (HashMap<String, Object>) Response.get("ClubAnnouncements");

                    List<ClubsAnnouncement> clubAnnouncements = new ArrayList<>();

                    for (Map.Entry<String, Object> entry : cAnnouncements.entrySet()) {
                        HashMap<String, Object> temp = (HashMap<String, Object>) entry.getValue();

                        List<PollDataClass> fullPollData = new ArrayList<>();

                        if (!temp.get("polldata").equals("")) {

                            HashMap<String, Object> tempPollData = (HashMap<String, Object>) temp.get("polldata");
                            HashMap<String, Object> tempPollAnswers = (HashMap<String, Object>) temp.get("pollanswers");


                            for (Map.Entry<String, Object> pollEntry : tempPollData.entrySet()) {
                                int votes = tempPollAnswers.get(pollEntry.getKey()) == null ? 0 : Integer.parseInt(tempPollAnswers.get(pollEntry.getKey()).toString());

                                PollDataClass tempPDC = new PollDataClass(
                                        Integer.parseInt(pollEntry.getKey()),
                                        pollEntry.getValue().toString(),
                                        votes,
                                        temp.get("myvoteid").toString().equals(pollEntry.getKey()));

                                fullPollData.add(tempPDC);

                            }
                        }

                        ClubsAnnouncement tempAnnouncement = new ClubsAnnouncement(
                                Integer.parseInt(temp.get("clubannouncement_id").toString()),
                                temp.get("seen") == "1" ? true : false,
                                temp.get("name").toString(),
                                temp.get("time").toString(),
                                temp.get("text").toString(),
                                temp.get("color").toString(),
                                fullPollData.size() == 0 ? null : fullPollData);

                        clubAnnouncements.add(tempAnnouncement);
                    }

                    adapter.updateData(clubAnnouncements);
                } else {
                    Toast.makeText(getActivity(), "Error occured", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
}
