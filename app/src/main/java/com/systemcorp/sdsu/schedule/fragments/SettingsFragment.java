package com.systemcorp.sdsu.schedule.fragments;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.MainActivity;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;

import java.net.CookieManager;
import java.util.ArrayList;

/**
 * Created by giorgi on 1/19/18.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Spinner spinner;
    private Switch remainderSwitch;
    private RelativeLayout remainder;
    private RelativeLayout time;
    private Switch clubsSwitch;
    private RelativeLayout clubs;
    private RelativeLayout logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initView(view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.timesArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;
    }

    private void initView(View view) {
        spinner = (Spinner) view.findViewById(R.id.spinner);
        remainderSwitch = (Switch) view.findViewById(R.id.remainder_switch);
        remainder = (RelativeLayout) view.findViewById(R.id.remainder);
        time = (RelativeLayout) view.findViewById(R.id.time);
        clubsSwitch = (Switch) view.findViewById(R.id.clubs_switch);
        clubs = (RelativeLayout) view.findViewById(R.id.clubs);
        logout = (RelativeLayout) view.findViewById(R.id.logout);

        remainderSwitch.setOnClickListener(this);
        remainder.setOnClickListener(this);
        time.setOnClickListener(this);
        clubsSwitch.setOnClickListener(this);
        clubs.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.remainder:
                remainderSwitch.setChecked(!remainderSwitch.isChecked());
                break;
            case R.id.time:
                spinner.performClick();
                break;
            case R.id.clubs:
                clubsSwitch.setChecked(!clubsSwitch.isChecked());
                break;
            case R.id.logout:
                logout();
                break;
        }
    }

    private void logout() {
        new NetworkCommunicator(Constants.HOST + "logout.php", new ArrayList<String>(), App.get().getCookies()) {
            @Override
            protected void onPostExecute(Pair<Object, CookieManager> success) {
                super.onPostExecute(success);

                App.get().setCookies("");
                App.get().setCourses(null);
                App.get().clearCookies(getActivity());

                Intent mStartActivity = new Intent(getActivity(), MainActivity.class);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mStartActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity(), mPendingIntentId, mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);


                getActivity().finishAffinity();


                System.exit(0);
            }
        }.execute();
    }
}
