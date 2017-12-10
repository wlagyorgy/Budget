package hu.bme.wlassits.budget.fragment.income;

import java.util.ArrayList;

import hu.bme.wlassits.budget.fragment.outlay.BaseOutlayFragment;
import hu.bme.wlassits.budget.fragment.outlay.DailyOutlaysFragment;
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class DailyIncomeFragment extends BaseIncomeFragment {


    public static DailyIncomeFragment createFragment() {
        return new DailyIncomeFragment();
    }

    @Override
    public void setData(ArrayList<Income> listData) {
        incomeAdapter = new BaseIncomeFragment.IncomeAdapter(listData, context);
        rvContent.setAdapter(incomeAdapter);
    }

}
