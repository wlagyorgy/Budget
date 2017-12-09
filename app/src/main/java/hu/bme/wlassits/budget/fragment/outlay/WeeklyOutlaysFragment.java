package hu.bme.wlassits.budget.fragment.outlay;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class WeeklyOutlaysFragment extends BaseOutlayFragment {


    public static WeeklyOutlaysFragment createFragment() {
        return new WeeklyOutlaysFragment();
    }


    @Override
    public void setData(ArrayList<Outlay> listData) {
        ArrayList<Outlay> thisWeeksOutlays = getThisWeeksOutlays(listData);
        outlayAdapter = new OutlayAdapter(thisWeeksOutlays, context);
        rvContent.setAdapter(outlayAdapter);
    }


    private ArrayList<Outlay> getThisWeeksOutlays(ArrayList<Outlay> listData) {
        ArrayList<Outlay> thisWeeksOutlays = new ArrayList<>();

        for (Outlay o : listData) {
            if (isOutlayThisWeek(o)) {
                thisWeeksOutlays.add(o);
            }
        }
        return thisWeeksOutlays;
    }

    private boolean isOutlayThisWeek(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.WEEK_OF_YEAR) == oCal.get(Calendar.WEEK_OF_YEAR);
    }


}