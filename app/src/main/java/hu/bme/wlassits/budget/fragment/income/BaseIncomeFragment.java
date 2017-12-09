package hu.bme.wlassits.budget.fragment.income;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Formatters;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

/**
 * Created by Adam Varga on 12/9/2017.
 */

public class BaseIncomeFragment extends Fragment {

    Context context;
    RecyclerView rvContent;
    IncomeAdapter incomeAdapter;
    FloatingActionButton fabAddItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    //TODO Gyuri alert dialog.
    public void createDialog(View view) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_outlays);
        dialog.setCancelable(false);

        Button newOutlayBtn = dialog.findViewById(R.id.btnNewOutlay);
        Button cancelOutlayBtn = dialog.findViewById(R.id.btnCancelOutlay);
        final EditText descriptionET = dialog.findViewById(R.id.etOutlayDescription);
        final EditText priceET = dialog.findViewById(R.id.etOutlayValue);
        final Spinner type = dialog.findViewById(R.id.spOutlayType);


        newOutlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Outlay outlay;
                String description = descriptionET.getText().toString();
                int value = Integer.parseInt(priceET.getText().toString());
            }
        });


        cancelOutlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.OutlayHolder> {

        private ArrayList<Income> listData;
        private LayoutInflater inflater;

        IncomeAdapter(ArrayList<Income> listData, Context c) {

            this.inflater = LayoutInflater.from(c);
            this.listData = listData;
        }

        @Override
        public OutlayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.component_outlay, parent, false);

            return new OutlayHolder(view);
        }

        @Override
        public void onBindViewHolder(OutlayHolder holder, int position) {
            Income item = listData.get(position);

            holder.tvDescription.setText(item.getDescription());
            holder.tvDate.setText(Formatters.dailyDateFormat.format(item.getDate()));
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


    public void setData(ArrayList<Income> listData) {
        incomeAdapter = new IncomeAdapter(listData, context);
        rvContent.setAdapter(incomeAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outlays, container, false);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, HORIZONTAL);

        rvContent = view.findViewById(R.id.rvContent);
        rvContent.addItemDecoration(itemDecor);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAddItem = view.findViewById(R.id.fabAddOutlayItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(view);
            }
        });


        setData(Globals.incomes);

        return view;
    }


}
