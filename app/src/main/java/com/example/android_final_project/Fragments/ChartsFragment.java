package com.example.android_final_project.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final_project.Activities.ChartsActivity;
import com.example.android_final_project.Adapters.ChartAdapter;
import com.example.android_final_project.Model.MyPieChart;
import com.example.android_final_project.R;

import java.util.ArrayList;


public class ChartsFragment extends Fragment {

    private RecyclerView charts_LST ;
    private ArrayList<MyPieChart> chartsList = new ArrayList<>();

    private ChartAdapter chartAdapter;

    public void setChartsList(ArrayList<MyPieChart> list) {
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
            chartAdapter = new ChartAdapter(chartsList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            charts_LST.setLayoutManager(linearLayoutManager);
            charts_LST.setAdapter(chartAdapter);
        }

    }

    private void findViews(View v) {
        charts_LST = v.findViewById(R.id.charts_LST);
    }
}