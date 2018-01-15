package com.example.root.sdsu_gmap.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.models.TasksData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/15/18.
 */

public class TasksListAdapter extends ArrayAdapter<TasksData> {
    private Context context;
    private LayoutInflater inflater;
    private List<TasksData> data;

    public TasksListAdapter(@NonNull Context context) {
        super(context, R.layout.tasks_list_row);

        this.context = context;
        data = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Nullable
    @Override
    public TasksData getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<TasksData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.tasks_list_row, parent, false);
            holder = new Holder();

            holder.title = (TextView) view.findViewById(R.id.title);
            holder.details = (TextView) view.findViewById(R.id.details);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final TasksData data = getItem(position);
        holder.details.setText(data.getDetails());
        holder.title.setText(data.getTitle());

        return view;
    }

    class Holder {
        TextView title;
        TextView details;
    }
}
