package com.example.android_final_project.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.SortOptions;
import com.example.android_final_project.Fragments.FilterFragment;
import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.CloseFilterCallback;
import com.example.android_final_project.Interfaces.DisplayFilterCallback;
import com.example.android_final_project.Interfaces.GetDataFromDB;
import com.example.android_final_project.Interfaces.ReqToDB;
import com.example.android_final_project.Model.BusinessActivityHashMap;
import com.example.android_final_project.Model.BusinessActivityList;
import com.example.android_final_project.Model.FilterManager;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChartsActivity extends AppCompatActivity implements CloseFilterCallback, DisplayFilterCallback {
    private FrameLayout charts_FRAME_nav;
    private NavFragment navFragment;
    private final String TITLE = "Charts";

    private LinearLayout charts_buttons_container;
    private Button charts_btn_display_options;
    private DialogUtils dialogUtils;
    private BusinessActivityList businessActivityList;
    private FrameLayout charts_FRAME_filter;
    private FilterFragment filterFragment;
    private FilterManager filterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        dialogUtils = new DialogUtils();
        startInitFilterManager();


        findViews();
        initViews();
    }

    private void initViews() {

        //nav fragment
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);
        getSupportFragmentManager().beginTransaction().add(R.id.charts_FRAME_nav, navFragment).commit();

        //filter fragment
        filterFragment = new FilterFragment();
        filterFragment.setCloseFilterCallback(this);
        filterFragment.setDisplayFilterCallback(this);
        getSupportFragmentManager().beginTransaction().add(R.id.charts_FRAME_filter, filterFragment).commit();

        //handle click display options
        charts_btn_display_options.setOnClickListener(v -> displayOptions());

        // Manage data and handle it in the callback
        manageData();
    }

    private void manageData() {
        getDataFromDB(new GetDataFromDB() {
            @Override
            public void getData(BusinessActivityHashMap businessActivityHashMap) {
                // Handle data here first time
                Log.d("Data in map", businessActivityHashMap.toString());
                businessActivityList = new BusinessActivityList();
                businessActivityList.putHashMapDataInList(businessActivityHashMap.getAllActivities());
                Log.d("data in list", businessActivityList.toString());
                //doing with the data what i need
                //listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));

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
                dialogUtils.showDialog(ChartsActivity.this, "Display Error", "Something went wrong, try again later.", null);
            }
        }, callback);
    }

    private void displayOptions() {
        charts_buttons_container.setVisibility(View.GONE);
        charts_FRAME_filter.setVisibility(View.VISIBLE);
    }

    private void findViews() {
        //fragments
        charts_FRAME_nav = findViewById(R.id.charts_FRAME_nav);
        charts_FRAME_filter = findViewById(R.id.charts_FRAME_filter);

        //buttons part
        charts_btn_display_options = findViewById(R.id.charts_btn_display_options);


        charts_buttons_container = findViewById(R.id.charts_buttons_container);

    }

    private void startInitFilterManager() {
        filterManager = new FilterManager();
        filterManager
                .setFilterByDate(false)
                .setFilterByPrice(false)
                .setBusinessActivityType(BusinessActivityType.BOTH)
                .setSortOption(SortOptions.DONT_SORT);
    }


    @Override
    public void onButtonClickedClose() {
        closeFilterFrag();
    }


    @Override
    public void onButtonClickedDisplay(boolean filterByDate, String dateFrom, String dateTo, boolean filterByPrice, double minPrice, double maxPrice, BusinessActivityType businessActivityType, SortOptions sortOption) {
        closeFilterFrag();

        filterManager
                .setFilterByDate(filterByDate)
                .setFromDate(dateFrom)
                .setToDate(dateTo)
                .setFilterByPrice(filterByPrice)
                .setMinPrice(minPrice)
                .setMaxPrice(maxPrice)
                .setBusinessActivityType(businessActivityType)
                .setSortOption(sortOption);
        //doing with the data what i need
        //listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
    }

    private void closeFilterFrag() {
        charts_buttons_container.setVisibility(View.VISIBLE);
        charts_FRAME_filter.setVisibility(View.GONE);
    }
}