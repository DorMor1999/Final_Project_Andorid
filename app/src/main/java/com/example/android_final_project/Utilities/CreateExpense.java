package com.example.android_final_project.Utilities;

import static com.example.android_final_project.Enums.ExpenseType.HOUSING;
import static com.example.android_final_project.Enums.ExpenseType.TRANSPORT;
import static com.example.android_final_project.Enums.ExpenseType.UTILITIES;
import static com.example.android_final_project.Enums.ExpenseType.GROCERIES;
import static com.example.android_final_project.Enums.ExpenseType.OTHER;
import com.example.android_final_project.Enums.ExpenseType;

import com.example.android_final_project.Model.Expense;

public class CreateExpense {

    public CreateExpense() {
    }

    public Expense createExpenseObj(String description, double price, String date, String expenseType){
        Expense newExpense = new Expense();
        newExpense
                .setExpenseType(getExpenseTypeENUM(expenseType)) //have to be first
                .setDescription(description)
                .setDate(date)
                .setPrice(price)
        ;
        return newExpense;
    }

    public ExpenseType getExpenseTypeENUM(String expenseType) {
        switch (expenseType) {
            case "Housing":
                return HOUSING;
            case "Utilities":
                return UTILITIES;
            case "Groceries":
                return GROCERIES;
            case "Transport":
                return TRANSPORT;
            default:
                return OTHER;
        }
    }
}
