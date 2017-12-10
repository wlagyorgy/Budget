package hu.bme.wlassits.budget.fragment.income;


import java.util.ArrayList;

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
        incomeAdapter = new BaseIncomeFragment.IncomeAdapter(listData, context);
        rvContent.setAdapter(incomeAdapter);
    }


}
