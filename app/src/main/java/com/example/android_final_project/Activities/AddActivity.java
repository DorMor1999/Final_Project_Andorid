package com.example.android_final_project.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_final_project.Fragments.NavFragment;
import com.example.android_final_project.Interfaces.ReqToDB;
import com.example.android_final_project.Model.BusinessActivity;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.CreateExpense;
import com.example.android_final_project.Utilities.CreateIncome;
import com.example.android_final_project.Utilities.DbOperations;
import com.example.android_final_project.Utilities.DialogUtils;
import com.example.android_final_project.Utilities.Validators;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private FrameLayout add_FRAME_nav;
    private NavFragment navFragment;
    private final String TITLE = "Add";


    private EditText ADDONE_et_description, ADDONE_et_date, ADDONE_et_price;
    private TextView ADDONE_tv_description_alert, ADDONE_tv_date_alert, ADDONE_tv_price_alert;
    private RadioButton ADDONE_rb_income, ADDONE_rb_expense;
    private RadioGroup ADDONE_rg_type;
    private Spinner ADDONE_spinner_type;
    private Button ADDONE_btn_submit;
    private boolean IS_INCOME;


    //utils classes
    private Validators validators;
    private CreateIncome createIncome;
    private CreateExpense createExpense;
    private DialogUtils dialogUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        validators = new Validators();
        createIncome = new CreateIncome();
        createExpense = new CreateExpense();
        dialogUtils = new DialogUtils();

        findViews();
        initViews();
    }

    private void initViews() {
        //nav fragment
        navFragment = new NavFragment();
        navFragment.setTextNavTitle(TITLE);
        getSupportFragmentManager().beginTransaction().add(R.id.add_FRAME_nav, navFragment).commit();

        //date
        ADDONE_et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Set default to income
        ADDONE_rb_income.setChecked(true);
        IS_INCOME = true;
        setSpinnerAdapter(R.array.type_income_array);

        // Set listener for income/expense toggle
        ADDONE_rg_type.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.ADDONE_rb_income) {
                setSpinnerAdapter(R.array.type_income_array);
                IS_INCOME = true;
            } else if (checkedId == R.id.ADDONE_rb_expense) {
                setSpinnerAdapter(R.array.type_expense_array);
                IS_INCOME = false;
            }
        });

        // submit
        ADDONE_btn_submit.setOnClickListener(v -> submitClicked());
    }

    private void submitClicked() {
        boolean formIsOk = true;

        //check description
        if(validators.stringIsEmpty(ADDONE_et_description.getText().toString())){
            formIsOk = false;
            ADDONE_tv_description_alert.setVisibility(View.VISIBLE);
        }
        else {
            ADDONE_tv_description_alert.setVisibility(View.GONE);
        }

        //check price
        String priceText = ADDONE_et_price.getText().toString();
        double price = 0.0;
        try {
            price = Double.parseDouble(priceText);
            if (!validators.doubleIsPositive(price)) {
                ADDONE_tv_price_alert.setVisibility(View.VISIBLE);
                formIsOk = false;
            } else {
                ADDONE_tv_price_alert.setVisibility(View.GONE); // Hide the alert if price is valid
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ADDONE_tv_price_alert.setVisibility(View.VISIBLE);
            formIsOk = false;
        }

        //check date
        if(validators.stringIsEmpty(ADDONE_et_date.getText().toString())){
            formIsOk = false;
            ADDONE_tv_date_alert.setVisibility(View.VISIBLE);
        }
        else {
            ADDONE_tv_date_alert.setVisibility(View.GONE);
        }


        if(formIsOk){
            BusinessActivity newBusinessActivity;
            if (IS_INCOME){
                newBusinessActivity = createIncome.createIncomeObj(ADDONE_et_description.getText().toString(), price, ADDONE_et_date.getText().toString(), ADDONE_spinner_type.getSelectedItem().toString());
            }
            else {
                newBusinessActivity = createExpense.createExpenseObj(ADDONE_et_description.getText().toString(), price, ADDONE_et_date.getText().toString(), ADDONE_spinner_type.getSelectedItem().toString());
            }
            Log.d("new object", newBusinessActivity.toString());
            addToDB(newBusinessActivity);
        }

    }

    private void addToDB(BusinessActivity newBusinessActivity){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DbOperations.getInstance().addNewBusinessActivity(user, new ReqToDB() {
            @Override
            public void onSuccess() {
                // Handle success case
                moveToMenuActivity();
            }

            @Override
            public void onFailure(Exception e) {
                // Handle failure case
                dialogUtils.showDialog(AddActivity.this, "Adding Error", "Something went wrong, try again later.", null);
            }
        }, newBusinessActivity);

    }

    private void setSpinnerAdapter(int arrayResourceId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResourceId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ADDONE_spinner_type.setAdapter(adapter);
    }

    private void findViews() {
        // FrameLayout
        add_FRAME_nav = findViewById(R.id.add_FRAME_nav);

        // Description input
        ADDONE_et_description = findViewById(R.id.ADDONE_et_description);
        ADDONE_tv_description_alert = findViewById(R.id.ADDONE_tv_description_alert);

        // Date input
        ADDONE_et_date = findViewById(R.id.ADDONE_et_date);
        ADDONE_tv_date_alert = findViewById(R.id.ADDONE_tv_date_alert);

        // Price input
        ADDONE_et_price = findViewById(R.id.ADDONE_et_price);
        ADDONE_tv_price_alert = findViewById(R.id.ADDONE_tv_price_alert);

        // Type selection (Income or Expense)
        ADDONE_rg_type = findViewById(R.id.ADDONE_rg_type);
        ADDONE_rb_income = findViewById(R.id.ADDONE_rb_income);
        ADDONE_rb_expense = findViewById(R.id.ADDONE_rb_expense);


        // Type of income or type of expense selection
        ADDONE_spinner_type = findViewById(R.id.ADDONE_spinner_type);


        // Submit button
        ADDONE_btn_submit = findViewById(R.id.ADDONE_btn_submit);
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> ADDONE_et_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                year, month, day);
        datePickerDialog.show();
    }

    private void moveToMenuActivity() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        finish();
    }
}