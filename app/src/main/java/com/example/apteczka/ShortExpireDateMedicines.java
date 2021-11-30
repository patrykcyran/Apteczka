package com.example.apteczka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ShortExpireDateMedicines extends AppCompatActivity
{

    private ArrayList<Medicine> medicines = new ArrayList<>();
    private RecyclerView medicinesRecView;
    private MedicinesListRecViewAdapter adapter;
    private MedicinesDataBaseAccess dataBaseAccess;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_expire_date);

        initViews();
    }

    private void initViews()
    {
        adapter = new MedicinesListRecViewAdapter();

        medicinesRecView = findViewById(R.id.short_expire_date_medicines_RecyclerView);

        medicinesRecView.setAdapter(adapter);
        medicinesRecView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAccess = MedicinesDataBaseAccess.getInsatnce(this);
        dataBaseAccess.open();
        medicines = dataBaseAccess.getShortExpireDateMedicines();
        dataBaseAccess.close();
        adapter.setMedicines(medicines);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        dataBaseAccess.open();
        medicines = dataBaseAccess.getShortExpireDateMedicines();
        dataBaseAccess.close();
        adapter.setMedicines(medicines);
        adapter.notifyDataSetChanged();
    }
}