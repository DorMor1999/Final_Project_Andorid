package com.example.android_final_project.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_final_project.Interfaces.DeleteClick;
import com.example.android_final_project.Model.BusinessActivity;
import com.example.android_final_project.Model.Expense;
import com.example.android_final_project.Model.Income;
import com.example.android_final_project.R;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>{

    private final ArrayList<BusinessActivity> activities_list;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClick(String item_id);
    }

    public ActivityAdapter(ArrayList<BusinessActivity> activities_list, OnItemClickListener onItemClickListener) {
        this.activities_list = activities_list;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        BusinessActivity businessActivity = getItem(position);

        Log.d("Adapter", "Binding data at position " + position + ": " + businessActivity.toString());

        // Bind the businessActivity data to the views
        holder.date_tv_list_item.setText(businessActivity.getDate());
        holder.description_tv_list_item.setText("Description: " + businessActivity.getDescription());
        holder.price_tv_list_item.setText("Price: " + String.format("%.2f", businessActivity.getPrice()));
        if(businessActivity instanceof Expense){
            holder.title_tv_list_item.setText("Expense");
            holder.type_tv_list_item.setText("Type: " + ((Expense) businessActivity).getExpenseTypeString());
            holder.card_list_background.setImageResource(R.drawable.background_card_expense);
        }else{
            holder.title_tv_list_item.setText("Income");
            holder.type_tv_list_item.setText("Type: " + ((Income) businessActivity).getIncomeTypeString());
            holder.card_list_background.setImageResource(R.drawable.background_card_income);
        }

        holder.delete_btn_list_item.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onDeleteClick(businessActivity.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return activities_list.size();
    }

    private BusinessActivity getItem(int position) {
        return activities_list.get(position);
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        private final CardView card_list_item;
        private final TextView date_tv_list_item;
        private final TextView title_tv_list_item;
        private final TextView description_tv_list_item;
        private final TextView type_tv_list_item;
        private final TextView price_tv_list_item;
        private  final Button delete_btn_list_item;
        private final ImageView card_list_background;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            card_list_item = itemView.findViewById(R.id.card_list_item);
            date_tv_list_item = itemView.findViewById(R.id.date_tv_list_item);
            title_tv_list_item = itemView.findViewById(R.id.title_tv_list_item);
            description_tv_list_item = itemView.findViewById(R.id.description_tv_list_item);
            type_tv_list_item = itemView.findViewById(R.id.type_tv_list_item);
            price_tv_list_item = itemView.findViewById(R.id.price_tv_list_item);
            delete_btn_list_item = itemView.findViewById(R.id.delete_btn_list_item);
            card_list_background = itemView.findViewById(R.id.card_list_background);
        }
    }
}