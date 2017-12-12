package hu.bme.wlassits.budget.fragment.income;

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
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.event.DatabaseChangedEvent;
import hu.bme.wlassits.budget.repository.DbHelper;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class BaseIncomeFragment extends Fragment {

    Context context;
    RecyclerView rvContent;
    IncomeAdapter incomeAdapter;
    FloatingActionButton fabAddItem;
    Button btnAdd;
    Button btnCancel;
    Button btnDelete;
    EditText etDescription;
    EditText etValue;
    Spinner spType;
    TextView tvAddOrModify;

    private static String TAG = "BaseIncomeFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        EventBus.getDefault().register(this);
    }


    @SuppressLint("SetTextI18n")
    public void createDialog(View view, final Income i, final int pos) {

        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_incomes);
        dialog.setCancelable(false);
        //Buttons
        btnAdd = dialog.findViewById(R.id.btnNewIncome);
        btnCancel = dialog.findViewById(R.id.btnCancelIncome);
        btnDelete = dialog.findViewById(R.id.btnDelete);
        //Texts
        etDescription = dialog.findViewById(R.id.etIncomeDescription);
        etValue = dialog.findViewById(R.id.etIncomeValue);
        tvAddOrModify = dialog.findViewById(R.id.tvAddIncome);
        //Spinner
        spType = dialog.findViewById(R.id.spIncomeType);



        if (i == null) {
            btnAdd.setText(R.string.add_income);
            //A Cancel legyen látható, a Delete pedig ne
            btnCancel.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
            tvAddOrModify.setText(R.string.add_new_income_dialog_header);
        }
        if (i != null) {
            btnAdd.setText(R.string.modify);
            //A Delete legyen látható, a Cancel pedig ne
            btnCancel.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
            tvAddOrModify.setText(R.string.modify_income_dialog_header);
            etDescription.setText(i.getDescription());
            etValue.setText(String.valueOf(i.getValue()));

            String typeS = i.getType();
            for (int k = 0; k < spType.getCount(); k++) {
                if (spType.getItemAtPosition(k).toString().equals(typeS)) {
                    spType.setSelection(k);
                }
            }
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(isInputInvalid(etDescription.getText().toString()) && !(isInputInvalid(etValue.getText().toString())))) {

                    if (i == null) {
                        addNewIncome();
                    } else {
                        modifyIncome(i);
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
                deleteIncome(i);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void deleteIncome(Income i) {
        DbHelper.removeItemFromDatabase(i);
    }

    public boolean isInputInvalid(String s) {
        return s.trim().isEmpty();

    }

    public void addNewIncome() {
        Income income = new Income();
        income.setDate(Calendar.getInstance().getTime());
        income.setType(spType.getSelectedItem().toString());
        income.setDescription(etDescription.getText().toString());
        income.setValue(Integer.parseInt(etValue.getText().toString()));
        String drawableId = spType.getSelectedItem().toString().toLowerCase();
        income.setId(DbHelper.database.child("dbEntities").push().getKey());
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
        income.setImg(getResources().getDrawable(imgId));

        DbHelper.addItemToDatabase(income);
    }


    public void modifyIncome(Income i) {
        Income income = new Income();

        income.setDate(i.getDate());
        income.setId(i.getId());
        income.setDescription(etDescription.getText().toString());
        income.setValue(Integer.parseInt(etValue.getText().toString()));
        income.setType(spType.getSelectedItem().toString());
        String drawableId = spType.getSelectedItem().toString().toLowerCase();
        int imgId = getResources().getIdentifier(drawableId, "drawable", context.getPackageName());
        income.setImg(getResources().getDrawable(imgId));

        DbHelper.updateItemInDatabase(income);
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

            int imgId = getResources().getIdentifier(item.getType().toLowerCase(), "drawable", context.getPackageName());
            holder.ivIcon.setImageDrawable(getResources().getDrawable(imgId));
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


    public void handleIncomeItem(int pos, View v) {
        createDialog(v, Globals.incomes.get(pos), pos);
    }


    public void setData(ArrayList<Income> listData) {
        incomeAdapter = new IncomeAdapter(listData, context);
        rvContent.setAdapter(incomeAdapter);
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
                createDialog(view, null, 0);
            }
        });

        setData(Globals.incomes);

        return view;
    }

    public void onEventMainThread(DatabaseChangedEvent event){
        incomeAdapter = new IncomeAdapter(Globals.incomes,context);
        rvContent.setAdapter(incomeAdapter);
        Log.e(TAG,"DatabaseChangedEvent");
    }
}