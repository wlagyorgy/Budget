package hu.bme.wlassits.budget.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import hu.bme.wlassits.budget.R;

//TODO Statisztika hozzáadása [https://github.com/PhilJay/MPAndroidChart]
public class StatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
}
