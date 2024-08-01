package com.example.android_final_project.Model;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.SortOptions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        }
    }

    public ArrayList<BusinessActivity> getBusinessActivityListDisplayFilteredSorted(FilterManager filterManager){
        fillDisplayList();
        if (filterManager.isFilterByDate()){
            filterDisplayListByDate(filterManager.getFromDate(), filterManager.getToDate());
        }
        if(filterManager.isFilterByPrice()){
            filterDisplayListByPrice(filterManager.getMinPrice(), filterManager.getMaxPrice());
        }
        if (filterManager.getBusinessActivityType() != BusinessActivityType.BOTH){
            filterDisplayListByType(filterManager.getBusinessActivityType());
        }
        if(filterManager.getSortOption() != SortOptions.DONT_SORT){
            decideHowToSortDisplayListAndSort(filterManager.getSortOption());
        }

        //calculate only after i change my list
        calculateDisplay();
        return businessActivityListDisplay;
    }

    private void decideHowToSortDisplayListAndSort(SortOptions sortWay) {
        switch (sortWay) {
            case PRICE_LOW_TO_HIGH:
                Collections.sort(businessActivityListDisplay, Comparator.comparingDouble(BusinessActivity::getPrice));
                break;
            case PRICE_HIGH_TO_LOW:
                Collections.sort(businessActivityListDisplay, Comparator.comparingDouble(BusinessActivity::getPrice).reversed());
                break;
            case DATE_LOW_TO_HIGH:
                Collections.sort(businessActivityListDisplay, Comparator.comparing(activity -> {
                    try {
                        return new SimpleDateFormat("dd/MM/yy").parse(activity.getDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }));
                break;
            case DATE_HIGH_TO_LOW:
                Collections.sort(businessActivityListDisplay, Comparator.comparing((BusinessActivity activity) -> {
                    try {
                        return new SimpleDateFormat("dd/MM/yy").parse(activity.getDate());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }).reversed());
                break;
            case DESCRIPTION_LOW_TO_HIGH:
                Collections.sort(businessActivityListDisplay, Comparator.comparing(BusinessActivity::getDescription));
                break;
            case DESCRIPTION_HIGH_TO_LOW:
                Collections.sort(businessActivityListDisplay, Comparator.comparing(BusinessActivity::getDescription).reversed());
                break;
            default:
                throw new IllegalArgumentException("Unknown sort option: " + sortWay);
        }
    }


    private void filterDisplayListByType(BusinessActivityType type){
        ArrayList<BusinessActivity> filteredList = new ArrayList<>();
        for (BusinessActivity activity : businessActivityListDisplay) {
            if ((type == BusinessActivityType.EXPENSE && activity instanceof Expense) ||
                    (type == BusinessActivityType.INCOME && activity instanceof Income)) {
                filteredList.add(activity);
            }
        }

        // Clear the display list and add all filtered activities
        businessActivityListDisplay.clear();
        businessActivityListDisplay.addAll(filteredList);
    }

    private void filterDisplayListByPrice(double min, double max){
        ArrayList<BusinessActivity> filteredList = new ArrayList<>();
        for (BusinessActivity activity : businessActivityListDisplay) {
            if (activity.getPrice() >= min && activity.getPrice() <= max) {
                filteredList.add(activity);
            }
        }

        // Clear the display list and add all filtered activities
        businessActivityListDisplay.clear();
        businessActivityListDisplay.addAll(filteredList);
    }

    private void filterDisplayListByDate(String fromDate,String toDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        try {
            Date from = dateFormat.parse(fromDate);
            Date to = dateFormat.parse(toDate);

            ArrayList<BusinessActivity> filteredList = new ArrayList<>();
            for (BusinessActivity activity : businessActivityListDisplay) {
                Date activityDate = dateFormat.parse(activity.getDate());
                if ((activityDate.equals(from) || activityDate.after(from)) &&
                        (activityDate.equals(to) || activityDate.before(to))) {
                    filteredList.add(activity);
                }
            }

            // Clear the display list and add all filtered activities
            businessActivityListDisplay.clear();
            businessActivityListDisplay.addAll(filteredList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void fillDisplayList() {
        businessActivityListDisplay.clear();
        for (int i = 0; i < businessActivityList.size(); i++) {
            businessActivityListDisplay.add(businessActivityList.get(i));
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

    public void calculateDisplay(){
        incomes = 0.0D;
        expenses = 0.0D;
        overall = 0.0D;
        for (int i = 0; i < businessActivityListDisplay.size(); i++) {
            if (businessActivityListDisplay.get(i) instanceof Expense){
                expenses += businessActivityListDisplay.get(i).getPrice();
                overall -= businessActivityListDisplay.get(i).getPrice();
            } else if (businessActivityListDisplay.get(i) instanceof Income) {
                incomes += businessActivityListDisplay.get(i).getPrice();
                overall += businessActivityListDisplay.get(i).getPrice();
            }
        }
    }


}
