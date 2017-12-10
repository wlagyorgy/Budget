package hu.bme.wlassits.budget.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.presentation.statistics.StatisticsActivity;

import static hu.bme.wlassits.budget.repository.DbHelper.getOutlayDataFromDb;
import static hu.bme.wlassits.budget.repository.DbHelper.initDb;


@SuppressLint("Registered")
public class DashboardActivity extends BaseActivity {

    private static final String TAG = "DashboardActivity";

    Button btnIncome;
    Button btnOutlay;
    Button btnStatistics;
    Button btnExit;
    TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnIncome = findViewById(R.id.btnIncome);
        btnOutlay = findViewById(R.id.btnOutlay);
        btnStatistics = findViewById(R.id.btnStatistics);
        btnExit = findViewById(R.id.btnExit);
        tvName = findViewById(R.id.tvName);
        tvName.setText(Globals.user.getFirst_name());

        setUpButtons(this);

        initDb();
        getOutlayDataFromDb();
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


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitApplication();
            }
        });
    }

    private void exitApplication() {
        finish();
        Log.e(TAG, "exitApplication()");
        LoginManager.getInstance().logOut();
        AccessToken.setCurrentAccessToken(null);
    }
}
