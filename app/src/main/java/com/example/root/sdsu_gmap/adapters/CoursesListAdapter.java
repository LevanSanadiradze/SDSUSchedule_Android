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
import com.example.root.sdsu_gmap.models.Course;
import com.example.root.sdsu_gmap.models.Lecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/13/18.
 */

public class CoursesListAdapter extends ArrayAdapter<Course> {

    private Context context;
    private LayoutInflater inflater;
    private List<Course> data;

    public CoursesListAdapter(@NonNull Context context) {
        super(context, R.layout.fragment_courses);
        this.context = context;
        data = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.courses_list_row, parent, false);
            holder = new Holder();

            holder.name = view.findViewById(R.id.name);
            holder.professor = view.findViewById(R.id.professor);
            holder.schedule = view.findViewById(R.id.schedule);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final Course course = data.get(position);

        String schedule = "";
        for (int i = 0; i < course.getLectures().size(); i++) {
            Lecture lecture = course.getLectures().get(i);
            schedule = schedule + lecture.getStartHour() + ":" + lecture.getStartMinute() + "-" + lecture.getEndHour() + ":" + lecture.getEndMinute() +
                    " " + getDay(lecture.getDay()) + "\n";
        }
        holder.name.setText(course.getCourse());
        holder.schedule.setText(schedule);
        holder.professor.setText(course.getInstructor());

        return view;
    }

    class Holder {
        TextView name;
        TextView professor;
        TextView schedule;
    }

    private String getDay(String day) {
        switch (day) {
            case "M":
                return "Monday";
            case "T":
                return "Tuesday";
            case "W":
                return "Wednesday";
            case "Th":
                return "Thursday";
            case "F":
                return "Friday";
            case "S":
                return "Saturday";
            default:
                return "";
        }
    }
}
