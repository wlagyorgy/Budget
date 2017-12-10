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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Formatters;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Outlay;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static hu.bme.wlassits.budget.model.Globals.outlays;

//TODO Gyuri 5. > Tesztelni mindent, hogy működik-e
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


    //TODO Gyuri 2. >  createDialog(View view) >>  csekkolni, hogy működik-é illetve mezők validálása (pl. nem üres meg ami még eszedbe jut)
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

        //TODO Zsömi > SaveData to DB
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
            holder.tvDate.setText(Formatters.monthlyDateFormat.format(item.getDate()));
            holder.tvValue.setText(String.valueOf(item.getValue()) + " Ft");
            holder.ivIcon.setImageDrawable(item.getImg());
            holder.rlOutlayComponent.setBackgroundColor(getBackgroundColorByValue(String.valueOf(item.getValue())));
        }

        private int getBackgroundColorByValue(String s) {
            //TODO Gyuri 3. > Színezés a költségek alapján, valamilyen határok
            //TODO Gyuri 3. > pl apró 0-2000Ft, kis kiadás = 2000ft-5000Ft, közepes 5000Ft-20000Ft, nagy 20000Ft fölött
            return R.color.accent;
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
            private RelativeLayout rlOutlayComponent;

            OutlayHolder(View itemView) {
                super(itemView);
                ivIcon = itemView.findViewById(R.id.ivIcon);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvValue = itemView.findViewById(R.id.tvValue);
                rlOutlayComponent = itemView.findViewById(R.id.rlOutlayComponent);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleOutlayItem(getAdapterPosition());
                    }
                });
            }
        }
    }

    //TODO Gyuri 4. > Layout létrehozása, amibe betöltjük a kiválasztott elem adatait, EditTextekbe, hogy majd lehessen szerkeszteni.
    //
    public void handleOutlayItem(int pos) {
        Log.e("Selected item:", outlays.get(pos).toString());
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
