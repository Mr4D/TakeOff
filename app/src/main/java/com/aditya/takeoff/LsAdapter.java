package com.aditya.takeoff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LsAdapter extends ArrayAdapter<Job> {
    private LayoutInflater mInflater;
    private ArrayList<Job> jobs;
    private int mViewResourceId;

    public LsAdapter(Context context, int textViewResourceId, ArrayList<Job> jobs) {
        super(context, textViewResourceId, jobs);
        this.jobs = jobs;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        convertView =  mInflater.inflate(mViewResourceId, null);
        Job job = jobs.get(position);
        if(job !=  null) {
            TextView username = (TextView) convertView.findViewById(R.id.textUsername);
            TextView task = (TextView) convertView.findViewById(R.id.textTask);
            TextView date = (TextView) convertView.findViewById(R.id.textDate);
            TextView time = (TextView) convertView.findViewById(R.id.textTime);
            if(username != null) {
                username.setText(job.getUsername());
            }
            if(task != null) {
                task.setText(job.getTask());
            }
            if(date != null) {
                date.setText(job.getDate());
            }
            if(time != null) {
                time.setText(job.getTime());
            }
        }
        return convertView;
    }

}
