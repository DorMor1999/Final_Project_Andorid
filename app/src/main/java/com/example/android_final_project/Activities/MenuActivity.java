package com.example.android_final_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.Callback_Log_Out_Clicked;
import com.example.android_final_project.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
       //navFragment.setCallbackLogOutClicked(this);
        // Show fragment
        getSupportFragmentManager().beginTransaction().add(R.id.menu_FRAME_nav, navFragment).commit();
    }

    private void findViews() {
        menu_FRAME_nav = findViewById(R.id.menu_FRAME_nav);
        menu_BTN_add_activity = findViewById(R.id.menu_BTN_add_activity);
        menu_BTN_add_activities = findViewById(R.id.menu_BTN_add_activities);
        menu_BTN_show_activities = findViewById(R.id.menu_BTN_show_activities);
        menu_BTN_show_charts = findViewById(R.id.menu_BTN_show_charts);
    }
}