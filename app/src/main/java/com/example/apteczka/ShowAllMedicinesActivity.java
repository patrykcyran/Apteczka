package com.example.apteczka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowAllMedicinesActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_show_all_medicines);

        initViews();
    }

    private void initViews()
    {
        adapter = new MedicinesListRecViewAdapter();

        medicinesRecView = findViewById(R.id.medicines_RecyclerView);

        medicinesRecView.setAdapter(adapter);
        medicinesRecView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAccess = MedicinesDataBaseAccess.getInsatnce(this);
        dataBaseAccess.open();
        medicines = dataBaseAccess.getAllMedicinesAtoZ();
        dataBaseAccess.close();
        adapter.setMedicines(medicines);
    }
}