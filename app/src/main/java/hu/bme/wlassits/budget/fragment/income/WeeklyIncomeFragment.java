package hu.bme.wlassits.budget.fragment.income;


import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.model.Income;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class WeeklyIncomeFragment extends BaseIncomeFragment {


    public static WeeklyIncomeFragment createFragment() {
        return new WeeklyIncomeFragment();
    }

    @Override
    public void setData(ArrayList<Income> listData) {
        ArrayList<Income> thisWeeksIncomes = getThisWeeksIncomes(listData);
        incomeAdapter = new IncomeAdapter(thisWeeksIncomes, context);
        rvContent.setAdapter(incomeAdapter);
    }


    private ArrayList<Income> getThisWeeksIncomes(ArrayList<Income> listData) {
        ArrayList<Income> thisWeeksIncomes = new ArrayList<>();

        for (Income i : listData) {
            if (isIncomeThisWeek(i)) {
                thisWeeksIncomes.add(i);
            }
        }
        return thisWeeksIncomes;
    }

    private boolean isIncomeThisWeek(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

        return cal.get(Calendar.WEEK_OF_YEAR) == oCal.get(Calendar.WEEK_OF_YEAR);
    }



}
