package com.example.apteczka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    private Button AddBtn, ShowAllMedicines, ShowShortExpireDateMedicines;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        ShowAllMedicines.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ShowAllMedicinesActivity.class);
                startActivity(intent);
            }
        });

        AddBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AddMedicineActivity.class);
                startActivity(intent);
            }
        });

        ShowShortExpireDateMedicines.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), ShortExpireDateMedicines.class);
                startActivity(intent);
            }
        });
    }

    private void initViews()
    {
        AddBtn = findViewById(R.id.add_medicine_button);
        ShowAllMedicines = findViewById(R.id.show_all_medicines_button);
        ShowShortExpireDateMedicines = findViewById(R.id.show_medicines_with_short_termin_button);
    }
}