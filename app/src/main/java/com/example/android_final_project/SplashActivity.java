package com.example.android_final_project;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class SplashActivity extends AppCompatActivity {
    private AppCompatImageView splash_IMG_logo;
    private TextView splash_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViews();
        startAnimationLogo(splash_IMG_logo);

    }

    private void startAnimationTitle(View view) {
        //start animation
        view.setVisibility(View.VISIBLE);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);//opacity

        view.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(3000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        //pass
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        moveToAuthActivity();
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        //pass
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                        //pass
                    }
                });

    }

    private void startAnimationLogo(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        //set start location
        view.setY(height/2.0f);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);//opacity

        view.animate()
                .alpha(1.0f)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .translationY(0)
                .setDuration(3000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animation) {
                        startAnimationTitle(splash_title);
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animation) {
                        //pass
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animation) {
                        //pass
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animation) {
                        //pass
                    }
                });
    }

    private void moveToAuthActivity() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    private void findViews() {
        splash_IMG_logo = findViewById(R.id.splash_IMG_logo);
        splash_title = findViewById(R.id.splash_title);
    }
}