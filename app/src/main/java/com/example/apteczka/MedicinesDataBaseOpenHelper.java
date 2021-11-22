package com.example.apteczka;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MedicinesDataBaseOpenHelper extends SQLiteAssetHelper
{
    //Konstruktor
    public MedicinesDataBaseOpenHelper(Context context)
    {
        super(context, "medicines_db.db", null, 1);
    }
}
