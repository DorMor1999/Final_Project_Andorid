package com.example.android_final_project.Model;

public class LinePart {
    private String date; // mm/yyyy  or yyyy
    private double overall = 0.0D;
    private double incomes = 0.0D;
    private double expenses = 0.0D;

    public LinePart(String date) {
        this.date = date;
        overall = 0.0D;
        incomes = 0.0D;
        expenses = 0.0D;
    }

    public void addExpense(double amount){
        expenses += amount;
        overall -= amount;
    }

    public void addIncome(double amount){
        incomes += amount;
        overall += amount;
    }

    public String getDate() {
        return date;
    }

    public LinePart setDate(String date) {
        this.date = date;
        return this;
    }

    public double getOverall() {
        return overall;
    }

    public LinePart setOverall(double overall) {
        this.overall = overall;
        return this;
    }

    public double getIncomes() {
        return incomes;
    }

    public LinePart setIncomes(double incomes) {
        this.incomes = incomes;
        return this;
    }

    public double getExpenses() {
        return expenses;
    }

    public LinePart setExpenses(double expenses) {
        this.expenses = expenses;
        return this;
    }
}
