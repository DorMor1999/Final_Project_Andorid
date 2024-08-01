package com.example.android_final_project.Utilities;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class Validators {

    public Validators() {
    }

    public boolean stringIsEmpty(String input){
        return input.isEmpty();
    }

    public boolean doubleIsPositive(double input) {
        return input > 0.0D;
    }

    public boolean dateRangeIsOk(String dateFrom, String dateTo) {
        // Define the date format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Set lenient to false to ensure strict date parsing

        try {
            // Parse the dates
            Date fromDate = sdf.parse(dateFrom);
            Date toDate = sdf.parse(dateTo);

            // Check if both dates are not null and fromDate is before or equal to toDate
            return fromDate != null && toDate != null && !fromDate.after(toDate);

        } catch (ParseException e) {
            // Handle the case where date parsing fails
            return false;
        }
    }

    public boolean priceRangeIsOk(double min, double max) {
        // Check if the minimum price is not greater than the maximum price
        if (min > max) {
            return false; // Invalid range
        }

        // Ensure that both prices are strictly positive
        if (min < 0 || max < 0) {
            return false; // Invalid range if zero or negative prices are not allowed
        }

        return true; // Valid price range
    }
}
