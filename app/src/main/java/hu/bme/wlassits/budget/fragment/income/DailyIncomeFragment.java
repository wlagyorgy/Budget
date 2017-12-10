package hu.bme.wlassits.budget.fragment.income;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.fragment.income.BaseIncomeFragment;
import hu.bme.wlassits.budget.fragment.income.DailyIncomeFragment;
import hu.bme.wlassits.budget.model.Income;


/**
 * Created by Adam Varga on 12/9/2017.
 */

public class DailyIncomeFragment extends BaseIncomeFragment {


    public static DailyIncomeFragment createFragment() {
        return new DailyIncomeFragment();
    }

    @Override
    public void setData(ArrayList<Income> listData) {
        ArrayList<Income> todaysIncomes = getTodaysIncomes(listData);
        incomeAdapter = new IncomeAdapter(todaysIncomes, context);
        rvContent.setAdapter(incomeAdapter);
    }

    private ArrayList<Income> getTodaysIncomes(ArrayList<Income> listData) {
        ArrayList<Income> todaysIncomes = new ArrayList<>();

        for (Income i : listData) {
            if (isIncomeToday(i)) {
                todaysIncomes.add(i);
            }
        }
        return todaysIncomes;
    }

    private boolean isIncomeToday(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

        return cal.get(Calendar.DAY_OF_YEAR) == oCal.get(Calendar.DAY_OF_YEAR) && cal.get(Calendar.YEAR) == oCal.get(Calendar.YEAR);
    }

}
