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
import com.systemcorp.sdsu.schedule.adapters.QuizListAdapter;
import com.systemcorp.sdsu.schedule.models.QuizListData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by giorgi on 2/18/18.
 */

public class QuizListFragment extends Fragment {

    private ListView listView;
    private QuizListAdapter adapter;
    private List<QuizListData> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        adapter = new QuizListAdapter(getActivity());
        listView = (ListView) view.findViewById(R.id.quiz_list);
        listView.setAdapter(adapter);

        data = new ArrayList<>();
        getData();

        return view;
    }

    private void getData() {
        new NetworkCommunicator(Constants.HOST + "getMyQuizes.php", new ArrayList<String>(), App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> data) {
                super.onPostExecute(data);

                if (data == null) {
                    Toast.makeText(getActivity(), "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                    return;
                }

                HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                for (int i = 0; i < Response.size(); i++) {
                    HashMap<String, Object> jsonData = (HashMap<String, Object>) Response.get("JSONArray" + i);
                    QuizListData listData = new QuizListData(jsonData.get("Course").toString(), Double.parseDouble(jsonData.get("TimePerQuestion").toString()), jsonData.get("Title").toString(), jsonData.get("Active").toString(), jsonData.get("id").toString());
                    QuizListFragment.this.data.add(listData);
                }

                adapter.updateData(QuizListFragment.this.data);
            }
        }.execute();
    }
}
