package hu.bme.wlassits.budget.managers;

import java.text.SimpleDateFormat;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class Formatters {
    public static SimpleDateFormat dailyDateFormat = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat weeklyDateFormat = new SimpleDateFormat("EEEE HH:mm");
    public static SimpleDateFormat monthlyDateFormat = new SimpleDateFormat("d EEEE HH:mm");
}
