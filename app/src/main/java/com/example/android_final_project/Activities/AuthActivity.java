package com.example.android_final_project.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_final_project.Model.BusinessActivityHashMap;
import com.example.android_final_project.Model.UsersHashMap;
import com.example.android_final_project.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            signIn();
        }
        else {
            moveToMenuActivity();
        }
    }

    private void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.budget_app_icon_xml)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //updateDB(user);
            moveToMenuActivity();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            if (response == null) {
                // User pressed back button
                showErrorDialog("Sign in cancelled.");
                return;
            }

            switch (response.getError().getErrorCode()) {
                case ErrorCodes.NO_NETWORK:
                    showErrorDialog("No internet connection.");
                    break;
                case ErrorCodes.UNKNOWN_ERROR:
                    showErrorDialog("Unknown error occurred.");
                    break;
                case ErrorCodes.EMAIL_MISMATCH_ERROR:
                    showErrorDialog("Email mismatch error.");
                    break;
                default:
                    showErrorDialog("Unknown sign-in response.");
                    break;
            }

        }
    }

    private void updateDB(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        UsersHashMap newUsersMap = new UsersHashMap();
        newUsersMap.getAllUsers().put(user.getUid() ,new BusinessActivityHashMap());
        usersRef.setValue(newUsersMap);
    }

    private void moveToMenuActivity() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Authentication Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}