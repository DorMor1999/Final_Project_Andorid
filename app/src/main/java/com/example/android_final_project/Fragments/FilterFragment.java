package com.example.android_final_project.Fragments;

import static com.example.android_final_project.Enums.SortOptions.stringToSortOptions;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android_final_project.Enums.BusinessActivityType;

import com.example.android_final_project.Enums.SortOptions;
import com.example.android_final_project.Interfaces.CloseFilterCallback;
import com.example.android_final_project.Interfaces.DisplayFilterCallback;
import com.example.android_final_project.R;
import com.example.android_final_project.Utilities.Validators;

import java.util.Calendar;

public class FilterFragment extends Fragment {

    private CheckBox filter_frame_date_checkbox;
    private LinearLayout filter_frame_date_container;
    private TextView filter_frame_date_picker_from_label;
    private EditText filter_frame_date_picker_from;
    private TextView filter_frame_date_picker_to_label;
    private EditText filter_frame_date_picker_to;
    private TextView filter_frame_date_error;
    private CheckBox filter_frame_price_checkbox;
    private TextView filter_frame_price_range_label;
    private LinearLayout filter_frame_price_range_layout;
    private EditText filter_frame_price_min;
    private EditText filter_frame_price_max;
    private TextView filter_frame_price_error;
    private TextView filter_frame_type_label;
    private RadioGroup filter_frame_type_group;
    private RadioButton filter_frame_rb_expense;
    private RadioButton filter_frame_rb_income;
    private RadioButton filter_frame_rb_both;
    private TextView filter_frame_sort_label;
    private Spinner filter_frame_sort_spinner;
    private Button filter_frame_display_button;
    private Button filter_frame_close_button;

    private BusinessActivityType type = BusinessActivityType.BOTH; // enum options EXPENSE, INCOME, BOTH
    private SortOptions sortOption = SortOptions.DONT_SORT;
    private CloseFilterCallback listenerClose;
    private DisplayFilterCallback listenerDisplay;
    private Validators validators;

    public void setCloseFilterCallback(CloseFilterCallback listenerClose) {
        this.listenerClose = listenerClose;
    }

    public void setDisplayFilterCallback(DisplayFilterCallback listenerDisplay){
        this.listenerDisplay = listenerDisplay;
    }



    public FilterFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        validators = new Validators();

        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        // not display date staff at first
        filter_frame_date_checkbox.setChecked(false);
        filter_frame_date_container.setVisibility(View.GONE);
        filter_frame_date_error.setVisibility(View.GONE);
        setupListenersDate();

        // not display price staff at first
        filter_frame_price_checkbox.setChecked(false);
        filter_frame_price_range_label.setVisibility(View.GONE);
        filter_frame_price_range_layout.setVisibility(View.GONE);
        filter_frame_price_error.setVisibility(View.GONE);
        setupListenerCheckBoxPrice();

        //radio button staff start with BOTH
        filter_frame_rb_both.setChecked(true);
        setupListenerRadioGroup();

        //spinner staff start with DONT_SORT
        setSpinnerAdapter(filter_frame_sort_spinner, R.array.sort_options);
        setupSpinnerListener(filter_frame_sort_spinner);

        //adding the buttons
        filter_frame_close_button.setOnClickListener(v -> clickClose());
        filter_frame_display_button.setOnClickListener(v -> displayClick());
    }

    private void displayClick() {
        boolean formIsOk = true;
        if (filter_frame_date_checkbox.isChecked()){
            if (!validators.dateRangeIsOk(filter_frame_date_picker_from.getText().toString(), filter_frame_date_picker_to.getText().toString())){
                formIsOk = false;
                filter_frame_date_error.setVisibility(View.VISIBLE);
            }
            else {
                filter_frame_date_error.setVisibility(View.GONE);
            }
        }
        double min = 0D;
        double max = 0D;
        if (filter_frame_price_checkbox.isChecked()){
            try {
                // Retrieve text values from EditText
                String minText = filter_frame_price_min.getText().toString();
                String maxText = filter_frame_price_max.getText().toString();

                // Convert text to double
                min = Double.parseDouble(minText);
                max = Double.parseDouble(maxText);

                // Now you can use min and max as double values
                if (!validators.priceRangeIsOk(min, max)) {
                    // Handle invalid price range
                    formIsOk = false;
                    filter_frame_price_error.setVisibility(View.VISIBLE);
                }
                else {
                    filter_frame_price_error.setVisibility(View.GONE);
                }

            } catch (NumberFormatException e) {
                // Handle the case where the text is not a valid number
                formIsOk = false;
                filter_frame_price_error.setVisibility(View.VISIBLE);
            }
        }

        if (formIsOk && listenerDisplay != null){
            Log.d("sort option", sortOption.toString());
            listenerDisplay.onButtonClickedDisplay(filter_frame_date_checkbox.isChecked(), filter_frame_date_picker_from.getText().toString(), filter_frame_date_picker_to.getText().toString(), filter_frame_price_checkbox.isChecked(), min, max, type, sortOption );
        }
    }

    private void clickClose() {
        if (listenerClose != null) {
            listenerClose.onButtonClickedClose();
        }
    }


    public void setupSpinnerListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item as a string
                String selectedOption = (String) parent.getItemAtPosition(position);

                // Convert the selected string to the SortOptions enum
                sortOption = stringToSortOptions(selectedOption);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected if needed
            }
        });
    }

    private void setupListenerRadioGroup() {
        filter_frame_type_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.filter_frame_rb_expense) {
                type = BusinessActivityType.EXPENSE;
            } else if (checkedId == R.id.filter_frame_rb_income) {
                type = BusinessActivityType.INCOME;
            } else if (checkedId == R.id.filter_frame_rb_both) {
                type = BusinessActivityType.BOTH;
            }
        });

    }

    private void setupListenersDate() {
        filter_frame_date_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle the check state change
                if (isChecked) {
                    // Actions when the checkbox is checked
                    filter_frame_date_container.setVisibility(View.VISIBLE);
                    filter_frame_date_error.setVisibility(View.GONE);
                } else {
                    // Actions when the checkbox is unchecked
                    filter_frame_date_container.setVisibility(View.GONE);
                    filter_frame_date_error.setVisibility(View.GONE);
                }
            }
        });

        filter_frame_date_picker_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(filter_frame_date_picker_from);
            }
        });

        filter_frame_date_picker_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(filter_frame_date_picker_to);
            }
        });
    }

    private void setupListenerCheckBoxPrice() {
        filter_frame_price_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle the check state change
                if (isChecked) {
                    // Actions when the checkbox is checked
                    filter_frame_price_range_label.setVisibility(View.VISIBLE);
                    filter_frame_price_range_layout.setVisibility(View.VISIBLE);
                    filter_frame_price_error.setVisibility(View.GONE);
                } else {
                    // Actions when the checkbox is unchecked
                    filter_frame_price_range_label.setVisibility(View.GONE);
                    filter_frame_price_range_layout.setVisibility(View.GONE);
                    filter_frame_price_error.setVisibility(View.GONE);
                }
            }
        });
    }

    private void findViews(View v) {
        filter_frame_date_checkbox = v.findViewById(R.id.filter_frame_date_checkbox);
        filter_frame_date_container = v.findViewById(R.id.filter_frame_date_container);
        filter_frame_date_picker_from_label = v.findViewById(R.id.filter_frame_date_picker_from_label);
        filter_frame_date_picker_from = v.findViewById(R.id.filter_frame_date_picker_from);
        filter_frame_date_picker_to_label = v.findViewById(R.id.filter_frame_date_picker_to_label);
        filter_frame_date_picker_to = v.findViewById(R.id.filter_frame_date_picker_to);
        filter_frame_date_error = v.findViewById(R.id.filter_frame_date_error);
        filter_frame_price_checkbox = v.findViewById(R.id.filter_frame_price_checkbox);
        filter_frame_price_range_label = v.findViewById(R.id.filter_frame_price_range_label);
        filter_frame_price_range_layout = v.findViewById(R.id.filter_frame_price_range_layout);
        filter_frame_price_min = v.findViewById(R.id.filter_frame_price_min);
        filter_frame_price_max = v.findViewById(R.id.filter_frame_price_max);
        filter_frame_price_error = v.findViewById(R.id.filter_frame_price_error);
        filter_frame_type_label = v.findViewById(R.id.filter_frame_type_label);
        filter_frame_type_group = v.findViewById(R.id.filter_frame_type_group);
        filter_frame_rb_expense = v.findViewById(R.id.filter_frame_rb_expense);
        filter_frame_rb_income = v.findViewById(R.id.filter_frame_rb_income);
        filter_frame_rb_both = v.findViewById(R.id.filter_frame_rb_both);
        filter_frame_sort_label = v.findViewById(R.id.filter_frame_sort_label);
        filter_frame_sort_spinner = v.findViewById(R.id.filter_frame_sort_spinner);
        filter_frame_display_button = v.findViewById(R.id.filter_frame_display_button);
        filter_frame_close_button = v.findViewById(R.id.filter_frame_close_button);
    }


    private void showDatePickerDialog(final EditText editText) {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date on the EditText
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editText.setText(selectedDate);
                    }
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }

    private void setSpinnerAdapter(Spinner spinner, int arrayResId) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                arrayResId,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}