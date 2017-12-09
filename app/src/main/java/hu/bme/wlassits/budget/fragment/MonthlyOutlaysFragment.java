package hu.bme.wlassits.budget.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Managers;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class MonthlyOutlaysFragment extends BaseOutlayFragment {


    String title;
    RecyclerView rvMonthly;
    OutlayAdapter outlayAdapter;
    FloatingActionButton fabAddItem;

    public static MonthlyOutlaysFragment newInstance(String title) {
        MonthlyOutlaysFragment fragmentFirst = new MonthlyOutlaysFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        fabAddItem = view.findViewById(R.id.fabAddOutlayMonthly);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(view);
            }
        });

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, HORIZONTAL);

        rvMonthly = view.findViewById(R.id.rvMonthly);
        rvMonthly.addItemDecoration(itemDecor);
        rvMonthly.setLayoutManager(new LinearLayoutManager(context));

        //TODO itt leszűrni a listát, mielőtt átadjuk az adapternek
        ArrayList<Outlay> listData = mockOutlays();

        outlayAdapter = new OutlayAdapter(listData, context);
        rvMonthly.setAdapter(outlayAdapter);

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




}