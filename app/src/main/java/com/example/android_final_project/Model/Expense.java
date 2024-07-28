package com.example.android_final_project.Model;

import com.example.android_final_project.Enums.ExpenseType;

public class Expense extends BusinessActivity{
    private ExpenseType expenseType = ExpenseType.OTHER;

    public Expense() {

    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public Expense setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
        return this;
    }

    public String getExpenseTypeString(){
        switch (expenseType) {
            case HOUSING:
                return "Housing";
            case UTILITIES:
                return "Utilities";
            case GROCERIES:
                return "Groceries";
            case TRANSPORT:
                return "Transport";
            case OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Expense{" + super.toString() +
                "expenseType=" + expenseType +
                '}';
    }
}
