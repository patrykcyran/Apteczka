package com.example.apteczka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.apteczka.Medicine;

import java.util.ArrayList;

public class MedicinesDataBaseAccess
{
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static MedicinesDataBaseAccess insatnce;

    private static final String MEDICINES_TABLE = "MEDICINES_TABLE";
    private static final String MEDICINES_TABLE_ID = "MEDICINES_TABLE.id";
    private static final String MEDICINE_NAME = "MEDICINES_TABLE.medicine_name";
    private static final String MEDICINE_DAY = "MEDICINES_TABLE.medicine_day";
    private static final String MEDICINE_MONTH = "MEDICINES_TABLE.medicine_month";
    private static final String MEDICINE_YEAR = "MEDICINES_TABLE.medicine_year";


    //Prywanty konstruktor żeby być pewnym że obiekt klasy nie zostanie utworzony poza nią
    private MedicinesDataBaseAccess(Context context)
    {
        this.openHelper = new MedicinesDataBaseOpenHelper(context);
    }

    //Funckja odpowiada za to żeby istniała tylko jedna instancja obiektu tej klasy
    public static MedicinesDataBaseAccess getInsatnce(Context context)
    {
        if(insatnce == null)
        {
            insatnce = new MedicinesDataBaseAccess(context);
        }
        return insatnce;
    }

    public void open()
    {
        this.database = openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(database != null)
        {
            database.close();
        }
    }

    //Metoda zwraca wszystkie leki z bazy danych
    public ArrayList<Medicine> getAllMedicines()
    {
        ArrayList<Medicine> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " +
                MEDICINES_TABLE + " " +
                "ORDER BY " + MEDICINE_NAME + " ASC";


        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(2);
                String name = cursor.getString(1);
                int day = cursor.getInt(0);
                int month = cursor.getInt(3);
                int year = cursor.getInt(4);

                Medicine medicine = new Medicine(ID, name, day, month, year);
                returnList.add(new Medicine(ID, name, year, month, day));

            }while(cursor.moveToNext());
        }
        cursor.close();

        return returnList;
    }

    public ArrayList<Medicine> getAllMedicinesAtoZ()
    {
        ArrayList<Medicine> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " +
                MEDICINES_TABLE + " " +
                "ORDER BY " + MEDICINE_NAME + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                int day = cursor.getInt(2);
                int month = cursor.getInt(3);
                int year = cursor.getInt(4);

                Medicine medicine = new Medicine(ID, name, year, month, day);
                returnList.add(medicine);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return returnList;
    }

    //Metoda dodaje do bazy danych lek przekazany jako parametr
    public void addMedicine(String name, int day, int month, int year)
    {
        ContentValues values = new ContentValues();
        values.put("medicine_name", name);
        values.put("medicine_day", day);
        values.put("medicine_month", month);
        values.put("medicine_year", year);

        database.insert(MEDICINES_TABLE, null, values);
    }

    public void editMedicine(int ID, String name, int day, int month, int year)
    {
        ContentValues values = new ContentValues();
        values.put("medicine_name",name);
        values.put("medicine_day",day);
        values.put("medicine_month",month);
        values.put("medicine_year",year);

        database.update(MEDICINES_TABLE, values, "id == " + ID, null);
    }
}
