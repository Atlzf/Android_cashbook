package com.example.cashbook2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class AddcashFragment extends Fragment {

    private CashDatabaseHelper dbHelper;
    private String userId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addcash, container, false);

        dbHelper = new CashDatabaseHelper(getContext());

        // 获取从Activity传递过来的用户id
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
        }


        TextView dateTextView = view.findViewById(R.id.dateTextView);
        EditText nameEditText = view.findViewById(R.id.textView5);
        RadioGroup typeRadioGroup = view.findViewById(R.id.radioGroup);
        EditText amountEditText = view.findViewById(R.id.amountEditText);
        EditText descriptionEditText = view.findViewById(R.id.descriptionEditText);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String selectedDate = year+ "/"+(month + 1) + "/" + dayOfMonth ;
                                dateTextView.setText(selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        Button saveButton = view.findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = dateTextView.getText().toString();
                String name = nameEditText.getText().toString();
                RadioButton selectedRadioButton = view.findViewById(typeRadioGroup.getCheckedRadioButtonId());
                String type = selectedRadioButton.getText().toString();
                double amount = Double.parseDouble(amountEditText.getText().toString());
                String description = descriptionEditText.getText().toString();

                // 将信息存储到数据库中
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("date", date);
                values.put("name", name);
                values.put("type", type);
                values.put("amount", amount);
                values.put("description", description);
                values.put("user_id", userId); //
                long newRowId = db.insert("cash", null, values);
                if (newRowId == -1) {
                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }
}