package hu.bme.wlassits.budget.fragment.outlay;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.managers.Formatters;
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
        ArrayList<Outlay> thisMonthsOutlays = getThisMonthsOutlays(listData);
        outlayAdapter = new OutlayAdapter(thisMonthsOutlays, context);
        rvContent.setAdapter(outlayAdapter);
    }

    private ArrayList<Outlay> getThisMonthsOutlays(ArrayList<Outlay> listData) {
        ArrayList<Outlay> thisMonthsOutlays = new ArrayList<>();

        for (Outlay o : listData) {
            if (isOutlayThisMonth(o)) {
                thisMonthsOutlays.add(o);
            }
        }
        return thisMonthsOutlays;
    }


    private boolean isOutlayThisMonth(Outlay o) {
        Calendar cal = Calendar.getInstance();
        Calendar oCal = Calendar.getInstance();
        oCal.setTime(o.getDate());

        return cal.get(Calendar.MONTH) == oCal.get(Calendar.MONTH);
    }


}