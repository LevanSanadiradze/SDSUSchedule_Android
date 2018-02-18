package com.systemcorp.sdsu.schedule.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.systemcorp.sdsu.schedule.R;

/**
 * Created by giorgi on 1/28/18.
 */

public class QuizFragment extends Fragment implements View.OnClickListener {

    private LinearLayout a;
    private LinearLayout b;
    private LinearLayout c;
    private LinearLayout d;
    private TextView aText;
    private TextView bText;
    private TextView cText;
    private TextView dText;
    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        a = (LinearLayout) view.findViewById(R.id.a_option_holder);
        b = (LinearLayout) view.findViewById(R.id.b_option_holder);
        c = (LinearLayout) view.findViewById(R.id.c_option_holder);
        d = (LinearLayout) view.findViewById(R.id.d_option_holder);
        aText = (TextView) view.findViewById(R.id.a_text);
        bText = (TextView) view.findViewById(R.id.b_text);
        cText = (TextView) view.findViewById(R.id.c_text);
        dText = (TextView) view.findViewById(R.id.d_text);
        submit = (Button) view.findViewById(R.id.submit);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:

                break;
            case R.id.a_option_holder:
                a.setBackgroundColor(Color.parseColor("#9B0055"));
                b.setBackgroundColor(Color.parseColor("#FFFFFF"));
                c.setBackgroundColor(Color.parseColor("#FFFFFF"));
                d.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.b_option_holder:
                b.setBackgroundColor(Color.parseColor("#9B0055"));
                a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                c.setBackgroundColor(Color.parseColor("#FFFFFF"));
                d.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.c_option_holder:
                c.setBackgroundColor(Color.parseColor("#9B0055"));
                b.setBackgroundColor(Color.parseColor("#FFFFFF"));
                a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                d.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.d_option_holder:
                d.setBackgroundColor(Color.parseColor("#9B0055"));
                b.setBackgroundColor(Color.parseColor("#FFFFFF"));
                c.setBackgroundColor(Color.parseColor("#FFFFFF"));
                a.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
}
