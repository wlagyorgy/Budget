package hu.bme.wlassits.budget.fragment.outlay;

import java.util.ArrayList;

import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class WeeklyOutlaysFragment extends BaseOutlayFragment {



    public static WeeklyOutlaysFragment createFragment() {
        return new WeeklyOutlaysFragment();
    }


    @Override
    public void setData(ArrayList<Outlay> listData){
        //TODO kiszűrni a csak heti adatokat
        outlayAdapter = new OutlayAdapter(listData, context);
        rvContent.setAdapter(outlayAdapter);
    }


}