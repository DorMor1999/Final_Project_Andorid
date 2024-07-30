package com.example.android_final_project.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_final_project.Fragments.ListFragment;
import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.GetDataFromDB;
import com.example.android_final_project.Interfaces.ReqToDB;
import com.example.android_final_project.Model.BusinessActivityHashMap;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitiesActivity extends AppCompatActivity {

    private FrameLayout activities_FRAME_nav;
    private NavFragment navFragment;
    private final String TITLE = "Activities";

    private FrameLayout activities_FRAME_list;
    private ListFragment listFragment;

    private Button activities_btn_display_options;

    private LinearLayout activities_linear_layout_incomes_expenses_overall;

    private TextView activities_incomes_tv_label;
    private TextView activities_expenses_tv_label;
    private TextView activities_overall_tv_label;
    private TextView activities_incomes_tv_amount;
    private TextView activities_expenses_tv_amount;
    private TextView activities_overall_tv_amount;
    private DialogUtils dialogUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        dialogUtils = new DialogUtils();

        findViews();
        initViews();
    }

    private void findViews() {
        //fragments
        activities_FRAME_nav = findViewById(R.id.activities_FRAME_nav);
        activities_FRAME_list = findViewById(R.id.activities_FRAME_list);

        //buttons
        activities_btn_display_options = findViewById(R.id.activities_btn_display_options);

        //linear layout and what inside
        activities_linear_layout_incomes_expenses_overall = findViewById(R.id.activities_linear_layout_incomes_expenses_overall);
        activities_overall_tv_label = findViewById(R.id.activities_overall_tv_label);
        activities_expenses_tv_label = findViewById(R.id.activities_expenses_tv_label);
        activities_incomes_tv_label = findViewById(R.id.activities_incomes_tv_label);
        activities_overall_tv_amount = findViewById(R.id.activities_overall_tv_label);
        activities_expenses_tv_amount = findViewById(R.id.activities_expenses_tv_label);
        activities_incomes_tv_amount = findViewById(R.id.activities_incomes_tv_label);
    }

    private void initViews() {
        // Manage data and handle it in the callback
        manageData();

        //nav fragment
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);
        getSupportFragmentManager().beginTransaction().add(R.id.activities_FRAME_nav, navFragment).commit();

        //list fragment
        //listFragment = new ListFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.activities_FRAME_list, listFragment).commit();
    }

    private void manageData() {
        getDataFromDB(new GetDataFromDB() {
            @Override
            public void getData(BusinessActivityHashMap businessActivityHashMap) {
                // Handle data here
                Log.d("Data", businessActivityHashMap.toString());
                // Update UI or perform other actions
            }
        });
    }

    private void getDataFromDB(GetDataFromDB callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DbOperations.getInstance().getAllBusinessActivitiesOfUser(user, new ReqToDB() {
            @Override
            public void onSuccess() {
                // Handle success case
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure case
                dialogUtils.showDialog(ActivitiesActivity.this, "Display Error", "Something went wrong, try again later.", null);
            }
        }, callback);
    }


}