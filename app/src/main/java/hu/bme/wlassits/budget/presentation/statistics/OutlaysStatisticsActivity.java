package hu.bme.wlassits.budget.presentation.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Outlay;
import hu.bme.wlassits.budget.presentation.BaseActivity;

/**
 * Created by Adam Varga on 12/10/2017.
 */

public class OutlaysStatisticsActivity extends BaseActivity {

    PieChart chartDailyOutlay;
    PieChart chartWeeklyOutlay;
    PieChart chartMonthlyOutlay;
    TextView tvDailyOutlayStat;
    TextView tvWeeklyOutlayStat;
    TextView tvMonthlyOutlayStat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlay_statistics);

        chartDailyOutlay = findViewById(R.id.chartDailyOutlay);
        chartWeeklyOutlay = findViewById(R.id.chartWeeklyOutlay);
        chartMonthlyOutlay = findViewById(R.id.chartMonthlyOutlay);

        tvDailyOutlayStat = findViewById(R.id.tvDailyOutlayStat);
        tvWeeklyOutlayStat = findViewById(R.id.tvWeeklyOutlayStat);
        tvMonthlyOutlayStat = findViewById(R.id.tvMonthlyOutlayStat);

        initDailyChart();
        initWeeklyChart();
        initMonthlyChart();
    }

    private int typesPositionInTheList(String s) {
        for (int i = 0; i < Globals.outlay_types.size(); i++) {
            if (Globals.outlay_types.get(i).equals(s)) {
                return i;
            }
        }
        return 0;
    }

    private void initDailyChart() {
        int outlayTypesSize = Globals.outlay_types.size();
        int[] todaysOutlay = new int[outlayTypesSize];

        //Init
        for (int i = 0; i < outlayTypesSize; i++) {
            todaysOutlay[i] = 0;
        }

        for (Outlay o : Globals.outlays) {
            if (isOutlayToday(o))
                todaysOutlay[typesPositionInTheList(o.getType())] += o.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < outlayTypesSize; i++) {
            if (todaysOutlay[i] != 0)
                entries.add(new PieEntry(todaysOutlay[i], Globals.outlay_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvDailyOutlayStat.setVisibility(View.VISIBLE);
            tvDailyOutlayStat.setText("You spent today" + "\n" + summarizeArray(todaysOutlay) + " Ft");
            chartDailyOutlay.setVisibility(View.VISIBLE);

            PieDataSet dataset = new PieDataSet(entries, "");

            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartDailyOutlay.setDescription(null);
            chartDailyOutlay.setData(data);

            chartDailyOutlay.animateY(5000);
            chartDailyOutlay.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
        } else {
            chartDailyOutlay.setVisibility(View.GONE);
            tvDailyOutlayStat.setVisibility(View.GONE);

        }
    }

    private boolean isOutlayToday(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.DAY_OF_YEAR) == oCal.get(Calendar.DAY_OF_YEAR) && cal.get(Calendar.YEAR) == oCal.get(Calendar.YEAR);
    }

    private void initWeeklyChart() {
        int outlayTypesSize = Globals.outlay_types.size();
        int[] thisWeeksOutlay = new int[outlayTypesSize];

        //Init
        for (int i = 0; i < outlayTypesSize; i++) {
            thisWeeksOutlay[i] = 0;
        }

        for (Outlay o : Globals.outlays) {
            if (isOutlayThisWeek(o))
                thisWeeksOutlay[typesPositionInTheList(o.getType())] += o.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < outlayTypesSize; i++) {
            if (thisWeeksOutlay[i] != 0)
                entries.add(new PieEntry(thisWeeksOutlay[i], Globals.outlay_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvWeeklyOutlayStat.setVisibility(View.VISIBLE);
            tvWeeklyOutlayStat.setText("You spent this week" + "\n" + summarizeArray(thisWeeksOutlay) + " Ft");
            chartWeeklyOutlay.setVisibility(View.VISIBLE);
            PieDataSet dataset = new PieDataSet(entries, "");

            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartWeeklyOutlay.setDescription(null);
            chartWeeklyOutlay.setData(data);

            chartWeeklyOutlay.animateY(5000);
            chartWeeklyOutlay.saveToGallery("/sd/mychart2.jpg", 85); // 85 is the quality of the image
        } else {
            chartWeeklyOutlay.setVisibility(View.GONE);
            tvWeeklyOutlayStat.setVisibility(View.GONE);
        }
    }

    private boolean isOutlayThisWeek(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.WEEK_OF_YEAR) == oCal.get(Calendar.WEEK_OF_YEAR);
    }

    private void initMonthlyChart() {
        int outlayTypesSize = Globals.outlay_types.size();
        int[] thisMonthsOutlay = new int[outlayTypesSize];

        //Init
        for (int i = 0; i < outlayTypesSize; i++) {
            thisMonthsOutlay[i] = 0;
        }

        for (Outlay o : Globals.outlays) {
            if (isOutlayThisMonth(o))
                thisMonthsOutlay[typesPositionInTheList(o.getType())] += o.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < outlayTypesSize; i++) {
            if (thisMonthsOutlay[i] != 0)
                entries.add(new PieEntry(thisMonthsOutlay[i], Globals.outlay_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvMonthlyOutlayStat.setVisibility(View.VISIBLE);
            tvMonthlyOutlayStat.setText("You spent this month" + "\n" + summarizeArray(thisMonthsOutlay) + " Ft");
            chartMonthlyOutlay.setVisibility(View.VISIBLE);
            PieDataSet dataset = new PieDataSet(entries, "");


            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartMonthlyOutlay.setDescription(null);
            chartMonthlyOutlay.setData(data);

            chartMonthlyOutlay.animateY(5000);
            chartMonthlyOutlay.saveToGallery("/sd/mychart3.jpg", 85); // 85 is the quality of the image

        } else {
            chartMonthlyOutlay.setVisibility(View.GONE);
            tvMonthlyOutlayStat.setVisibility(View.GONE);
        }
    }


    private boolean isOutlayThisMonth(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.MONTH) == oCal.get(Calendar.MONTH);
    }


    private int summarizeArray(int[] items) {
        int sum = 0;

        for (int i = 0; i < items.length; i++) {
            sum += items[i];
        }

        return sum;

    }
}
