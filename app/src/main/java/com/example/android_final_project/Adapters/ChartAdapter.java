package com.example.android_final_project.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.android_final_project.Activities.ChartsActivity;
import com.example.android_final_project.Model.LinePart;
import com.example.android_final_project.Model.MyChart;
import com.example.android_final_project.Model.MyLineChart;
import com.example.android_final_project.Model.MyPieChart;
import com.example.android_final_project.Model.PiePart;
import com.example.android_final_project.R;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartViewHolder>{

    private final ArrayList<MyChart> charts_list;

    public ChartAdapter(ArrayList<MyChart> charts_list) {
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
        MyChart myChart = getItem(position);

        if (holder.any_chart_view == null) {
            Log.e("ChartAdapter", "AnyChartView is null in position: " + position);
            return;
        }

        if (myChart instanceof MyPieChart){
            Pie pie = AnyChart.pie();
            /*
            holder.any_chart_view.setProgressBar(holder.progressBar);

            pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                @Override
                public void onClick(Event event) {
                    Toast.makeText(holder.itemView.getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                }
            });*/
            List<DataEntry> data = new ArrayList<>();
            MyPieChart myPieChart = (MyPieChart)myChart;
            for (PiePart  currentPiePart: myPieChart.getPieParts())
            {
                data.add(new ValueDataEntry(currentPiePart.getName(), currentPiePart.getAmount()));
            }
            pie.data(data);

            pie.title(myChart.getTitle());

            pie.labels().position("outside");

            holder.any_chart_view.setChart(pie);
            }
        else{
            Cartesian cartesian = AnyChart.line();

            cartesian.animation(true);

            cartesian.padding(10d, 20d, 5d, 20d);

            cartesian.crosshair().enabled(true);
            cartesian.crosshair()
                    .yLabel(true)
                    // TODO ystroke
                    .yStroke((Stroke) null, null, null, (String) null, (String) null);

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

            cartesian.title(myChart.getTitle());

            cartesian.yAxis(0).title("Money");
            cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

            MyLineChart myLineChart = (MyLineChart) myChart;
            List<DataEntry> seriesData = new ArrayList<>();
            for (LinePart currentLinePart: myLineChart.getLineParts())
            {
                seriesData.add(new CustomDataEntry(currentLinePart.getDate(), currentLinePart.getOverall(), currentLinePart.getIncomes(), currentLinePart.getExpenses()));
            }
            Set set = Set.instantiate();
            set.data(seriesData);

            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
            Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
            Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

            Line series1 = cartesian.line(series1Mapping);
            series1.name("Overall");

            Line series2 = cartesian.line(series2Mapping);
            series2.name("Incomes");

            Line series3 = cartesian.line(series3Mapping);
            series3.name("Expenses");
            holder.any_chart_view.setChart(cartesian);
        }
    }

    @Override
    public int getItemCount() {
        return charts_list.size();
    }

    private MyChart getItem(int position) {
        return charts_list.get(position);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }


    public static class ChartViewHolder extends RecyclerView.ViewHolder {

        private final AnyChartView any_chart_view;
        //private final ProgressBar progressBar;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            any_chart_view = itemView.findViewById(R.id.any_chart_view);
            //progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}
