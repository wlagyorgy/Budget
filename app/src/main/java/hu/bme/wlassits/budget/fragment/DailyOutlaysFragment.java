package hu.bme.wlassits.budget.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class DailyOutlaysFragment extends BaseOutlayFragment {


    public static DailyOutlaysFragment newInstance(String title) {
        DailyOutlaysFragment fragmentFirst = new DailyOutlaysFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, HORIZONTAL);

        rvDaily = view.findViewById(R.id.rvDaily);
        rvDaily.addItemDecoration(itemDecor);
        rvDaily.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAddItem = view.findViewById(R.id.fabAddOutlayDaily);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(view);
            }
        });


        //TODO itt leszűrni a listát, mielőtt átadjuk az adapternek
        ArrayList<Outlay> listData = mockOutlays();

        setData(listData);

        return view;
    }


}