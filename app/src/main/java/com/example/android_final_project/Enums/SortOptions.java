package com.example.android_final_project.Enums;

public enum SortOptions {
    DONT_SORT,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW,
    DATE_LOW_TO_HIGH,
    DATE_HIGH_TO_LOW,
    DESCRIPTION_LOW_TO_HIGH,
    DESCRIPTION_HIGH_TO_LOW;

    public static SortOptions stringToSortOptions(String sortOptionString) {
        switch (sortOptionString) {
            case "Don't sort":
                return DONT_SORT;
            case "Sort by price low to high":
                return PRICE_LOW_TO_HIGH;
            case "Sort by price high to low":
                return PRICE_HIGH_TO_LOW;
            case "Sort by date low to high":
                return DATE_LOW_TO_HIGH;
            case "Sort by date high to low":
                return DATE_HIGH_TO_LOW;
            case "Sort by description low to high":
                return DESCRIPTION_LOW_TO_HIGH;
            case "Sort by description high to low":
                return DESCRIPTION_HIGH_TO_LOW;
            default:
                throw new IllegalArgumentException("Unknown sort option: " + sortOptionString);
        }
    }
}
