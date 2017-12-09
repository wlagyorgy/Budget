package hu.bme.wlassits.budget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Managers;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class WeeklyOutlaysFragment extends Fragment {


    String title;
    RecyclerView rvWeekly;
    OutlayAdapter outlayAdapter;
    Context context;

    public static WeeklyOutlaysFragment newInstance(String title) {
        WeeklyOutlaysFragment fragmentFirst = new WeeklyOutlaysFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        context = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);

        rvWeekly = view.findViewById(R.id.rvWeekly);
        rvWeekly.addItemDecoration(itemDecor);
        rvWeekly.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO itt leszűrni a listát, mielőtt átadjuk az adapternek
        ArrayList<Outlay> listData = mockOutlays();

        outlayAdapter = new OutlayAdapter(listData, getContext());
        rvWeekly.setAdapter(outlayAdapter);

        return view;
    }


    public class OutlayAdapter extends RecyclerView.Adapter<OutlayAdapter.OutlayHolder> {

        private ArrayList<Outlay> listData;
        private LayoutInflater inflater;

        OutlayAdapter(ArrayList<Outlay> listData, Context c) {
            this.inflater = LayoutInflater.from(c);
            this.listData = listData;
        }

        @Override
        public OutlayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.component_outlay, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Ide bekötni, hogy mi történjen, ha rmegyünk egy elemre
                    Toast.makeText(context, "Valami történik majd ezzel", Toast.LENGTH_LONG).show();
                }
            });
            return new OutlayHolder(view);
        }

        @Override
        public void onBindViewHolder(OutlayHolder holder, int position) {
            Outlay item = listData.get(position);

            holder.tvDescription.setText(item.getDescription());
            holder.tvDate.setText(Managers.dailyDateFormat.format(item.getDate()));
            holder.tvValue.setText(String.valueOf(item.getValue()) + " Ft");
            holder.ivIcon.setImageDrawable(item.getImg());
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }

        class OutlayHolder extends RecyclerView.ViewHolder {

            private ImageView ivIcon;
            private TextView tvDescription;
            private TextView tvDate;
            private TextView tvValue;
            private View container;

            OutlayHolder(View itemView) {
                super(itemView);

                ivIcon = itemView.findViewById(R.id.ivIcon);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvValue = itemView.findViewById(R.id.tvValue);
                //   container = itemView.findViewById(R.id.notification_item_root);
            }
        }
    }


    public ArrayList<Outlay> mockOutlays() {

        ArrayList<Outlay> outlays = new ArrayList<>();
        Outlay outlay;

        for (int i = 0; i < 10; i++) {
            outlay = new Outlay();

            outlay.setDate(Calendar.getInstance().getTime());
            outlay.setDescription(i+" kiló alma");
            outlay.setValue(i*1000);
            outlay.setImg(getResources().getDrawable(R.drawable.app_logo));
            outlays.add(outlay);
        }
        return outlays;
    }





}