package com.example.android_final_project.Model;

import java.util.HashMap;

public class BusinessActivityHashMap {

    private String userId = "";
    private HashMap<String, BusinessActivity> allActivities = new HashMap<>();

    public BusinessActivityHashMap() {
    }

    public BusinessActivityHashMap(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public BusinessActivityHashMap setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public HashMap<String, BusinessActivity> getAllActivities() {
        return allActivities;
    }

    public BusinessActivityHashMap setAllActivities(HashMap<String, BusinessActivity> allActivities) {
        this.allActivities = allActivities;
        return this;
    }
}
