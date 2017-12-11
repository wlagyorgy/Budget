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


        long outlaysSum = countOutlays();
        long incomesSum = countIncomes();

        //Ha van már adat kiadásról, akkor legyen kattintható a gomb, ekkor színnel jelöljük is.
        //Zöld > kattintható ; Szürke > nem kattintható.
        if (outlaysSum != 0) {
            outlaysSumBtn.setClickable(true);
            outlaysSumBtn.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            outlaysSumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), OutlaysStatisticsActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            outlaysSumBtn.setClickable(false);
            outlaysSumBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        //Ha van már adat bevételről, akkor legyen kattintható a gomb, ekkor színnel jelöljük is.
        //Zöld > kattintható ; Szürke > nem kattintható.
        if (incomesSum != 0) {
            incomesSumBtn.setClickable(true);
            incomesSumBtn.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            incomesSumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), IncomeStatisticsActivity.class);
                    startActivity(intent);
                }

            });
        } else {
            incomesSumBtn.setClickable(false);
            incomesSumBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        }


        initSummaryDiagram(outlaysSum, incomesSum);
    }


    private void initSummaryDiagram(long outlaysSum, long incomesSum) {

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

    public long countOutlays() {
        long outlaysSum = 0;
        for (int i = 0; i < outlays.size(); i++) {
            outlaysSum += outlays.get(i).getValue();
        }
        return outlaysSum;
    }

    public long countIncomes() {
        long incomesSum = 0;
        for (int i = 0; i < incomes.size(); i++) {
            incomesSum += incomes.get(i).getValue();
        }
        return incomesSum;
    }

}
