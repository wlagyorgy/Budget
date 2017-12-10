package hu.bme.wlassits.budget.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import hu.bme.wlassits.budget.R;

import static hu.bme.wlassits.budget.model.Globals.incomes;
import static hu.bme.wlassits.budget.model.Globals.outlays;


public class StatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        long outlaysSum = 0;
        long incomesSum = 0;
        int i;
        for(i = 0; i< outlays.size(); i++){
            outlaysSum += outlays.get(i).getValue();
        }

        for(i = 0; i< incomes.size(); i++){
            incomesSum += incomes.get(i).getValue();
        }

        TextView tvOut = findViewById(R.id.tvOutlayStats);
        TextView tvIn = findViewById(R.id.tvIncomeStats);
        tvOut.setText(String.valueOf(outlaysSum));
        tvIn.setText(String.valueOf(incomesSum));

        //TODO Gyuri 7. Egyszerű statisztikák hozzáadása, pl összes bevétel és kiadás az első login óta
    }


    //TODO Gyuri 8. > Diagramok hozzáadása [https://github.com/PhilJay/MPAndroidChart]
    //TODO Gyuri 8. > Layoutot se felejtsd el kiegészíteni
}
