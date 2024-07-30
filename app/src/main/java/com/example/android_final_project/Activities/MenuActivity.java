package com.example.android_final_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.R;

public class MenuActivity extends AppCompatActivity  {

    private FrameLayout menu_FRAME_nav;
    private Button menu_BTN_add_activity;
    private Button menu_BTN_add_activities;
    private Button menu_BTN_show_activities;
    private Button menu_BTN_show_charts;
    private NavFragment navFragment;
    private final String TITLE = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();
        initViews();
    }

    private void initViews() {
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);

        //buttons handling
        menu_BTN_add_activity.setOnClickListener(v -> moveToAddActivity());
        menu_BTN_show_activities.setOnClickListener(v -> moveToActivitiesActivity());

        // Show fragment
        getSupportFragmentManager().beginTransaction().add(R.id.menu_FRAME_nav, navFragment).commit();
    }

    private void moveToAddActivity() {
        Intent i = new Intent(this, AddActivity.class);
        startActivity(i);
        finish();
    }

    private void moveToActivitiesActivity() {
        Intent i = new Intent(this, ActivitiesActivity.class);
        startActivity(i);
        finish();
    }

    private void findViews() {
        menu_FRAME_nav = findViewById(R.id.menu_FRAME_nav);
        menu_BTN_add_activity = findViewById(R.id.menu_BTN_add_activity);
        menu_BTN_add_activities = findViewById(R.id.menu_BTN_add_activities);
        menu_BTN_show_activities = findViewById(R.id.menu_BTN_show_activities);
        menu_BTN_show_charts = findViewById(R.id.menu_BTN_show_charts);
    }
}