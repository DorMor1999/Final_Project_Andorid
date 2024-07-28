package com.example.android_final_project.Model;

import java.util.HashMap;

public class BusinessActivityHashMap {
    private HashMap<String, BusinessActivity> allActivities = new HashMap<>();

    public BusinessActivityHashMap() {
    }

    public HashMap<String, BusinessActivity> getAllActivities() {
        return allActivities;
    }

    public BusinessActivityHashMap setAllActivities(HashMap<String, BusinessActivity> allActivities) {
        this.allActivities = allActivities;
        return this;
    }
}
