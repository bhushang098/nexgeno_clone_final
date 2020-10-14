package com.twilio.video.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.twilio.video.app.MainPages.JobDetails;
import com.twilio.video.app.PostedJobResponse.Datum;
import com.twilio.video.app.R;

import java.util.ArrayList;
import java.util.List;

public class PostedJobsAdapter extends  RecyclerView.Adapter<PostedJobsAdapter.PostedJobsAdapterViewHolder> {

    Context context;
    String token;
    String status;
    private List<Datum> JobDataList;

    public PostedJobsAdapter(Context context, String token, String status, List<Datum> jobDataList) {
        this.context = context;
        this.token = token;
        this.status = status;
        JobDataList = jobDataList;
    }

    @NonNull
    @Override
    public PostedJobsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.job_item,parent,false);
        return new PostedJobsAdapter.PostedJobsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostedJobsAdapterViewHolder holder, int position) {
        holder.jobNme.setText(JobDataList.get(position).getTitle());
        holder.location.setText("Location : "+JobDataList.get(position).getLocation());
        holder.qualification.setText("Min Qualification : "+JobDataList.get(position).getMinQualification());
        if(JobDataList.get(position).getRequiredExp()==0)
        {
            holder.experience.setText("Experience : "+" Freshers ");
        }else {
            holder.experience.setText("Experience : "+JobDataList.get(position).getRequiredExp());
        }

        holder.salary.setText("Salary : "+JobDataList.get(position).getSalary());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, JobDetails.class);
                Gson json = new Gson();
                String JobobjStr = json.toJson(JobDataList.get(position));
                i.putExtra("status",status);
                i.putExtra("jobObj",JobobjStr);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return JobDataList.size();
    }

    public class PostedJobsAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView jobNme,location,qualification,experience,salary;
        public PostedJobsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            jobNme = itemView.findViewById(R.id.tv_job_name_on_item);
            location = itemView.findViewById(R.id.tv_job_location_on_item);
            qualification = itemView.findViewById(R.id.tv_minimum_qua_on_item);
            experience = itemView.findViewById(R.id.tv_experience_on_item);
            salary = itemView.findViewById(R.id.tv_salary_on_item);
        }
    }
}
