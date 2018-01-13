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
import com.example.root.sdsu_gmap.models.CoursesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/13/18.
 */

public class CoursesListAdapter extends ArrayAdapter<CoursesData> {

    private Context context;
    private LayoutInflater inflater;
    private List<CoursesData> data;

    public CoursesListAdapter(@NonNull Context context) {
        super(context, R.layout.fragment_courses);
        this.context = context;
        data = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public CoursesData getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<CoursesData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        Holder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.clubs_list_row, parent, false);
            holder = new Holder();

            holder.name = view.findViewById(R.id.name);
            holder.professor = view.findViewById(R.id.professor);
            holder.schedule = view.findViewById(R.id.schedule);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final CoursesData course = data.get(position);

        holder.name.setText(course.getName());
        holder.schedule.setText(course.getSchedule());
        holder.professor.setText(course.getProfessor());

        return view;
    }

    class Holder {
        TextView name;
        TextView professor;
        TextView schedule;
    }
}
