package com.example.android_final_project.Model;

import com.example.android_final_project.Enums.BusinessActivityType;
import com.example.android_final_project.Enums.SortOptions;

public class FilterManager {
    private boolean filterByDate;
    private String fromDate;
    private String toDate;
    private boolean filterByPrice;
    private double minPrice;
    private double maxPrice;
    private BusinessActivityType businessActivityType;
    private SortOptions sortOption;

    public FilterManager() {
    }

    public boolean isFilterByDate() {
        return filterByDate;
    }

    public FilterManager setFilterByDate(boolean filterByDate) {
        this.filterByDate = filterByDate;
        return this;
    }

    public String getFromDate() {
        return fromDate;
    }

    public FilterManager setFromDate(String fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public String getToDate() {
        return toDate;
    }

    public FilterManager setToDate(String toDate) {
        this.toDate = toDate;
        return this;
    }

    public boolean isFilterByPrice() {
        return filterByPrice;
    }

    public FilterManager setFilterByPrice(boolean filterByPrice) {
        this.filterByPrice = filterByPrice;
        return this;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public FilterManager setMinPrice(double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public FilterManager setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public BusinessActivityType getBusinessActivityType() {
        return businessActivityType;
    }

    public FilterManager setBusinessActivityType(BusinessActivityType businessActivityType) {
        this.businessActivityType = businessActivityType;
        return this;
    }

    public SortOptions getSortOption() {
        return sortOption;
    }

    public FilterManager setSortOption(SortOptions sortOption) {
        this.sortOption = sortOption;
        return this;
    }
}
