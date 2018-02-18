package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/19/18.
 */

public class MenuAdapter extends ArrayAdapter<MenuItem> {

    private Context context;
    private LayoutInflater inflater;
    private List<MenuItem> data;

    public MenuAdapter(@NonNull Context context) {
        super(context, R.layout.menu_item);

        this.context = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        data = new ArrayList<>();

        data.add(new MenuItem(R.drawable.ic_schedule, "Schedule"));
        data.add(new MenuItem(R.drawable.ic_announcement, "Announcements"));
        data.add(new MenuItem(R.drawable.ic_courses, "Courses"));
        data.add(new MenuItem(R.drawable.ic_tasks, "Tasks"));
        data.add(new MenuItem(R.drawable.ic_events, "Quizzes"));
        data.add(new MenuItem(R.drawable.ic_clubs, "Clubs"));
        data.add(new MenuItem(R.drawable.ic_contact, "Contact"));

        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public MenuItem getItem(int position) {
        return data.get(position);
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
            view = inflater.inflate(R.layout.menu_item, parent, false);
            holder = new Holder();

            holder.icon = view.findViewById(R.id.icon);
            holder.title = view.findViewById(R.id.title);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final MenuItem item = data.get(position);

        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIcon());

        return view;
    }

    class Holder {
        ImageView icon;
        TextView title;
    }
}
