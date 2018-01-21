package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.models.ClubsAnnouncement;
import com.systemcorp.sdsu.schedule.models.PollDataClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/13/18.
 */

public class ClubsListAdapter extends ArrayAdapter<ClubsAnnouncement> {

    private Context context;
    private LayoutInflater inflater;
    private List<ClubsAnnouncement> data;

    public ClubsListAdapter(@NonNull Context context) {
        super(context, R.layout.clubs_list_row);
        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        data = new ArrayList<>();
    }

    @Nullable
    @Override
    public ClubsAnnouncement getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<ClubsAnnouncement> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        Holder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.clubs_list_row, parent, false);
            holder = new Holder();

            holder.icon = view.findViewById(R.id.icon);
            holder.name = view.findViewById(R.id.name);
            holder.time = view.findViewById(R.id.time);
            holder.text = view.findViewById(R.id.text);
            holder.radioGroup = view.findViewById(R.id.radio_group);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final ClubsAnnouncement announcement = data.get(position);


        holder.text.setText(announcement.getText());
        holder.time.setText(announcement.getTime());
        holder.name.setText(announcement.getName());
        holder.icon.setBackgroundColor(Color.parseColor(announcement.getColor()));

        if (announcement.getPollData() != null) {
            holder.radioGroup.setVisibility(View.VISIBLE);

            for (int i = 0; i < announcement.getPollData().size(); i++) {
                RadioButton radioButton = new RadioButton(context);
                PollDataClass pollData = announcement.getPollData().get(i);
                radioButton.setText(pollData.getAnswer());
                holder.radioGroup.addView(radioButton);

                if (pollData.isVoted()) {
                    holder.radioGroup.check(radioButton.getId());
                }
            }
        }

        return view;
    }

    class Holder {
        ImageView icon;
        TextView name;
        TextView time;
        TextView text;
        RadioGroup radioGroup;
    }
}
