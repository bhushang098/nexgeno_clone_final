package com.twilio.video.app.MainPages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.twilio.video.app.HomePage;
import com.twilio.video.app.R;
import com.twilio.video.app.adapter.SkillTabsAdapter;
import com.twilio.video.app.subMainPages.AppliedJobsFrag;
import com.twilio.video.app.subMainPages.FindJobsFrag;
import com.twilio.video.app.util.NetworkOperator;

public class JobsPage extends AppCompatActivity {

    Toolbar toolbar;
    ImageView iv_search_jobs;

    private SkillTabsAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    String token;

    NetworkOperator networkOperator = new NetworkOperator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_page);
        setUi();
        loadPreferences();
        //toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
       // setSupportActionBar(toolbar);


        adapter = new SkillTabsAdapter(getSupportFragmentManager());
        adapter.addFragment(new FindJobsFrag(), "Find Jobs ");
        adapter.addFragment(new AppliedJobsFrag(), "Applied Jobs ");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_job);
        bottomNavigationView.setSelectedItemId(R.id.nav_jobs_bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(JobsPage.this, HomePage.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.nav_skill:
                        startActivity(new Intent(JobsPage.this, SkillPage.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.nav_jobs:
                        break;
                    case R.id.nav_classes:
                        startActivity(new Intent(JobsPage.this, ClassesPage.class));
                        overridePendingTransition(0, 0);
                        finish();

                        break;

                    case R.id.nav_users:
                        startActivity(new Intent(JobsPage.this, UsersPage.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences("login_preferences",
                Context.MODE_PRIVATE);

        token = settings.getString("token", "");

    }

    private void setUi() {
        toolbar = findViewById(R.id.tbjobs);
        //joinBtn = findViewById(R.id.btn_join_room);
        // cvSkill = findViewById(R.id.cv_skill);
        iv_search_jobs = findViewById(R.id.iv_search_job);
        viewPager = (ViewPager) findViewById(R.id.vpg_jobs);
        tabLayout = (TabLayout) findViewById(R.id.tbl_jobs);
    }
}