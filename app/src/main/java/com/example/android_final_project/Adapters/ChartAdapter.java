package com.example.android_final_project.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.example.android_final_project.Model.MyPieChart;
import com.example.android_final_project.Model.PiePart;
import com.example.android_final_project.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartViewHolder>{

    private final ArrayList<MyPieChart> charts_list;

    public ChartAdapter(ArrayList<MyPieChart> charts_list) {
        this.charts_list = charts_list;

    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart_item, parent, false);
        return new ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {


        MyPieChart myPieChart = getItem(position);

        holder.title_chart_tv.setText(myPieChart.getTitle());

        int[] customColors = {
                Color.rgb(255, 99, 132), // Example color 1
                Color.rgb(54, 162, 235), // Example color 2
                Color.rgb(255, 206, 86), // Example color 3
                Color.rgb(75, 192, 192), // Example color 4
                Color.rgb(153, 102, 255) // Example color 5
        };



        ArrayList<PieEntry> entries = new ArrayList<>();
        for (PiePart currentPiePart: myPieChart.getPieParts()) {
            entries.add(new PieEntry((float) currentPiePart.getAmount(), currentPiePart.getName()));
        }

        PieDataSet pieDataSet = new PieDataSet(entries,"");
        pieDataSet.setColors(customColors);

        // Create PieData object
        PieData pieData = new PieData(pieDataSet);

        // Set value formatter to show percentages
        pieData.setValueFormatter(new PercentFormatter());

        // Optionally set the value text size and color
        pieDataSet.setValueTextSize(12f); // Adjust text size as needed
        pieDataSet.setValueTextColor(Color.BLACK); // Adjust text color as needed

        holder.chart_pie.setData(pieData);
        holder.chart_pie.getDescription().setEnabled(false);
        holder.chart_pie.animateY(1000);
        holder.chart_pie.invalidate();
    }

    @Override
    public int getItemCount() {
        return charts_list.size();
    }

    private MyPieChart getItem(int position) {
        return charts_list.get(position);
    }


    public static class ChartViewHolder extends RecyclerView.ViewHolder {

        private TextView title_chart_tv;
        private PieChart chart_pie;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            chart_pie = itemView.findViewById(R.id.chart_pie);
            title_chart_tv = itemView.findViewById(R.id.title_chart_tv);
        }
    }
}
