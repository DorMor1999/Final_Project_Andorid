package com.example.android_final_project.Utilities;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android_final_project.Interfaces.AddUserCallback;
import com.example.android_final_project.Model.BusinessActivity;
import com.example.android_final_project.Model.BusinessActivityHashMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DbOperations {
    private static volatile DbOperations instance = null;
    private Context context;

    private DbOperations(Context context) {
        this.context = context;
    }

    public static DbOperations init(Context context){
        if (instance == null) {
            synchronized (DbOperations.class){
                if (instance == null){
                    instance = new DbOperations(context);
                }
            }
        }
        return getInstance();
    }

    public static DbOperations getInstance() {
        return instance;
    }

    public void  addUserToDB(FirebaseUser user, AddUserCallback callback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("DB", "User ID already exists");
                    callback.onSuccess();
                } else {

                    BusinessActivityHashMap newBusinessActivityHashMap = new BusinessActivityHashMap(user.getUid());
                    usersRef.child(user.getUid()).setValue(newBusinessActivityHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("DB", "onSuccess");
                            callback.onSuccess();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("DB", "onFailure", e);
                            callback.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DB", "Database error: " + error.getMessage());
                callback.onFailure(error.toException());
            }
        });
    }


    public void addNewBusinessActivity(FirebaseUser user, AddUserCallback callback, BusinessActivity newBusinessActivity) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Create a new unique key for the new business activity
        DatabaseReference newActivityRef = usersRef
                .child(user.getUid())
                .child("allActivities")
                .push();

        // Get the generated unique key
        String activityId = newActivityRef.getKey();

        // Set the ID in the BusinessActivity object
        newBusinessActivity.setId(activityId);

        // Set the value of the new activity
        newActivityRef.setValue(newBusinessActivity)
                .addOnSuccessListener(aVoid -> {
                    // Handle success, e.g., notify the callback
                    if (callback != null) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure, e.g., notify the callback with an error
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                });
    }
}
