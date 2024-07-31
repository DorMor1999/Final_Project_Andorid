package com.example.android_final_project.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android_final_project.Activities.ActivitiesActivity;
import com.example.android_final_project.Adapters.ActivityAdapter;
import com.example.android_final_project.Interfaces.DeleteClick;
import com.example.android_final_project.Model.BusinessActivity;
import com.example.android_final_project.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private RecyclerView activities_LST;
    private ArrayList<BusinessActivity> businessActivityList = new ArrayList<>();
    private ActivityAdapter activityAdapter;

    private DeleteClick callbackDeleteItemClicked;

    public void setCallbackDeleteItemClicked(DeleteClick callbackDeleteItemClicked){
        this.callbackDeleteItemClicked = callbackDeleteItemClicked;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    public void setBusinessActivityList(ArrayList<BusinessActivity> list) {
        this.businessActivityList = list != null ? list : new ArrayList<>();
        initViews();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(v);
        initViews();
        return  v;
    }

    private void initViews() {
        if (getActivity() instanceof ActivitiesActivity) {
            // Safe to use context here

            Log.d("ListFragment", "initViews called");
            Log.d("ListFragment", "Adapter data: " + businessActivityList.toString());

            activityAdapter = new ActivityAdapter(businessActivityList, new ActivityAdapter.OnItemClickListener() {
                @Override
                public void onDeleteClick(String item_id) {
                    if (callbackDeleteItemClicked != null) {
                        callbackDeleteItemClicked.listItemDeleteClicked(item_id);
                    }
                }
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            activities_LST.setLayoutManager(linearLayoutManager);
            activities_LST.setAdapter(activityAdapter);
        }
    }

    private void findViews(View v) {
        activities_LST = v.findViewById(R.id.activities_LST);
    }
}