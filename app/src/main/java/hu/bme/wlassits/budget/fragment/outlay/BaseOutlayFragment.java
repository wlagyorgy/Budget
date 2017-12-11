package hu.bme.wlassits.budget.fragment.outlay;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import de.greenrobot.event.EventBus;
import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Formatters;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Outlay;
import hu.bme.wlassits.budget.model.event.DatabaseChangedEvent;
import hu.bme.wlassits.budget.repository.DbHelper;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class BaseOutlayFragment extends Fragment {

    Context context;
    RecyclerView rvContent;
    OutlayAdapter outlayAdapter;
    FloatingActionButton fabAddItem;
    Button btnAdd;
    Button btnCancel;
    Button btnDelete;
    EditText etDescription;
    EditText etValue;
    Spinner spType;
    TextView tvAddOrModify;

    private static String TAG = "BaseOutlayFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        EventBus.getDefault().register(this);
    }


    @SuppressLint("SetTextI18n")
    public void createDialog(View view, final Outlay o, final int pos) {

        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_outlays);
        dialog.setCancelable(false);

        //Buttons
        btnAdd = dialog.findViewById(R.id.btnNewOutlay);
        btnCancel = dialog.findViewById(R.id.btnCancelOutlay);
        btnDelete = dialog.findViewById(R.id.btnDelete);
        //Texts
        etDescription = dialog.findViewById(R.id.etOutlayDescription);
        etValue = dialog.findViewById(R.id.etOutlayValue);
        tvAddOrModify = dialog.findViewById(R.id.tvAddOutlay);
        //Spinner to choose type
        spType = dialog.findViewById(R.id.spOutlayType);


        if (o == null) {
            btnAdd.setText(R.string.add_outlay);
            //A Cancel legyen látható, a Delete pedig ne
            btnCancel.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
            tvAddOrModify.setText(R.string.add_new_outlay_dialog_header);
        }
        if (o != null) {

            btnAdd.setText(R.string.modify);
            //A Delete legyen látható, a Cancel pedig ne
            btnCancel.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            tvAddOrModify.setText(R.string.modify_outlay_dialog_header);
            etDescription.setText(o.getDescription());
            etValue.setText(String.valueOf(o.getValue()));

            String typeS = o.getType();
            for (int i = 0; i < spType.getCount(); i++) {
                if (spType.getItemAtPosition(i).toString().equals(typeS)) {
                    spType.setSelection(i);
                }
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isInputInvalid(etDescription.getText().toString()) && !(isInputInvalid(etValue.getText().toString())))) {

                    if (o == null) {
                        addNewOutlay();
                    } else {
                        modifyOutlay(o);
                    }

                    dialog.dismiss();
                } else {
                    if (isInputInvalid(etDescription.getText().toString())) {
                        etDescription.setHintTextColor(getResources().getColor(R.color.red));
                        etDescription.setHint("This field can't be empty");
                    }
                    if (isInputInvalid(etValue.getText().toString())) {
                        etValue.setHintTextColor(getResources().getColor(R.color.red));
                        etValue.setHint("This field can't be empty!");
                    }
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOutlay(o);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void deleteOutlay(Outlay o) {
        DbHelper.removeItemFromDatabase(o);
    }


    public boolean isInputInvalid(String s) {
        return s.trim().isEmpty();
    }

    public void addNewOutlay() {
        Outlay outlay = new Outlay();
        outlay.setDate(Calendar.getInstance().getTime());
        outlay.setDescription(etDescription.getText().toString());
        outlay.setValue(Integer.parseInt(etValue.getText().toString()));
        outlay.setType(spType.getSelectedItem().toString());
        String drawableId = spType.getSelectedItem().toString().toLowerCase();
        outlay.setId(DbHelper.database.child("dbEntities").push().getKey());
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
        outlay.setImg(getResources().getDrawable(imgId));


        DbHelper.addItemToDatabase(outlay);
    }

    public void modifyOutlay(Outlay o) {
        Outlay outlay = new Outlay();

        outlay.setDate(o.getDate());
        outlay.setId(o.getId());
        outlay.setDescription(etDescription.getText().toString());
        outlay.setValue(Integer.parseInt(etValue.getText().toString()));
        outlay.setType(spType.getSelectedItem().toString());
        String drawableId = spType.getSelectedItem().toString().toLowerCase();
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
        outlay.setImg(getResources().getDrawable(imgId));

        DbHelper.updateItemInDatabase(outlay);
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

            int imgId = getResources().getIdentifier(item.getType().toLowerCase(), "drawable", context.getPackageName());
            holder.ivIcon.setImageDrawable(getResources().getDrawable(imgId));
            holder.rlOutlayComponent.setBackgroundColor(getBackgroundColorByValue(String.valueOf(item.getValue())));
        }

        private int getBackgroundColorByValue(String s) {
            int value = Integer.parseInt(s);

            if (value > 0 && value <= 2000) {
                return getResources().getColor(R.color.verylow);
            } else if (value > 2000 && value <= 5000) {
                return getResources().getColor(R.color.low);
            } else if (value > 5000 && value <= 20000) {
                return getResources().getColor(R.color.medium);
            } else return getResources().getColor(R.color.high);

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
                        handleOutlayItem(getAdapterPosition(), v);
                    }
                });
            }
        }
    }

    public void handleOutlayItem(int pos, View v) {
        createDialog(v, Globals.outlays.get(pos), pos);
    }


    public void setData(ArrayList<Outlay> listData) {
        outlayAdapter = new OutlayAdapter(listData, context);
        rvContent.setAdapter(outlayAdapter);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outlays, container, false);

        //Egyes komponensek közötti elválasztóvonal a recycleview-nál
        DividerItemDecoration itemDecor = new DividerItemDecoration(context, HORIZONTAL);

        rvContent = view.findViewById(R.id.rvContent);
        rvContent.addItemDecoration(itemDecor);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabAddItem = view.findViewById(R.id.fabAddOutlayItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(view, null, 0);
            }
        });

        setData(Globals.outlays);

        return view;
    }

    // This method will be called when a DatabaseChangedEvent is posted
    public void onEventMainThread(DatabaseChangedEvent event){
        outlayAdapter = new OutlayAdapter(Globals.outlays,context);
        rvContent.setAdapter(outlayAdapter);
        Log.e(TAG,"DatabaseChangedEvent");
    }


}
