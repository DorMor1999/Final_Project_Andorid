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
    private double incomesSalary = 0.0D;
    private double incomesBonuses = 0.0D;
    private double incomesInterest = 0.0D;
    private double incomesDividends = 0.0D;
    private double incomesOther = 0.0D;
    private  double expensesHousing = 0.0D;
    private  double expensesUtilities = 0.0D;
    private  double expensesGroceries = 0.0D;
    private  double expensesTransport = 0.0D;
    private  double expensesOther = 0.0D;



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

    public double getIncomesSalary() {
        return incomesSalary;
    }

    public BusinessActivityList setIncomesSalary(double incomesSalary) {
        this.incomesSalary = incomesSalary;
        return this;
    }

    public double getIncomesBonuses() {
        return incomesBonuses;
    }

    public BusinessActivityList setIncomesBonuses(double incomesBonuses) {
        this.incomesBonuses = incomesBonuses;
        return this;
    }

    public double getIncomesInterest() {
        return incomesInterest;
    }

    public BusinessActivityList setIncomesInterest(double incomesInterest) {
        this.incomesInterest = incomesInterest;
        return this;
    }

    public double getIncomesDividends() {
        return incomesDividends;
    }

    public BusinessActivityList setIncomesDividends(double incomesDividends) {
        this.incomesDividends = incomesDividends;
        return this;
    }

    public double getIncomesOther() {
        return incomesOther;
    }

    public BusinessActivityList setIncomesOther(double incomesOther) {
        this.incomesOther = incomesOther;
        return this;
    }

    public double getExpensesHousing() {
        return expensesHousing;
    }

    public BusinessActivityList setExpensesHousing(double expensesHousing) {
        this.expensesHousing = expensesHousing;
        return this;
    }

    public double getExpensesUtilities() {
        return expensesUtilities;
    }

    public BusinessActivityList setExpensesUtilities(double expensesUtilities) {
        this.expensesUtilities = expensesUtilities;
        return this;
    }

    public double getExpensesGroceries() {
        return expensesGroceries;
    }

    public BusinessActivityList setExpensesGroceries(double expensesGroceries) {
        this.expensesGroceries = expensesGroceries;
        return this;
    }

    public double getExpensesTransport() {
        return expensesTransport;
    }

    public BusinessActivityList setExpensesTransport(double expensesTransport) {
        this.expensesTransport = expensesTransport;
        return this;
    }

    public double getExpensesOther() {
        return expensesOther;
    }

    public BusinessActivityList setExpensesOther(double expensesOther) {
        this.expensesOther = expensesOther;
        return this;
    }

    public void calculateDisplay() {
        // Reset all totals
        incomes = 0.0D;
        expenses = 0.0D;
        overall = 0.0D;

        // Reset individual income types
        incomesSalary = 0.0D;
        incomesBonuses = 0.0D;
        incomesInterest = 0.0D;
        incomesDividends = 0.0D;
        incomesOther = 0.0D;

        // Reset individual expense types
        expensesHousing = 0.0D;
        expensesUtilities = 0.0D;
        expensesGroceries = 0.0D;
        expensesTransport = 0.0D;
        expensesOther = 0.0D;

        // Calculate totals
        for (BusinessActivity activity : businessActivityListDisplay) {
            double price = activity.getPrice();
            if (activity instanceof Expense) {
                expenses += price;
                overall -= price;  // Subtract expenses from overall balance

                // Distribute expense into categories
                Expense expense = (Expense) activity;
                switch (expense.getExpenseType()) {
                    case HOUSING:
                        expensesHousing += price;
                        break;
                    case UTILITIES:
                        expensesUtilities += price;
                        break;
                    case GROCERIES:
                        expensesGroceries += price;
                        break;
                    case TRANSPORT:
                        expensesTransport += price;
                        break;
                    case OTHER:
                        expensesOther += price;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown expense type: " + expense.getExpenseType());
                }
            } else if (activity instanceof Income) {
                incomes += price;
                overall += price;  // Add incomes to overall balance

                // Distribute income into categories
                Income income = (Income) activity;
                switch (income.getIncomeType()) {
                    case SALARY:
                        incomesSalary += price;
                        break;
                    case BONUSES:
                        incomesBonuses += price;
                        break;
                    case INTEREST:
                        incomesInterest += price;
                        break;
                    case DIVIDENDS:
                        incomesDividends += price;
                        break;
                    case OTHER:
                        incomesOther += price;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown income type: " + income.getIncomeType());
                }
            }
        }
    }

}
