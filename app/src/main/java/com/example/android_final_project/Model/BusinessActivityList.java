package com.example.android_final_project.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BusinessActivityList {

    private double incomes = 0.0D;
    private double expenses = 0.0D;
    private double overall = 0.0D;

    private ArrayList<BusinessActivity> businessActivityList = new ArrayList<>();
    private ArrayList<BusinessActivity> businessActivityListDisplay = new ArrayList<>();

    public BusinessActivityList() {

    }

    public void putHashMapDataInList(HashMap<String, BusinessActivity> hashMap){
        for (Map.Entry<String, BusinessActivity> entry : hashMap.entrySet()) {
            businessActivityList.add(entry.getValue());
            businessActivityListDisplay.add(entry.getValue());
        }
    }

    public ArrayList<BusinessActivity> getBusinessActivityListDisplay() {
        return businessActivityListDisplay;
    }

    @Override
    public String toString() {
        return "BusinessActivityList{" +
                "businessActivityList=" + businessActivityList +
                '}';
    }

    public double getIncomes() {
        return incomes;
    }

    public double getExpenses() {
        return expenses;
    }

    public double getOverall() {
        return overall;
    }

    public void calculateAll(){
        incomes = 0.0D;
        expenses = 0.0D;
        overall = 0.0D;
        for (int i = 0; i < businessActivityListDisplay.size(); i++) {
            if (businessActivityList.get(i) instanceof Expense){
                expenses += businessActivityList.get(i).getPrice();
                overall -= businessActivityList.get(i).getPrice();
            } else if (businessActivityList.get(i) instanceof Income) {
                incomes += businessActivityList.get(i).getPrice();
                overall += businessActivityList.get(i).getPrice();
            }
        }
    }


}
