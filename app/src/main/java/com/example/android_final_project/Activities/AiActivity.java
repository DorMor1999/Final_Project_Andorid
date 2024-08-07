package com.example.android_final_project.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.MyPieChartTypes;
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
import com.example.android_final_project.Model.MyPieChart;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AiActivity extends AppCompatActivity implements CloseFilterCallback, DisplayFilterCallback {

    private FrameLayout ai_FRAME_nav;
    private NavFragment navFragment;
    private final String TITLE = "Ai";

    private LinearLayout ai_buttons_container;
    private Button ai_btn_display_options;
    private DialogUtils dialogUtils;
    private BusinessActivityList businessActivityList;
    private FrameLayout ai_FRAME_filter;
    private FilterFragment filterFragment;
    private FilterManager filterManager;

    private CardView ai_ai_container;
    private TextView ai_description_tv;
    private Button ai_btn_ask;
    private TextView ai_response_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        dialogUtils = new DialogUtils();
        startInitFilterManager();

        findViews();
        initViews();
    }

    private void initViews() {
        //nav fragment
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);
        getSupportFragmentManager().beginTransaction().add(R.id.ai_FRAME_nav, navFragment).commit();

        //filter fragment
        filterFragment = new FilterFragment();
        filterFragment.setCloseFilterCallback(this);
        filterFragment.setDisplayFilterCallback(this);
        filterFragment.setIsActivitiesActivity(false);
        getSupportFragmentManager().beginTransaction().add(R.id.ai_FRAME_filter, filterFragment).commit();

        //handle click display options
        ai_btn_display_options.setOnClickListener(v -> displayOptions());

        // Manage data and handle it in the callback
        manageData();
    }

    private void manageData() {
        getDataFromDB(new GetDataFromDB() {
            @Override
            public void getData(BusinessActivityHashMap businessActivityHashMap) {
                // Handle data here first time
                businessActivityList = new BusinessActivityList();
                businessActivityList.putHashMapDataInList(businessActivityHashMap.getAllActivities());
                //doing with the data what i need
                filterData(businessActivityList);
            }
        });
    }

    private void filterData(BusinessActivityList businessActivityList) {
        //refresh display
        businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager);
        Log.d("filterData: ", businessActivityList.getBusinessActivityListDisplay().toString());
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
                dialogUtils.showDialog(AiActivity.this, "Display Error", "Something went wrong, try again later.", null);
            }
        }, callback);
    }

    private void displayOptions() {
        ai_buttons_container.setVisibility(View.GONE);
        ai_FRAME_filter.setVisibility(View.VISIBLE);
    }

    private void findViews() {
        //fragments
        ai_FRAME_nav = findViewById(R.id.ai_FRAME_nav);
        ai_FRAME_filter = findViewById(R.id.ai_FRAME_filter);


        //filter part
        ai_btn_display_options = findViewById(R.id.ai_btn_display_options);
        ai_buttons_container = findViewById(R.id.ai_buttons_container);

        //ai part
        ai_ai_container = findViewById(R.id.ai_ai_container);
        ai_description_tv = findViewById(R.id.ai_description_tv);
        ai_btn_ask = findViewById(R.id.ai_btn_ask);
        ai_response_tv = findViewById(R.id.ai_response_tv);
    }

    private void startInitFilterManager() {
        filterManager = new FilterManager();
        filterManager
                .setFilterByDate(false)
                .setFilterByPrice(false)
                .setBusinessActivityType(BusinessActivityType.BOTH)
                .setSortOption(SortOptions.DATE_LOW_TO_HIGH);
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
        filterData(businessActivityList);
    }

    private void closeFilterFrag() {
        ai_buttons_container.setVisibility(View.VISIBLE);
        ai_FRAME_filter.setVisibility(View.GONE);
    }
}