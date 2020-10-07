package com.twilio.video.app.SettingPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.twilio.video.app.ApiModals.MakeClassResponse;
import com.twilio.video.app.R;
import com.twilio.video.app.RetrifitClient;
import com.twilio.video.app.SettingsResponse.SettingsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HrSettingPage extends AppCompatActivity {

    TextView tvproStatus;
    Button apply;
    ImageView ibBack;
    String proStat,token;
    PopupWindow progressPopu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_setting_page);
        setui();
        proStat = getIntent().getStringExtra("status");
        loadToken();

        if(proStat.equalsIgnoreCase("2"))
        {
            tvproStatus.setVisibility(View.VISIBLE);
            tvproStatus.setText("Your Application For Hr is Under Review");
            apply.setVisibility(View.GONE);
        }
        if(proStat.equalsIgnoreCase("1"))
        {
            tvproStatus.setVisibility(View.VISIBLE);
            tvproStatus.setText("Your Application For Hr is Approved");
            apply.setVisibility(View.GONE);
        }
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressPopup(HrSettingPage.this);
                updateToHr();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void startProgressPopup(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.progres_popup,
                null); // inflating popup layout
        progressPopu = new PopupWindow(popUpView, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        progressPopu.setAnimationStyle(android.R.style.Animation_Dialog);
        progressPopu.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
    }


    private void loadToken() {

        SharedPreferences settings = getSharedPreferences("login_preferences",
                Context.MODE_PRIVATE);
        token = settings.getString("token", "");

    }

    private void setui() {
        tvproStatus = findViewById(R.id.tv_hr_status);
        ibBack = findViewById(R.id.iv_back_edit_hr);
        apply = findViewById(R.id.btn_apply_for_hr);

    }

    private void updateToHr() {
        Call<MakeClassResponse> call = RetrifitClient.getInstance()
                .getSettingsApi().applyForHr(token);

        call.enqueue(new Callback<MakeClassResponse>() {
            @Override
            public void onResponse(Call<MakeClassResponse> call, Response<MakeClassResponse> response) {
                Log.d("Tag>response >>", response.raw().toString());

                progressPopu.dismiss();
                if(response.body()!=null)
                {
                    if (response.body().getStatus())
                    {
                        tvproStatus.setVisibility(View.VISIBLE);
                        tvproStatus.setText("Application For Hr is being Submitted");
                        apply.setVisibility(View.GONE);
                        Toast.makeText(HrSettingPage.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(HrSettingPage.this, "Error Updating ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MakeClassResponse> call, Throwable t) {
                progressPopu.dismiss();
            }
        });
    }
}