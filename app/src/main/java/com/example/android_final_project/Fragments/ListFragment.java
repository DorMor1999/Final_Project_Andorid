package com.example.android_final_project.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final_project.R;

public class ListFragment extends Fragment {

    private RecyclerView activities_LST;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);

        return  v;
    }

    private void findViews(View v) {
        activities_LST = v.findViewById(R.id.activities_LST);
    }
}