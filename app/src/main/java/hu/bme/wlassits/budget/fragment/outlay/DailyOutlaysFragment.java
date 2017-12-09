package hu.bme.wlassits.budget.fragment.outlay;


import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.model.Globals;
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
        ArrayList<Outlay> todaysOutlays = getTodaysOutlays(listData);
        outlayAdapter = new OutlayAdapter(todaysOutlays, context);
        rvContent.setAdapter(outlayAdapter);
    }

    private ArrayList<Outlay> getTodaysOutlays(ArrayList<Outlay> listData) {
        ArrayList<Outlay> todaysOutlays = new ArrayList<>();

        for (Outlay o : listData) {
            if (isOutlayToday(o)) {
                todaysOutlays.add(o);
            }
        }
        return todaysOutlays;
    }

    private boolean isOutlayToday(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.DAY_OF_YEAR) == oCal.get(Calendar.DAY_OF_YEAR) && cal.get(Calendar.YEAR) == oCal.get(Calendar.YEAR);
    }
}