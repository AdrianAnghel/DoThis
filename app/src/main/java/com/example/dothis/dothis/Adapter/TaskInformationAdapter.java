package com.example.dothis.dothis.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dothis.dothis.BaseClases.TaskInformations;
import com.example.dothis.dothis.R;
import com.example.dothis.dothis.db.TaskContract;

import java.util.ArrayList;

/**
 * Created by Valentin on 1/14/2017.
 */

public class TaskInformationAdapter extends ArrayAdapter<TaskInformations> {
    private final Context context;
    private final ArrayList<TaskInformations> data;
    private final int textViewResourceId;
    private final int layoutResourceId;

    public TaskInformationAdapter(Context context, int layoutResourceId, int textViewResourceId, ArrayList<TaskInformations> data) {
        super(context, layoutResourceId, textViewResourceId, data);
        this.context = context;
        this.data = data;
        this.textViewResourceId = textViewResourceId;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.task_title = (TextView)row.findViewById(R.id.task_title);
            holder.due_date = (TextView)row.findViewById(R.id.due_date);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }

        TaskInformations taskInformations = data.get(position);

        holder.task_title.setText(taskInformations.getObjectiveText());
        holder.due_date.setText(taskInformations.getDueDate());
        return row;
    }

    static class ViewHolder
    {
        TextView task_title;
        TextView due_date;
        TextView textView3;
    }


}
