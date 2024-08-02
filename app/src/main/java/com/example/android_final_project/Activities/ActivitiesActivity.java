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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.SortOptions;
import com.example.android_final_project.Fragments.FilterFragment;
import com.example.android_final_project.Fragments.ListFragment;
import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.CloseFilterCallback;
import com.example.android_final_project.Interfaces.DeleteClick;
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

public class ActivitiesActivity extends AppCompatActivity implements DeleteClick, CloseFilterCallback, DisplayFilterCallback {

    private FrameLayout activities_FRAME_nav;
    private NavFragment navFragment;
    private final String TITLE = "Activities";

    private FrameLayout activities_FRAME_list;
    private ListFragment listFragment;
    private  LinearLayout activities_buttons_container;
    private Button activities_btn_display_options;


    private LinearLayout activities_linear_layout_incomes_expenses_overall;

    private TextView activities_incomes_tv_label;
    private TextView activities_expenses_tv_label;
    private TextView activities_overall_tv_label;
    private TextView activities_incomes_tv_amount;
    private TextView activities_expenses_tv_amount;
    private TextView activities_overall_tv_amount;
    private DialogUtils dialogUtils;
    private BusinessActivityList businessActivityList;
    private FrameLayout activities_FRAME_filter;
    private FilterFragment filterFragment;
    private FilterManager filterManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        dialogUtils = new DialogUtils();
        startInitFilterManager();

        findViews();
        initViews();
    }

    private void startInitFilterManager() {
        filterManager = new FilterManager();
        filterManager
                .setFilterByDate(false)
                .setFilterByPrice(false)
                .setBusinessActivityType(BusinessActivityType.BOTH)
                .setSortOption(SortOptions.DONT_SORT);
    }

    private void findViews() {
        //fragments
        activities_FRAME_nav = findViewById(R.id.activities_FRAME_nav);
        activities_FRAME_list = findViewById(R.id.activities_FRAME_list);
        activities_FRAME_filter =findViewById(R.id.activities_FRAME_filter);

        //buttons part
        activities_buttons_container = findViewById(R.id.activities_buttons_container);
        activities_btn_display_options = findViewById(R.id.activities_btn_display_options);

        //linear layout and what inside
        activities_linear_layout_incomes_expenses_overall = findViewById(R.id.activities_linear_layout_incomes_expenses_overall);
        activities_overall_tv_label = findViewById(R.id.activities_overall_tv_label);
        activities_expenses_tv_label = findViewById(R.id.activities_expenses_tv_label);
        activities_incomes_tv_label = findViewById(R.id.activities_incomes_tv_label);
        activities_overall_tv_amount = findViewById(R.id.activities_overall_tv_amount);
        activities_expenses_tv_amount = findViewById(R.id.activities_expenses_tv_amount);
        activities_incomes_tv_amount = findViewById(R.id.activities_incomes_tv_amount);
    }

    private void initViews() {


        //nav fragment
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);
        getSupportFragmentManager().beginTransaction().add(R.id.activities_FRAME_nav, navFragment).commit();

        //filter fragment
        filterFragment = new FilterFragment();
        filterFragment.setCloseFilterCallback(this);
        filterFragment.setDisplayFilterCallback(this);
        filterFragment.setIsChartsActivity(false);
        getSupportFragmentManager().beginTransaction().add(R.id.activities_FRAME_filter, filterFragment).commit();

        //handle click display options
        activities_btn_display_options.setOnClickListener(v -> displayOptions());

        //list fragment
        listFragment = new ListFragment();
        listFragment.setCallbackDeleteItemClicked(this);
        // Manage data and handle it in the callback
        manageData();
        getSupportFragmentManager().beginTransaction().add(R.id.activities_FRAME_list, listFragment).commit();


    }

    private void displayOptions() {
        activities_buttons_container.setVisibility(View.GONE);
        activities_linear_layout_incomes_expenses_overall.setVisibility(View.GONE);
        activities_FRAME_list.setVisibility(View.GONE);
        activities_FRAME_filter.setVisibility(View.VISIBLE);
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
                listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
                updateTable();
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


    @Override
    public void listItemDeleteClicked(String item_id) {
        Log.d("ID to delete", "listItemDeleteClicked: " + item_id);
        deleteInDB(item_id);
    }

    private void deleteInDB(String activityId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DbOperations.getInstance().deleteBusinessActivity(user, new ReqToDB() {
            @Override
            public void onSuccess() {
                // Handle success case
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure case
                dialogUtils.showDialog(ActivitiesActivity.this, "Delete Error", "Something went wrong, try again later.", null);
            }
        }, activityId);
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
        listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
        updateTable();
    }

    private void closeFilterFrag() {
        activities_buttons_container.setVisibility(View.VISIBLE);
        activities_linear_layout_incomes_expenses_overall.setVisibility(View.VISIBLE);
        activities_FRAME_list.setVisibility(View.VISIBLE);
        activities_FRAME_filter.setVisibility(View.GONE);
    }

    private void updateTable(){
        activities_expenses_tv_amount.setText(String.valueOf(businessActivityList.getExpenses()));
        activities_incomes_tv_amount.setText( String.valueOf(businessActivityList.getIncomes()));
        activities_overall_tv_amount.setText(String.valueOf(businessActivityList.getOverall()));
    }
}