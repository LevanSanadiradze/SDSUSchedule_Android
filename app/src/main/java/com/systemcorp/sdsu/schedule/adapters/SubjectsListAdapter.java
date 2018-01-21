package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/20/18.
 */

public class SubjectsListAdapter extends ArrayAdapter<Course> {

    private Context context;
    private LayoutInflater inflater;
    private List<Course> data;

    public SubjectsListAdapter(@NonNull Context context) {
        super(context, R.layout.subjects_list_row);

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
    }

    @Nullable
    @Override
    public Course getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<Course> data) {
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
            view = inflater.inflate(R.layout.subjects_list_row, parent, false);
            holder = new Holder();

            holder.title = (TextView) view.findViewById(R.id.title);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.title.setText(getItem(position).getCourse());

        return view;
    }

    class Holder {
        TextView title;
    }
}
