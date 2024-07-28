package com.example.android_final_project.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_final_project.Activities.AuthActivity;
import com.example.android_final_project.Activities.MenuActivity;
import com.example.android_final_project.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class NavFragment extends Fragment {


    private AppCompatImageView FRAG_NAV_LOGO;
    private TextView FRAG_NAV_TITLE;
    private ExtendedFloatingActionButton FRAG_NAV_LOG_OUT_BTN;
    private String navTitle;

    public NavFragment() {
        // Required empty public constructor
    }

    public void setTextNavTitle(String navTitle){
        this.navTitle = navTitle;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nav, container, false);
        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        changeTitleText();
        FRAG_NAV_LOG_OUT_BTN.setOnClickListener(v -> logOutClicked());
        FRAG_NAV_LOGO.setOnClickListener(v -> moveToMenu());
    }

    private void moveToMenu() {
        Intent i = new Intent(getActivity(), MenuActivity.class);
        startActivity(i);
        getActivity().finish(); // Close the current activity
    }

    private void logOutClicked() {
        if (getActivity() != null) {
            AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(getActivity(), AuthActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    });
        }
    }

    private void changeTitleText() {
        FRAG_NAV_TITLE.setText(navTitle);
    }

    private void findViews(View v) {
        FRAG_NAV_LOGO = v.findViewById(R.id.FRAG_NAV_LOGO);
        FRAG_NAV_TITLE = v.findViewById(R.id.FRAG_NAV_TITLE);
        FRAG_NAV_LOG_OUT_BTN = v.findViewById(R.id.FRAG_NAV_LOG_OUT_BTN);
    }
}