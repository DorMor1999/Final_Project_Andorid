package com.example.android_final_project.Model;

import com.example.android_final_project.Enums.LineChartTypes;

import java.util.ArrayList;

import kotlin.contracts.Returns;

public class MyLineChart extends MyChart{
    private ArrayList<LinePart> lineParts = new ArrayList<>();
    private LineChartTypes lineChartType;


    public MyLineChart(String title, LineChartTypes lineChartType, BusinessActivityList businessActivityList) {
        super(title);
        this.lineChartType = lineChartType;
        reloadLineParts(businessActivityList);
    }

    private void reloadLineParts(BusinessActivityList businessActivityList) {
        LinePart currentLinePart = new LinePart(getDateSplitByLineChartType(businessActivityList.getBusinessActivityListDisplay().get(0).getDate()));
        for (BusinessActivity activity: businessActivityList.getBusinessActivityListDisplay()) {
            String currentDate = getDateSplitByLineChartType(activity.getDate());
            //check if date different so i can open new LinePart
            if(!currentLinePart.getDate().equals(currentDate)){
                lineParts.add(currentLinePart);
                currentLinePart = new LinePart(currentDate);
            }

            if(activity instanceof Income){
                currentLinePart.addIncome(activity.getPrice());
            }else{
                currentLinePart.addExpense(activity.getPrice());
            }
        }
        lineParts.add(currentLinePart);
    }

    private String getDateSplitByLineChartType(String date){
        switch (lineChartType){
            case YEAR:
                return date.split("/")[2];
            case YEAR_AND_MONTH:
                return date.split("/")[1] + "/"+ date.split("/")[2];
            default:
                return date;
        }
    }
}
