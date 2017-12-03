package com.example.wlassits.budget;

/**
 * Created by wlassits on 2017. 12. 03..
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class OutlaysAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public OutlaysAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DailyTabFragment daily = new DailyTabFragment();
                return daily;
            case 1:
                WeeklyTabFragment weekly = new WeeklyTabFragment();
                return weekly;
            case 2:
                MonthlyTabFragment monthly = new MonthlyTabFragment();
                return monthly;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
