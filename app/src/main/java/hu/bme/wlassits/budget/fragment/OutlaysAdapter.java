package hu.bme.wlassits.budget.fragment;

/**
 * Created by wlassits on 2017. 12. 03..
 */
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import hu.bme.wlassits.budget.R;

public class OutlaysAdapter extends FragmentStatePagerAdapter{
    private static int pages = 3;

    public OutlaysAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return pages;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return DailyTabFragment.newInstance(0, Resources.getSystem().getString(R.string.outlay_daily));
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return WeeklyTabFragment.newInstance(1, Resources.getSystem().getString(R.string.outlay_weekly));
            case 2: // Fragment # 1 - This will show SecondFragment
                return MonthlyTabFragment.newInstance(2, Resources.getSystem().getString(R.string.outlay_monthly));
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

}


