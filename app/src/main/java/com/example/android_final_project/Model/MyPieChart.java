package com.example.android_final_project.Model;

import com.example.android_final_project.Enums.MyPieChartTypes;

import java.util.ArrayList;

public class MyPieChart{
    private String title;
    private ArrayList<PiePart> pieParts = new ArrayList<>();
    private MyPieChartTypes myPieChartType;

    public MyPieChart(String title, MyPieChartTypes myPieChartType,BusinessActivityList businessActivityList ) {
        this.title = title;
        this.myPieChartType = myPieChartType;
        decideHowToReloadPieParts(businessActivityList);
    }

    private void decideHowToReloadPieParts(BusinessActivityList businessActivityList) {
        switch (myPieChartType){
            case EXPENSES_VS_INCOMES:
                reload_EXPENSES_VS_INCOMES(businessActivityList);
                return ;
            case EXPENSE_TYPES:
                reload_EXPENSE_TYPES(businessActivityList);
                return ;
            case INCOME_TYPES:
                reload_INCOME_TYPES(businessActivityList);
                return ;
        }
    }

    private void reload_INCOME_TYPES(BusinessActivityList businessActivityList) {
        pieParts.add(new PiePart("Salary", businessActivityList.getIncomesSalary()));
        pieParts.add(new PiePart("Bonuses", businessActivityList.getIncomesBonuses()));
        pieParts.add(new PiePart("Interest", businessActivityList.getIncomesInterest()));
        pieParts.add(new PiePart("Dividends", businessActivityList.getIncomesDividends()));
        pieParts.add(new PiePart("Other", businessActivityList.getIncomesOther()));
    }

    private void reload_EXPENSE_TYPES(BusinessActivityList businessActivityList) {
        pieParts.add(new PiePart("Housing", businessActivityList.getExpensesHousing()));
        pieParts.add(new PiePart("Utilities", businessActivityList.getExpensesUtilities()));
        pieParts.add(new PiePart("Groceries", businessActivityList.getExpensesGroceries()));
        pieParts.add(new PiePart("Transport", businessActivityList.getExpensesTransport()));
        pieParts.add(new PiePart("Other", businessActivityList.getExpensesOther()));
    }

    private void reload_EXPENSES_VS_INCOMES(BusinessActivityList businessActivityList) {
        pieParts.add(new PiePart("Incomes", businessActivityList.getIncomes()));
        pieParts.add(new PiePart("Expenses", businessActivityList.getExpenses()));
    }

    public ArrayList<PiePart> getPieParts() {
        return pieParts;
    }

    public MyPieChart setPieParts(ArrayList<PiePart> pieParts) {
        this.pieParts = pieParts;
        return this;
    }

    public MyPieChartTypes getMyPieChartType() {
        return myPieChartType;
    }

    public MyPieChart setMyPieChartType(MyPieChartTypes myPieChartType) {
        this.myPieChartType = myPieChartType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MyPieChart setTitle(String title) {
        this.title = title;
        return this;
    }
}
