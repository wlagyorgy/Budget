package hu.bme.wlassits.budget.fragment.outlay;

import java.util.ArrayList;

import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class MonthlyOutlaysFragment extends BaseOutlayFragment {


    public static MonthlyOutlaysFragment createFragment() {
        return new MonthlyOutlaysFragment();
    }


    @Override
    public void setData(ArrayList<Outlay> listData) {
        //TODO kiszűrni a csak havi adatokat
        outlayAdapter = new OutlayAdapter(listData, context);
        rvContent.setAdapter(outlayAdapter);
    }


}