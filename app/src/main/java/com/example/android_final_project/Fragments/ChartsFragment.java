package com.example.android_final_project.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final_project.Activities.ChartsActivity;
import com.example.android_final_project.Model.MyChart;
import com.example.android_final_project.R;

import java.util.ArrayList;


public class ChartsFragment extends Fragment {

    private RecyclerView charts_LST ;
    private ArrayList<MyChart> chartsList = new ArrayList<>();

    public void setChartsList(ArrayList<MyChart> list) {
        this.chartsList = list != null ? list : new ArrayList<>();
        initViews();
    }

    public ChartsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_charts, container, false);

        findViews(v);
        initViews();

        return v;
    }

    private void initViews() {
        if (getActivity() instanceof ChartsActivity) {
            Log.d("Charts fragment", chartsList.toString());
        }

    }

    private void findViews(View v) {
        charts_LST = v.findViewById(R.id.charts_LST);
    }
}