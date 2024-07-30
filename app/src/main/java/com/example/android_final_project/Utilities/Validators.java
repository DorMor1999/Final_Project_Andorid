package com.example.android_final_project.Utilities;

public class Validators {

    public Validators() {
    }

    public boolean stringIsEmpty(String input){
        return input.isEmpty();
    }

    public boolean doubleIsPositive(double input) {
        return input > 0.0D;
    }
}
