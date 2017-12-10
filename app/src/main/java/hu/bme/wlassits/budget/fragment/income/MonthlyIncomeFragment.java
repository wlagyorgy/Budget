package hu.bme.wlassits.budget.fragment.income;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.model.Income;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class MonthlyIncomeFragment  extends  BaseIncomeFragment{

    public static MonthlyIncomeFragment createFragment() {
        return new MonthlyIncomeFragment();
    }


    @Override
    public void setData(ArrayList<Income> listData) {
        ArrayList<Income> thisMonthsIncomes = getThisMonthsIncomes(listData);
        incomeAdapter = new IncomeAdapter(thisMonthsIncomes, context);
        rvContent.setAdapter(incomeAdapter);
    }

    private ArrayList<Income> getThisMonthsIncomes(ArrayList<Income> listData) {
        ArrayList<Income> thisMonthsIncomes = new ArrayList<>();

        for (Income i: listData) {
            if (isIncomeThisMonth(i)) {
                thisMonthsIncomes.add(i);
            }
        }
        return thisMonthsIncomes;
    }


    private boolean isIncomeThisMonth(Income i) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(i.getDate());

        return cal.get(Calendar.MONTH) == oCal.get(Calendar.MONTH);
    }


}
