package com.twilio.video.app.subMainPages;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.twilio.video.app.ApiModals.MakeClassResponse;
import com.twilio.video.app.ApiModals.UserObj;
import com.twilio.video.app.JobsResponse.Datum;
import com.twilio.video.app.MainPages.JobsPage;
import com.twilio.video.app.R;
import com.twilio.video.app.RetrifitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostNewJob extends AppCompatActivity {

    EditText etJobName, etDesc, etLocation, etExperience, etMinQualification, etSalary,etCompany;
    String jobName, desc, location, experience, minQualification, salary, lastDate,company;
    TextView tvLastDate;

    Button btnCommit, btnDelete;
    DatePickerDialog lastDatePicker;
    Dialog successOnApiDialog;
    PopupWindow progressPopup;

    UserObj userobj;
    Datum jobObj;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_job);
        setUi();
        Gson gson = new Gson();
        successOnApiDialog = new Dialog(this);
        userobj = gson.fromJson(getIntent().getStringExtra("userObj"), UserObj.class);
        if (getIntent().getStringExtra("jobObj").equalsIgnoreCase("0"))
            jobObj = null;
        else
            jobObj = gson.fromJson(getIntent().getStringExtra("jobObj"), Datum.class);

        token = getIntent().getStringExtra("token");

        btnCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                jobName = etJobName.getText().toString();
                desc = etDesc.getText().toString();
                location = etLocation.getText().toString();
                experience = etExperience.getText().toString();
                minQualification = etMinQualification.getText().toString();
                salary = etSalary.getText().toString();
                lastDate = etSalary.getText().toString();
                company = etCompany.getText().toString();

                if (btnCommit.getText().toString().equalsIgnoreCase("Post Job")) {
                    //TpDo make New Job By Api

                    if (jobName.isEmpty() || desc.isEmpty() || location.isEmpty()
                            || experience.isEmpty() || minQualification.isEmpty() || salary.isEmpty()
                            || lastDate.isEmpty()|| company.isEmpty()) {
                        Toast.makeText(PostNewJob.this, "Provide All Information", Toast.LENGTH_SHORT).show();
                    } else {
                        makeJobByApi();
                    }

                } else {

                    //ToDo Update Job By Api

                    if (jobName.isEmpty() || desc.isEmpty() || location.isEmpty()
                            || experience.isEmpty() || minQualification.isEmpty() || salary.isEmpty()
                            || lastDate.isEmpty()) {
                        Toast.makeText(PostNewJob.this, "Provide All Information", Toast.LENGTH_SHORT).show();
                    } else {
                        editJobByApi();
                    }

                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJobyApi();

            }
        });

        tvLastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                lastDatePicker = new DatePickerDialog(PostNewJob.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tvLastDate.setText("         " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                lastDate = String.valueOf(year) + "-" + String.valueOf(monthOfYear) + "-" + String.valueOf(dayOfMonth);
                            }
                        }, year, month, day);
                lastDatePicker.show();
            }
        });

        if (jobObj == null) {
            btnCommit.setText("Post Job");

        } else {
            btnCommit.setText("Update Job");
            btnDelete.setVisibility(View.VISIBLE);

            etJobName.setText(jobObj.getTitle());
            etDesc.setText(jobObj.getDescription());
            etLocation.setText(jobObj.getLocation());
            etExperience.setText(jobObj.getRequiredExp().toString());
            etMinQualification.setText(jobObj.getMinQualification());
            etSalary.setText(jobObj.getSalary());

            tvLastDate.setText(jobObj.getLastDate());
        }


    }

    private void deleteJobyApi() {
        startProgressPopup(PostNewJob.this);

        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .gteJobsApi().deleteJob("job/delete"+jobObj.getId().toString(),token);

        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Response>>",response.raw().toString());
                progressPopup.dismiss();

                if(response.body()!=null)
                {
                    if(response.body().getStatus())
                    {
                        Toast.makeText(PostNewJob.this, "Job Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PostNewJob.this, JobsPage.class));
                        finish();
                    }else {
                        Toast.makeText(PostNewJob.this, "Can Not Delete Job", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {
                progressPopup.dismiss();
                Log.d("Error>>",t.toString());
            }
        });

    }


    private void makeJobByApi() {
        startProgressPopup(PostNewJob.this);

        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .gteJobsApi().makeNewJob(token, jobName, desc, location,company, experience, minQualification
                        , salary, lastDate);

        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Response>>", response.raw().toString());
                progressPopup.dismiss();

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(PostNewJob.this, "Job Created SuccessFully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(PostNewJob.this, "Error While Posting Job", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {
                Log.d("Error>>", t.toString());
                progressPopup.dismiss();
            }
        });
    }

    private void editJobByApi() {
        startProgressPopup(PostNewJob.this);

        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .gteJobsApi().editJob(token,jobObj.getId().toString(), jobName,company, desc, location, experience, minQualification
                        , salary, lastDate);

        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Response>>", response.raw().toString());
                progressPopup.dismiss();

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(PostNewJob.this, "Changes Made", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(PostNewJob.this, "Error While Updating Job", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {
                Log.d("Error>>", t.toString());
                progressPopup.dismiss();
            }
        });
    }

    private void startProgressPopup(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.progres_popup,
                null); // inflating popup layout
        progressPopup = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        progressPopup.setAnimationStyle(android.R.style.Animation_Dialog);
        progressPopup.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
    }

    private void setUi() {
        etJobName = findViewById(R.id.et_new_job_name);
        etDesc = findViewById(R.id.et_new_job_desc);
        etLocation = findViewById(R.id.et_new_job_location);
        etExperience = findViewById(R.id.et_new_job_req_expe);
        etMinQualification = findViewById(R.id.et_new_job_min_qualification);
        etSalary = findViewById(R.id.et_new_job_slalry);
        //TextView
        tvLastDate = findViewById(R.id.tv_new_job_last_date);

        //Buttons
        btnCommit = findViewById(R.id.btn_commit_job);
        btnDelete = findViewById(R.id.btn_delete_job);

    }
}