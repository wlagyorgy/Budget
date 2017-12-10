package hu.bme.wlassits.budget.model;


import java.util.ArrayList;
import java.util.Arrays;

public class Globals {
    public static User user;
    public static ArrayList<Outlay> outlays = new ArrayList<>();
    public static ArrayList<Income> incomes = new ArrayList<>();
    public ArrayList<String> outlay_types = new ArrayList<String>(Arrays.asList("Clothes", "Food", "Travel", "House", "Car", "Freetime"));
    public ArrayList<String> income_types = new ArrayList<String>(Arrays.asList("Creditcard", "Cash", "Gift"));
}
