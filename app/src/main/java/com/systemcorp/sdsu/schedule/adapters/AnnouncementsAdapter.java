package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.helper.Utils;
import com.systemcorp.sdsu.schedule.models.AnnouncementsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsAdapter extends ArrayAdapter<AnnouncementsData> {

    private Context context;
    private List<AnnouncementsData> data;
    private LayoutInflater inflater;

    public AnnouncementsAdapter(Context context) {
        super(context, R.layout.announcements_list_item);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
    }

    public void updateData(List<AnnouncementsData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Nullable
    @Override
    public AnnouncementsData getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        Holder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.announcements_list_item, parent, false);
            holder = new Holder();

            holder.title = view.findViewById(R.id.title);
            holder.time = view.findViewById(R.id.date);
            holder.text = view.findViewById(R.id.text);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final AnnouncementsData announcement = getItem(position);

        holder.time.setText(Utils.formatDate(announcement.getDate()));
        holder.title.setText(announcement.getTitle());
        holder.text.setText(announcement.getText());

        if (announcement.isSeen()) {
            holder.title.setTypeface(null, Typeface.BOLD);
            holder.time.setTypeface(null, Typeface.BOLD);
            holder.text.setTypeface(null, Typeface.BOLD);
        }

        return view;
    }

    class Holder {
        TextView title;
        TextView time;
        TextView text;
    }
}
