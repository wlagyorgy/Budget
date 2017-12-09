package hu.bme.wlassits.budget.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import hu.bme.wlassits.budget.R;

@SuppressLint("Registered")
public class DashboardActivity extends BaseActivity {

    Button btnIncome;
    Button btnOutlay;
    Button btnStatistics;
    Button btnExit;
    Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnIncome = findViewById(R.id.btnIncome);
        btnOutlay = findViewById(R.id.btnOutlay);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnExit = findViewById(R.id.btnExit);

        setUpButtons(this);
    }


    public void setUpButtons(final Context context) {

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IncomesActivity.class);
                startActivity(intent);
            }
        });


        btnOutlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OutlaysActivity.class);
                startActivity(intent);
            }
        });


        btnStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StatisticsActivity.class);
                startActivity(intent);
            }
        });


        btnOutlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApplication();
            }
        });


    }

    private void exitApplication() {


    }
}
