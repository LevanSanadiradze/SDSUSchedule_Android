package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.models.TasksData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
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
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.tasks_list_row, parent, false);
            holder = new Holder();

            holder.title = (TextView) view.findViewById(R.id.title);
            holder.details = (TextView) view.findViewById(R.id.details);
            holder.done = (RelativeLayout) view.findViewById(R.id.done);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final TasksData data = getItem(position);
        holder.details.setText(data.getDetails());
        holder.title.setText(data.getTitle());

        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> parameters = new ArrayList<>();
                parameters.add("taskid=" + data.getId());
                new NetworkCommunicator(Constants.HOST + "doneTask.php", parameters, App.get().getCookies()) {
                    @Override
                    protected void onPostExecute(Pair<Object, CookieManager> data) {
                        super.onPostExecute(data);

                        if (data == null) {
                            Toast.makeText(context, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        HashMap<String, Object> Response = (HashMap<String, Object>) data.first;

                        String ErrorCode = Response.get("ErrorCode").toString();

                        if (ErrorCode.equals("0")) {
                            TasksListAdapter.this.data.remove(position);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Error occured", Toast.LENGTH_LONG).show();
                        }
                        //todo add done action
                    }
                }.execute();
            }
        });

        return view;
    }

    class Holder {
        TextView title;
        TextView details;
        RelativeLayout done;
    }
}
