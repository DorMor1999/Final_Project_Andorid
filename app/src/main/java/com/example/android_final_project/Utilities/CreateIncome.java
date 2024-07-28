package com.example.android_final_project.Utilities;

import static com.example.android_final_project.Enums.IncomeType.SALARY;
import static com.example.android_final_project.Enums.IncomeType.BONUSES;
import static com.example.android_final_project.Enums.IncomeType.INTEREST;
import static com.example.android_final_project.Enums.IncomeType.DIVIDENDS;
import static com.example.android_final_project.Enums.IncomeType.OTHER;
import com.example.android_final_project.Enums.IncomeType;



import com.example.android_final_project.Model.Income;

public class CreateIncome {

    public static Income createIncomeObj(String description, double price, String date, String incomeType){
        Income newIncome = new Income();
        newIncome
                .setIncomeType(getIncomeTypeENUM(incomeType)) //have to be first
                .setDescription(description)
                .setDate(date)
                .setPrice(price)
        ;
        return newIncome;
    }

    public static IncomeType getIncomeTypeENUM(String incomeType) {
        switch (incomeType) {
            case "Salary":
                return SALARY;
            case "Bonuses":
                return BONUSES;
            case "Interest":
                return INTEREST;
            case "Dividends":
                return DIVIDENDS;
            default:
                return OTHER;
        }
    }
}
