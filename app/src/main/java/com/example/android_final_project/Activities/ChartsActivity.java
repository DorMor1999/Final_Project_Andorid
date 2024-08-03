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
import com.example.android_final_project.Enums.LineChartTypes;
import com.example.android_final_project.Enums.MyPieChartTypes;
import com.example.android_final_project.Enums.SortOptions;
import com.example.android_final_project.Fragments.ChartsFragment;
import com.example.android_final_project.Fragments.FilterFragment;
import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.CloseFilterCallback;
import com.example.android_final_project.Interfaces.DisplayFilterCallback;
import com.example.android_final_project.Interfaces.GetDataFromDB;
import com.example.android_final_project.Interfaces.ReqToDB;
import com.example.android_final_project.Model.BusinessActivity;
import com.example.android_final_project.Model.BusinessActivityHashMap;
import com.example.android_final_project.Model.BusinessActivityList;
import com.example.android_final_project.Model.FilterManager;
import com.example.android_final_project.Model.MyChart;
import com.example.android_final_project.Model.MyLineChart;
import com.example.android_final_project.Model.MyPieChart;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity implements CloseFilterCallback, DisplayFilterCallback {
    private FrameLayout charts_FRAME_nav;
    private NavFragment navFragment;

    private FrameLayout charts_FRAME_charts_list;
    private ChartsFragment chartsFragment;
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
        filterFragment.setIsChartsActivity(true);
        getSupportFragmentManager().beginTransaction().add(R.id.charts_FRAME_filter, filterFragment).commit();

        //handle click display options
        charts_btn_display_options.setOnClickListener(v -> displayOptions());

        //list fragment
        chartsFragment = new ChartsFragment();
        // Manage data and handle it in the callback
        manageData();
        getSupportFragmentManager().beginTransaction().add(R.id.charts_FRAME_charts_list, chartsFragment).commit();
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
                changeChartsListInFragment(businessActivityList);
                /*
                //refresh display
                businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager);
                ArrayList<MyChart> chartsList = new ArrayList<>();
                chartsList.add(new MyLineChart("Overall Incomes and Expenses chart years", LineChartTypes.YEAR, businessActivityList));
                chartsList.add(new MyLineChart("Overall Incomes and Expenses chart months", LineChartTypes.YEAR_AND_MONTH, businessActivityList));
                chartsList.add(new MyLineChart("Overall Incomes and Expenses chart days", LineChartTypes.YEAR_AND_MONTH_AND_DAY, businessActivityList));
                chartsList.add(new MyPieChart("Incomes VS Expenses", MyPieChartTypes.EXPENSES_VS_INCOMES, businessActivityList));
                chartsList.add(new MyPieChart("Expenses types", MyPieChartTypes.EXPENSE_TYPES, businessActivityList));
                chartsList.add(new MyPieChart("Incomes types", MyPieChartTypes.INCOME_TYPES, businessActivityList));
                //listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
                */
            }
        });
    }

    private void changeChartsListInFragment(BusinessActivityList businessActivityList) {
        //refresh display
        businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager);
        ArrayList<MyChart> chartsList = new ArrayList<>();
        chartsList.add(new MyLineChart("Overall Incomes and Expenses chart years", LineChartTypes.YEAR, businessActivityList));
        chartsList.add(new MyLineChart("Overall Incomes and Expenses chart months", LineChartTypes.YEAR_AND_MONTH, businessActivityList));
        chartsList.add(new MyLineChart("Overall Incomes and Expenses chart days", LineChartTypes.YEAR_AND_MONTH_AND_DAY, businessActivityList));
        //chartsList.add(new MyPieChart("Incomes VS Expenses", MyPieChartTypes.EXPENSES_VS_INCOMES, businessActivityList));
        //chartsList.add(new MyPieChart("Expenses types", MyPieChartTypes.EXPENSE_TYPES, businessActivityList));
        //chartsList.add(new MyPieChart("Incomes types", MyPieChartTypes.INCOME_TYPES, businessActivityList));
        chartsFragment.setChartsList(chartsList);
        //listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
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
        charts_FRAME_charts_list = findViewById(R.id.charts_FRAME_charts_list);

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
        //listFragment.setBusinessActivityList(businessActivityList.getBusinessActivityListDisplayFilteredSorted(filterManager));
    }

    private void closeFilterFrag() {
        charts_buttons_container.setVisibility(View.VISIBLE);
        charts_FRAME_filter.setVisibility(View.GONE);
    }
}