package com.systemcorp.sdsu.schedule.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.systemcorp.sdsu.schedule.App;
import com.systemcorp.sdsu.schedule.Constants;
import com.systemcorp.sdsu.schedule.NetworkCommunicator;
import com.systemcorp.sdsu.schedule.R;
import com.systemcorp.sdsu.schedule.models.QuizListData;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by giorgi on 2/18/18.
 */

public class QuizListAdapter extends ArrayAdapter<QuizListData> {

    private Context context;
    private LayoutInflater inflater;
    private List<QuizListData> data;

    public QuizListAdapter(@NonNull Context context) {
        super(context, R.layout.quiz_list_item);

        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new ArrayList<>();
    }

    @Nullable
    @Override
    public QuizListData getItem(int position) {
        return data.get(position);
    }

    public void updateData(List<QuizListData> data) {
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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        final Holder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.quiz_list_item, parent, false);
            holder = new Holder();

            holder.subject = (TextView) view.findViewById(R.id.subject);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.code = (EditText) view.findViewById(R.id.code);
            holder.start = (Button) view.findViewById(R.id.start);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.title.setText(getItem(position).getTitle());
        holder.subject.setText(getItem(position).getCourse());
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(position).isActive()) {
                    ArrayList<String> parameters = new ArrayList<>();
                    parameters.add("quizid=" + getItem(position).getId());
                    parameters.add("quizpassword=" + holder.code.getText().toString().trim());

                    new NetworkCommunicator(Constants.HOST + "getQuizQuestions.php", parameters, App.get().getCookies()) {
                        @Override
                        protected void onPostExecute(Pair<Object, CookieManager> data) {
                            super.onPostExecute(data);

                            if (data == null) {
                                Toast.makeText(context, "Unexpected Error, Please check your internet connection.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }.execute();
                } else {
                    Toast.makeText(context, "The quiz is not active", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    class Holder {
        TextView subject;
        TextView title;
        EditText code;
        Button start;
    }
}
