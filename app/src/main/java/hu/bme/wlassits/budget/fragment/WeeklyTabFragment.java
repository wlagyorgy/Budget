package hu.bme.wlassits.budget.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.bme.wlassits.budget.R;

public class WeeklyTabFragment extends Fragment {

    private String title;
    private int page;


    public static WeeklyTabFragment newInstance(int page, String title) {
        WeeklyTabFragment weeklyTabFragment = new WeeklyTabFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        weeklyTabFragment.setArguments(args);
        return weeklyTabFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        title = getArguments().getString("title");
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        return view;
    }
}

