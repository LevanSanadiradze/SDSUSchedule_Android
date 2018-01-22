package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.R;

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
                App.get().logout(getActivity());
                break;
        }
    }
}
