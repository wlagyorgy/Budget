package hu.bme.wlassits.budget.fragment.outlay;

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
import java.util.Calendar;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Managers;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static hu.bme.wlassits.budget.model.Globals.outlays;


public class BaseOutlayFragment extends Fragment {

    Context context;
    RecyclerView rvContent;
    OutlayAdapter outlayAdapter;
    FloatingActionButton fabAddItem;
    Button newOutlayBtn;
    Button cancelOutlayBtn;
    EditText descriptionET;
    EditText valueET;
    Spinner type;

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
        newOutlayBtn = dialog.findViewById(R.id.btnNewOutlay);
        cancelOutlayBtn = dialog.findViewById(R.id.btnCancelOutlay);
        descriptionET = dialog.findViewById(R.id.etOutlayDescription);
        valueET = dialog.findViewById(R.id.etOutlayValue);
        type = dialog.findViewById(R.id.spOutlayType);


        newOutlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOutlay();
                dialog.dismiss();
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

    public void addNewOutlay() {
        Outlay outlay = new Outlay();
        outlay.setDate(Calendar.getInstance().getTime());
        outlay.setDescription(descriptionET.getText().toString());
        outlay.setValue(Integer.parseInt(valueET.getText().toString()));
        String drawableId = type.getSelectedItem().toString().toLowerCase();
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());

        outlay.setImg(getResources().getDrawable(imgId));
        outlays.add(outlay);

        //TODO SaveData to preferences
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleOutlayItem(getAdapterPosition());
                    }
                });
            }
        }
    }

    //TODO Ehhez létrehozni layoutot illetve betölteni bele az adatokat, hogy lehessen baszogatni.
    public void handleOutlayItem(int pos){

    }


    public void setData(ArrayList<Outlay> listData) {
        outlayAdapter = new OutlayAdapter(listData, context);
        rvContent.setAdapter(outlayAdapter);
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

        setData(Globals.outlays);

        return view;
    }


}
