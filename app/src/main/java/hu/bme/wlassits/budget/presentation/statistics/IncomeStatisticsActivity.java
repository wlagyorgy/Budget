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
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.presentation.BaseActivity;


public class IncomeStatisticsActivity extends BaseActivity{
    PieChart chartDailyIncome;
    PieChart chartWeeklyIncome;
    PieChart chartMonthlyIncome;
    TextView tvDailyIncomeStat;
    TextView tvWeeklyIncomeStat;
    TextView tvMonthlyIncomeStat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_statistics);

        chartDailyIncome = findViewById(R.id.chartDailyIncome);
        chartWeeklyIncome = findViewById(R.id.chartWeeklyIncome);
        chartMonthlyIncome = findViewById(R.id.chartMonthlyIncome);

        tvDailyIncomeStat = findViewById(R.id.tvDailyIncomeStat);
        tvWeeklyIncomeStat = findViewById(R.id.tvWeeklyIncomeStat);
        tvMonthlyIncomeStat = findViewById(R.id.tvMonthlyIncomeStat);

        initDailyChart();
        initWeeklyChart();
        initMonthlyChart();
    }

    private int typesPositionInTheList(String s) {
        for (int i = 0; i < Globals.income_types.size(); i++) {
            if (Globals.income_types.get(i).equals(s)) {
                return i;
            }
        }
        return 0;
    }

    private void initDailyChart() {
        int incomeTypesSize = Globals.income_types.size();
        int[] todaysIncome = new int[incomeTypesSize];

        //Init
        for (int i = 0; i < incomeTypesSize; i++) {
            todaysIncome[i] = 0;
        }

        for (Income i : Globals.incomes) {
            if (isIncomeToday(i))
                todaysIncome[typesPositionInTheList(i.getType())] += i.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < incomeTypesSize; i++) {
            if (todaysIncome[i] != 0)
                entries.add(new PieEntry(todaysIncome[i], Globals.income_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvDailyIncomeStat.setVisibility(View.VISIBLE);
            tvDailyIncomeStat.setText("You got today" + "\n" + summarizeArray(todaysIncome) + " Ft");
            chartDailyIncome.setVisibility(View.VISIBLE);

            PieDataSet dataset = new PieDataSet(entries, "");

            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartDailyIncome.setDescription(null);
            chartDailyIncome.setData(data);

            chartDailyIncome.animateY(5000);
            chartDailyIncome.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image
        } else {
            chartDailyIncome.setVisibility(View.GONE);
            tvDailyIncomeStat.setVisibility(View.GONE);

        }
    }

    private boolean isIncomeToday(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

        return cal.get(Calendar.DAY_OF_YEAR) == oCal.get(Calendar.DAY_OF_YEAR) && cal.get(Calendar.YEAR) == oCal.get(Calendar.YEAR);
    }

    private void initWeeklyChart() {
        int incomeTypesSize = Globals.income_types.size();
        int[] thisWeeksIncome = new int[incomeTypesSize];

        //Init
        for (int i = 0; i < incomeTypesSize; i++) {
            thisWeeksIncome[i] = 0;
        }

        for (Income i: Globals.incomes) {
            if (isIncomeThisWeek(i))
                thisWeeksIncome[typesPositionInTheList(i.getType())] += i.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < incomeTypesSize; i++) {
            if (thisWeeksIncome[i] != 0)
                entries.add(new PieEntry(thisWeeksIncome[i], Globals.income_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvWeeklyIncomeStat.setVisibility(View.VISIBLE);
            tvWeeklyIncomeStat.setText("You got this week" + "\n" + summarizeArray(thisWeeksIncome) + " Ft");
            chartWeeklyIncome.setVisibility(View.VISIBLE);
            PieDataSet dataset = new PieDataSet(entries, "");

            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartWeeklyIncome.setDescription(null);
            chartWeeklyIncome.setData(data);

            chartWeeklyIncome.animateY(5000);
            chartWeeklyIncome.saveToGallery("/sd/mychart2.jpg", 85); // 85 is the quality of the image
        } else {
            chartWeeklyIncome.setVisibility(View.GONE);
            tvWeeklyIncomeStat.setVisibility(View.GONE);
        }
    }

    private boolean isIncomeThisWeek(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

        return cal.get(Calendar.WEEK_OF_YEAR) == oCal.get(Calendar.WEEK_OF_YEAR);
    }

    private void initMonthlyChart() {
        int incomeTypesSize = Globals.income_types.size();
        int[] thisMonthsIncome = new int[incomeTypesSize];

        //Init
        for (int i = 0; i < incomeTypesSize; i++) {
            thisMonthsIncome[i] = 0;
        }

        for (Income i : Globals.incomes) {
            if (isIncomeThisMonth(i))
                thisMonthsIncome[typesPositionInTheList(i.getType())] += i.getValue();
        }


        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < incomeTypesSize; i++) {
            if (thisMonthsIncome[i] != 0)
                entries.add(new PieEntry(thisMonthsIncome[i], Globals.income_types.get(i)));
        }

        if (!entries.isEmpty()) {
            tvMonthlyIncomeStat.setVisibility(View.VISIBLE);
            tvMonthlyIncomeStat.setText("You got this month" + "\n" + summarizeArray(thisMonthsIncome) + " Ft");
            chartMonthlyIncome.setVisibility(View.VISIBLE);
            PieDataSet dataset = new PieDataSet(entries, "");


            PieData data = new PieData(dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            chartMonthlyIncome.setDescription(null);
            chartMonthlyIncome.setData(data);

            chartMonthlyIncome.animateY(5000);
            chartMonthlyIncome.saveToGallery("/sd/mychart3.jpg", 85); // 85 is the quality of the image

        } else {
            chartMonthlyIncome.setVisibility(View.GONE);
            tvMonthlyIncomeStat.setVisibility(View.GONE);
        }
    }


    private boolean isIncomeThisMonth(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

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


