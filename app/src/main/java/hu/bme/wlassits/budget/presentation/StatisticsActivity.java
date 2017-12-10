package hu.bme.wlassits.budget.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import hu.bme.wlassits.budget.R;


public class StatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //TODO Gyuri 7. Egyszerű statisztikák hozzáadása, pl összes bevétel és kiadás az első login óta
    }


    //TODO Gyuri 8. > Diagramok hozzáadása [https://github.com/PhilJay/MPAndroidChart]
    //TODO Gyuri 8. > Layoutot se felejtsd el kiegészíteni
}
