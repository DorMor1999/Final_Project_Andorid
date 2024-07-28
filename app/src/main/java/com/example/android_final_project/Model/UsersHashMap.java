package com.example.android_final_project.Model;

import java.util.HashMap;

public class UsersHashMap {
    private HashMap<String, BusinessActivityHashMap> allUsers = new HashMap<>();

    public UsersHashMap() {
    }

    public HashMap<String, BusinessActivityHashMap> getAllUsers() {
        return allUsers;
    }

    public UsersHashMap setAllUsers(HashMap<String, BusinessActivityHashMap> allUsers) {
        this.allUsers = allUsers;
        return this;
    }
}
