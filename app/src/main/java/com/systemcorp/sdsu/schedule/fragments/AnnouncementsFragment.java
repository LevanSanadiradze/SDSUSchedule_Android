package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.adapters.AnnouncementsAdapter;
import com.systemcorp.sdsu.schedule.models.AnnouncementsData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsFragment extends Fragment {

    private ListView listView;
    private AnnouncementsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);

        adapter = new AnnouncementsAdapter(getActivity());
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                ArrayList<String> parameters = new ArrayList<>();
                parameters.add("announcementid=" + adapter.getItem(i).getId());

                final TextView textView = view.findViewById(R.id.text);
                if (view.findViewById(R.id.text).getVisibility() == View.VISIBLE) {
                    textView.setVisibility(View.GONE);
                    view.setBackgroundResource(R.drawable.announcement_list_row_background);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    view.setBackgroundResource(R.drawable.announcement_list_child_background);
                }

                if (!adapter.getItem(i).isSeen()) {
                    new NetworkCommunicator(Constants.HOST + "voteOnClubAnnouncement.php", parameters, App.get().getCookies()) {
                        @Override
                        protected void onPostExecute(Pair<Object, CookieManager> data) {
                            super.onPostExecute(data);

                            if (data == null) {
                                Toast.makeText(getActivity(), "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                            String ErrorCode = Response.get("ErrorCode").toString();

                            if (!ErrorCode.equals("0")) {
                                Toast.makeText(getActivity(), "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                textView.setTypeface(null, Typeface.NORMAL);
                                TextView date = (TextView) view.findViewById(R.id.date);
                                TextView title = (TextView) view.findViewById(R.id.title);
                                title.setTypeface(null, Typeface.NORMAL);
                                date.setTypeface(null, Typeface.NORMAL);
                            }
                        }
                    }.execute();
                }
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
