package com.example.apteczka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddMedicineActivity extends AppCompatActivity
{

    private EditText MedicineName, MedicineDay, MedicineMonth, MedicineYear;
    private Button addMedicineButton;
    private MedicinesDataBaseAccess dataBaseAccess;
    private String medicineName;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        initViews();

        addMedicineButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dataBaseAccess = MedicinesDataBaseAccess.getInsatnce(getApplicationContext());
                medicineName = String.valueOf(MedicineName.getText());
                day = Integer.parseInt(String.valueOf(MedicineDay.getText()));
                month = Integer.parseInt(String.valueOf(MedicineMonth.getText()));
                year = Integer.parseInt(String.valueOf(MedicineYear.getText()));
                dataBaseAccess.open();
                dataBaseAccess.addMedicine(medicineName, year, month, day);
                dataBaseAccess.close();

                finish();
            }
        });
    }

    private void initViews()
    {
        MedicineName = findViewById(R.id.medicine_add_medicine_name_editText);
        MedicineDay = findViewById(R.id.medicine_add_medicine_day_editText);
        MedicineMonth = findViewById(R.id.medicine_add_medicine_month_editText);
        MedicineYear = findViewById(R.id.medicine_add_medicine_year_editText);

        addMedicineButton = findViewById(R.id.medicine_add_button);
    }
}