package com.example.android_final_project.Model;

public abstract class MyChart {
    private String title;

    public MyChart(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public MyChart setTitle(String title) {
        this.title = title;
        return this;
    }
}
