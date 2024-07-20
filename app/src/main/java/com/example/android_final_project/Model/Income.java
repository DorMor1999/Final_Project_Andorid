package com.example.android_final_project.Model;

import com.example.android_final_project.Enums.IncomeType;

public class Income extends BusinessActivity {
    private IncomeType incomeType = IncomeType.OTHER;

    public Income() {

    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public Income setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
        return this;
    }

    public String getIncomeTypeString() {
        switch (incomeType) {
            case SALARY:
                return "Salary";
            case BONUSES:
                return "Bonuses";
            case INTEREST:
                return "Interest";
            case DIVIDENDS:
                return "Dividends";
            case OTHER:
                return "Other";
            default:
                return "Unknown";
        }
    }
}
