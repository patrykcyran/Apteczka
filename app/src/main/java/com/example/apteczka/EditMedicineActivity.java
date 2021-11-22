package com.example.apteczka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMedicineActivity extends AppCompatActivity
{

    private EditText MedicineName, MedicineDay, MedicineMonth, MedicineYear;
    private Button editMedicineButton;
    private MedicinesDataBaseAccess dataBaseAccess;
    private String medicineName;
    private int ID, day, month, year;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        initViews();

        editMedicineButton.setOnClickListener(new View.OnClickListener()
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
                dataBaseAccess.editMedicine(ID, medicineName, year, month, day);
                dataBaseAccess.close();

                finish();
            }
        });
    }

    private void initViews()
    {
        MedicineName = findViewById(R.id.medicine_edit_medicine_name_editText);
        MedicineDay = findViewById(R.id.medicine_edit_medicine_day_editText);
        MedicineMonth = findViewById(R.id.medicine_edit_medicine_month_editText);
        MedicineYear = findViewById(R.id.medicine_edit_medicine_year_editText);

        editMedicineButton = findViewById(R.id.medicine_edit_button);

        intent = getIntent();
        MedicineName.setText(intent.getStringExtra("NAME"));
        MedicineDay.setText(String.valueOf(intent.getIntExtra("DAY",0)));
        MedicineMonth.setText(String.valueOf(intent.getIntExtra("MONTH",0)));
        MedicineYear.setText(String.valueOf(intent.getIntExtra("YEAR",0)));
        ID = intent.getIntExtra("ID",0);
    }
}