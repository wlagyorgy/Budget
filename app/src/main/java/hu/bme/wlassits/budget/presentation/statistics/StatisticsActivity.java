package hu.bme.wlassits.budget.presentation.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.presentation.BaseActivity;

import static hu.bme.wlassits.budget.model.Globals.incomes;
import static hu.bme.wlassits.budget.model.Globals.outlays;


public class StatisticsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Button outlaysSumBtn = findViewById(R.id.btnOutlaysSum);
        Button incomesSumBtn = findViewById(R.id.btnIncomesSum);
        long outlaysSum = 0;
        long incomesSum = 0;
        int i;
        for (i = 0; i < outlays.size(); i++) {
            outlaysSum += outlays.get(i).getValue();
        }

        for (i = 0; i < incomes.size(); i++) {
            incomesSum += incomes.get(i).getValue();
        }


        outlaysSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), OutlaysStatisticsActivity.class);
                startActivity(intent);
            }
        });

        incomesSumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getBaseContext(), IncomeStatisticsActivity.class);
                    startActivity(intent);
                }

            });



        //TODO Gyuri 7. Egyszerű statisztikák hozzáadása, pl összes bevétel és kiadás az első login óta
    }


    //TODO Gyuri 8. > Diagramok hozzáadása [https://github.com/PhilJay/MPAndroidChart]
    //TODO Gyuri 8. > Layoutot se felejtsd el kiegészíteni
}
