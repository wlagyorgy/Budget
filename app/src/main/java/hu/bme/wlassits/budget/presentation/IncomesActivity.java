package hu.bme.wlassits.budget.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.fragment.income.DailyIncomeFragment;
import hu.bme.wlassits.budget.fragment.income.MonthlyIncomeFragment;
import hu.bme.wlassits.budget.fragment.income.WeeklyIncomeFragment;
import hu.bme.wlassits.budget.fragment.outlay.DailyOutlaysFragment;
import hu.bme.wlassits.budget.fragment.outlay.MonthlyOutlaysFragment;
import hu.bme.wlassits.budget.fragment.outlay.WeeklyOutlaysFragment;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class IncomesActivity extends BaseActivity {

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomes);
        ViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        TabLayout tabLayout = findViewById(R.id.tlSlider);
        tabLayout.setupWithViewPager(vpPager);
    }



    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DailyIncomeFragment.createFragment();
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return WeeklyIncomeFragment.createFragment();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return MonthlyIncomeFragment.createFragment();
                default:
                    return null;
            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Daily";
                case 1:
                    return "Weekly";
                default:
                    return "Monthly";
            }
        }
    }
}
