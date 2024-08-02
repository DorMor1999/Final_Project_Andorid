package com.example.android_final_project.Model;

public class PiePart {
    private String name;
    private double amount;

    public PiePart(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public PiePart setName(String name) {
        this.name = name;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public PiePart setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
