package com.example.root.sdsu_gmap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.root.sdsu_gmap.R;
import com.example.root.sdsu_gmap.models.AnnouncementsData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 1/14/18.
 */

public class AnnouncementsAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<AnnouncementsData> data;
    private LayoutInflater inflater;

    public AnnouncementsAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
    }

    public void updateData(List<AnnouncementsData> data) {
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public AnnouncementsData getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.announcements_list_item, null, false);
        }

        TextView date = (TextView) view.findViewById(R.id.date);
        TextView title = (TextView) view.findViewById(R.id.title);

        date.setText(getGroup(i).getDate());
        title.setText(getGroup(i).getTitle());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.announcements_list_item_details, null, false);
        }

        TextView text = (TextView) view.findViewById(R.id.text);

        text.setText(getGroup(i).getText());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
