package com.example.android_final_project.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android_final_project.Interfaces.AddUserCallback;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private DialogUtils dialogUtils;
    private final String ERROR_TITLE = "Authentication Error";


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

        dialogUtils = new DialogUtils();

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
            updateDB(user);
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            String errorMsg = "Something went wrong, try again later.";
            if (response == null) {
                // User pressed back button
                errorMsg = "Sign in cancelled.";
            }

            switch (response.getError().getErrorCode()) {
                case ErrorCodes.NO_NETWORK:
                    errorMsg = "No internet connection.";
                    break;
                case ErrorCodes.UNKNOWN_ERROR:
                    errorMsg = "Unknown error occurred.";
                    break;
                case ErrorCodes.EMAIL_MISMATCH_ERROR:
                    errorMsg = "Email mismatch error.";
                    break;
                default:
                    errorMsg = "Something went wrong, try again later.";
                    break;
            }

            dialogUtils.showDialog(AuthActivity.this, ERROR_TITLE, errorMsg,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Log out the user
                            FirebaseAuth.getInstance().signOut();
                            signIn(); // Move to sign-in after OK
                        }
                    }
            );
        }
    }

    private void updateDB(FirebaseUser user) {
        DbOperations.getInstance().addUserToDB(user, new AddUserCallback() {
            @Override
            public void onSuccess() {
                // Handle success case
                moveToMenuActivity();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure case
                dialogUtils.showDialog(AuthActivity.this, ERROR_TITLE, "Something went wrong, try again later.",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Log out the user
                                FirebaseAuth.getInstance().signOut();
                                signIn(); // Move to sign-in after OK
                            }
                        }
                );
            }
        });
    }

    private void moveToMenuActivity() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}