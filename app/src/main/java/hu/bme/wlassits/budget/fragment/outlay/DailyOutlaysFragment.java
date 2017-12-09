package hu.bme.wlassits.budget.fragment.outlay;


import java.util.ArrayList;

import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class DailyOutlaysFragment extends BaseOutlayFragment {


    public static DailyOutlaysFragment createFragment() {
        return new DailyOutlaysFragment();
    }




    @Override
    public void setData(ArrayList<Outlay> listData) {
        //TODO kiszűrni a csak napi adatokat
        outlayAdapter = new OutlayAdapter(listData, context);
        rvContent.setAdapter(outlayAdapter);
    }

}