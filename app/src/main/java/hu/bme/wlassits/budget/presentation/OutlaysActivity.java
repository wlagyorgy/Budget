package hu.bme.wlassits.budget.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.fragment.DailyOutlaysFragment;
import hu.bme.wlassits.budget.fragment.MonthlyOutlaysFragment;
import hu.bme.wlassits.budget.fragment.WeeklyOutlaysFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.model.Outlay;
import hu.bme.wlassits.budget.model.OutlayType;


public class OutlaysActivity extends BaseActivity {

    FloatingActionButton button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlays);
        ViewPager vpPager = findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlSlider);
        tabLayout.setupWithViewPager(vpPager);
        setContentView(R.layout.activity_outlays);
        button = (FloatingActionButton) findViewById(R.id.fabAddOutlay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getBaseContext());
                dialog.setContentView(R.layout.dialog_outlays);
                dialog.setCancelable(false);
                Button newOutlayBtn = dialog.findViewById(R.id.btnNewOutlay);
                Button cancelOutlayBtn = dialog.findViewById(R.id.btnCancelOutlay);
                final EditText descriptionET = dialog.findViewById(R.id.etOutlayDescription);
                final EditText priceET = dialog.findViewById(R.id.etOutlayValue);
                final Spinner type = dialog.findViewById(R.id.spOutlayType);


                newOutlayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Outlay outlay;
                        String description =  descriptionET.getText().toString();
                        int value = Integer.parseInt(priceET.getText().toString());
                    }
                });



                cancelOutlayBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



            }
        });

    }
    FragmentPagerAdapter adapterViewPager;

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
                    return DailyOutlaysFragment.newInstance("Daily outlays");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return WeeklyOutlaysFragment.newInstance("Weekly outlays");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return MonthlyOutlaysFragment.newInstance("Monthly outlays");
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
