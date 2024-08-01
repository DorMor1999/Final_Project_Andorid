package com.example.android_final_project.Interfaces;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.SortOptions;

public interface DisplayFilterCallback {

    void onButtonClickedDisplay(boolean filterByDate, String dateFrom, String dateTo, boolean filterByPrice, double minPrice, double maxPrice, BusinessActivityType businessActivityType, SortOptions sortOption);
}
