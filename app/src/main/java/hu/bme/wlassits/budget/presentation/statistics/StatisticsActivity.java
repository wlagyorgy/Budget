package hu.bme.wlassits.budget.presentation.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.presentation.BaseActivity;

import static hu.bme.wlassits.budget.model.Globals.incomes;
import static hu.bme.wlassits.budget.model.Globals.outlays;


public class StatisticsActivity extends BaseActivity {


    PieChart chartSummary;
    Button outlaysSumBtn;
    Button incomesSumBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        outlaysSumBtn = findViewById(R.id.btnOutlaysSum);
        incomesSumBtn = findViewById(R.id.btnIncomesSum);
        chartSummary = findViewById(R.id.chartSummary);
        outlaysSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), OutlaysStatisticsActivity.class);
                startActivity(intent);
            }
        });

        incomesSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), IncomeStatisticsActivity.class);
                startActivity(intent);
            }

        });
        initSummaryDiagram();
    }


    private void initSummaryDiagram() {
        long outlaysSum = 0;
        long incomesSum = 0;

        for (int i = 0; i < outlays.size(); i++) {
            outlaysSum += outlays.get(i).getValue();
        }
        for (int i = 0; i < incomes.size(); i++) {
            incomesSum += incomes.get(i).getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        if (incomesSum != 0)
            entries.add(new PieEntry(incomesSum, "Incomes"));
        if (outlaysSum != 0)
            entries.add(new PieEntry(outlaysSum, "Outlays"));


        if (!entries.isEmpty()) {
            chartSummary.setVisibility(View.VISIBLE);
            PieDataSet dataset = new PieDataSet(entries, "");


            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            Description desc = new Description();
            desc.setText("Overall");
            chartSummary.setDescription(desc);
            chartSummary.setData(data);

            chartSummary.animateY(5000);
            chartSummary.saveToGallery("/sd/mychart5.jpg", 85);

        } else {
            chartSummary.setVisibility(View.GONE);
        }
    }

}
