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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import hu.bme.wlassits.budget.R;
import hu.bme.wlassits.budget.managers.Formatters;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.dbmodels.DbEntity;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

/**
 * Created by Adam Varga on 12/9/2017.
 */


//TODO Gyuri 6. >  áthozni a BaseIncomeFragment-ből mindent, és Incommal megcsinálni

public class BaseIncomeFragment extends Fragment {

    Context context;
    RecyclerView rvContent;
    IncomeAdapter IncomeAdapter;
    FloatingActionButton fabAddItem;
    Button newIncomeBtn;
    Button cancelIncomeBtn;
    EditText descriptionET;
    EditText valueET;
    Spinner type;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    //TODO Gyuri 2. >  createDialog(View view) >>  csekkolni, hogy működik-é illetve mezők validálása (pl. nem üres meg ami még eszedbe jut)
    public void createDialog(View view, Income o) {

        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_incomes);
        dialog.setCancelable(false);

        newIncomeBtn = dialog.findViewById(R.id.btnNewIncome);
        cancelIncomeBtn = dialog.findViewById(R.id.btnCancelIncome);
        descriptionET = dialog.findViewById(R.id.etIncomeDescription);
        valueET = dialog.findViewById(R.id.etIncomeValue);
        type = dialog.findViewById(R.id.spIncomeType);
        TextView addOrModify = dialog.findViewById(R.id.tvAddIncome);
      

        if(o == null) {
            newIncomeBtn.setText("Add");
            addOrModify.setText("Add new Income");
        }
        if (o != null) {
            newIncomeBtn.setText("Modify");
            addOrModify.setText("Modify Income");
            descriptionET.setText(o.getDescription());
            valueET.setText(String.valueOf(o.getValue()));
            String typeS = o.getType();
            for (int i = 0; i < type.getCount(); i++) {
                if (type.getItemAtPosition(i).toString().equals(typeS)) {
                    type.setSelection(i);
                }
            }
        }


        newIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isInputInvalid(descriptionET.getText().toString())&& !(isInputInvalid(valueET.getText().toString())))) {
                    addNewIncome();
                    dialog.dismiss();
                } else {
                    if (isInputInvalid(descriptionET.getText().toString())) {
                        descriptionET.setHintTextColor(getResources().getColor(R.color.red));
                        descriptionET.setHint("This field can't be empty");
                    }
                    if (isInputInvalid(valueET.getText().toString())) {
                        valueET.setHintTextColor(getResources().getColor(R.color.red));
                        valueET.setHint("This field can't be empty!");
                    }
                }
            }
        });


        cancelIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public boolean isInputInvalid(String s){
        return s.trim().isEmpty();

    }

    public void addNewIncome() {
        Income income = new Income();
        income.setDate(Calendar.getInstance().getTime());
        income.setDescription(descriptionET.getText().toString());
        income.setValue(Integer.parseInt(valueET.getText().toString()));
        String drawableId = type.getSelectedItem().toString().toLowerCase();
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
        income.setImg(getResources().getDrawable(imgId));

        Globals.incomes.add(income);
        IncomeAdapter.notifyDataSetChanged();

        SaveIncomeToDatabase(income);
    }


    public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeHolder> {

        private ArrayList<Income> listData;
        private LayoutInflater inflater;

        IncomeAdapter(ArrayList<Income> listData, Context c) {

            this.inflater = LayoutInflater.from(c);
            this.listData = listData;
        }

        @Override
        public IncomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = inflater.inflate(R.layout.component_income, parent, false);

            return new IncomeHolder(view);
        }

        @Override
        public void onBindViewHolder(IncomeHolder holder, int position) {
            Income item = listData.get(position);

            holder.tvDescription.setText(item.getDescription());
            holder.tvDate.setText(Formatters.monthlyDateFormat.format(item.getDate()));
            holder.tvValue.setText(String.valueOf(item.getValue()) + " Ft");

            holder.ivIcon.setImageDrawable(item.getImg());
            holder.rlIncomeComponent.setBackgroundColor(getBackgroundColorByValue(String.valueOf(item.getValue())));
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

        class IncomeHolder extends RecyclerView.ViewHolder {

            private ImageView ivIcon;
            private TextView tvDescription;
            private TextView tvDate;
            private TextView tvValue;
            private RelativeLayout rlIncomeComponent;

            IncomeHolder(View itemView) {
                super(itemView);
                ivIcon = itemView.findViewById(R.id.ivIcon);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvValue = itemView.findViewById(R.id.tvValue);
                rlIncomeComponent = itemView.findViewById(R.id.rlIncomeComponent);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleIncomeItem(getAdapterPosition(), v);
                    }
                });
            }
        }
    }

    //TODO Gyuri 4. > Layout létrehozása, amibe betöltjük a kiválasztott elem adatait, EditTextekbe, hogy majd lehessen szerkeszteni.
    //
    public void handleIncomeItem(int pos, View v) {
        createDialog(v, Globals.incomes.get(pos));
        Log.e("Selected item:", Globals.incomes.get(pos).toString());
    }


    public void setData(ArrayList<Income> listData) {
        IncomeAdapter = new IncomeAdapter(listData, context);
        rvContent.setAdapter(IncomeAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomes, container, false);

        DividerItemDecoration itemDecor = new DividerItemDecoration(context, HORIZONTAL);

        rvContent = view.findViewById(R.id.rvContent);
        rvContent.addItemDecoration(itemDecor);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAddItem = view.findViewById(R.id.fabAddIncomeItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(view,null);
            }
        });

        setData(Globals.incomes);

        return view;
    }

    public void saveIncomeToDatabase(Income o) {

        Calendar cal = Calendar.getInstance();
        DbEntity dbEntity = new DbEntity();

        dbEntity.setDate(cal.getTime());
        dbEntity.setFbId(Globals.user.getFacebookIdentifier());
        dbEntity.setDescription(o.getDescription());
        dbEntity.setId(database.child("dbEntities").push().getKey());
        dbEntity.setValue(String.valueOf(o.getValue()));
        dbEntity.setType("teszt");
        database.child("dbEntities").child(dbEntity.getId()).setValue(dbEntity);

    }


}