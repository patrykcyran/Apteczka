package com.example.apteczka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import com.example.apteczka.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private static final String MEDICINE_DATE = "MEDICINES_TABLE.medicine_date";


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
                int year = cursor.getInt(2);
                int month = cursor.getInt(3);
                int day = cursor.getInt(4);

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
        //Upewnienie sie ze data zostanie zapisana w popranym do posortowania formacie
        String days, months, years, date;
        days = String.valueOf(day);
        months = String.valueOf(month);
        years = String.valueOf(year);

        if(day < 10)
        {
            days = "0" + days;
        }

        if(month < 10)
        {
            months = "0" + months;
        }

        date = years + "-" + months + "-" + days;


        ContentValues values = new ContentValues();
        values.put("medicine_name", name);
        values.put("medicine_day", day);
        values.put("medicine_month", month);
        values.put("medicine_year", year);
        values.put("medicine_date", date);

        database.insert(MEDICINES_TABLE, null, values);
    }

    public void editMedicine(int ID, String name, int day, int month, int year)
    {
        String days, months, years, date;
        days = String.valueOf(day);
        months = String.valueOf(month);
        years = String.valueOf(year);

        if(day < 10)
        {
            days = "0" + days;
        }

        if(month < 10)
        {
            months = "0" + months;
        }

        date = years + "-" + months + "-" + days;

        ContentValues values = new ContentValues();
        values.put("medicine_name",name);
        values.put("medicine_day",day);
        values.put("medicine_month",month);
        values.put("medicine_year",year);
        values.put("medicine_date", date);

        database.update(MEDICINES_TABLE, values, "id == " + ID, null);
    }


    //Wyciaga z bazy danych leki z krotkim terminem przydatnosci (ponizej 2 miesiecy)
    public ArrayList<Medicine> getShortExpireDateMedicines()
    {
        ArrayList<Medicine> returnList = new ArrayList<>();

        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay, currentMonth, currentYear;

        currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        currentMonth = currentDate.get(Calendar.MONTH) + 1;
        currentYear = currentDate.get(Calendar.YEAR);

        int expireMonth = 2 + currentMonth;

        String queryString;


        //Dla dwoch ostatnich miesiecy w roku, wyciagamy z bazy danych wszystkie leki ktore maja
        //termin waznosci do konca roku
        if(currentMonth>=11)
        {
            queryString = "SELECT * FROM " +
                    MEDICINES_TABLE + " " +
                    "WHERE " + MEDICINE_YEAR + " == " + currentYear + " " +
                    "ORDER BY " + MEDICINE_DATE + " ASC";

            Cursor cursor = database.rawQuery(queryString, null);

            if (cursor.moveToFirst())
            {
                do
                {
                    int ID = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int year = cursor.getInt(2);
                    int month = cursor.getInt(3);
                    int day = cursor.getInt(4);

                    Medicine medicine = new Medicine(ID, name, year, month, day);
                    returnList.add(medicine);
                }while (cursor.moveToNext());
            }


            //Ustalamy do ktorego miesiaca po nowym roku funkcja bedzie wyciagac dane z bazy
            currentMonth = currentMonth%11;

            expireMonth = currentMonth + 1;

            //Przechodzimy do nastepnego roku
            currentYear++;
        }



        //W pierwszym przypadku sprawdzamy date waznosci leków jeszcze z tego roku,
        //a w drugim musimy przeskoczyć już do następnego


        //W przypadku gdy roznica miesiecy, aktualnego i daty przydatnosci,
        //jest mniejsza od naszego kryterium przydatnosci to nie musimy sprawdzac dnia,
        //jeżeli jednak liczby te są równe, to o przydatnosci będzie decydował dzień

        queryString = "SELECT * FROM " +
                MEDICINES_TABLE + " " +
                "WHERE (" + MEDICINE_YEAR + " == " + currentYear + ") AND (" + MEDICINE_MONTH + " < " + expireMonth + ")" +
                "ORDER BY " + MEDICINE_DATE + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                int year = cursor.getInt(2);
                int month = cursor.getInt(3);
                int day = cursor.getInt(4);

                Medicine medicine = new Medicine(ID, name, year, month, day);
                returnList.add(medicine);
            }while (cursor.moveToNext());
        }

        queryString = "SELECT * FROM " +
                MEDICINES_TABLE + " " +
                "WHERE (" + MEDICINE_YEAR + " == " + currentYear + ") AND (" + MEDICINE_MONTH +
                " == " + expireMonth + ")" + " AND (" + MEDICINE_DAY +
                " <= " + currentDay + ") " +
                "ORDER BY " + MEDICINE_DATE + " ASC";

        cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                int year = cursor.getInt(2);
                int month = cursor.getInt(3);
                int day = cursor.getInt(4);

                Medicine medicine = new Medicine(ID, name, year, month, day);
                returnList.add(medicine);
            }while (cursor.moveToNext());
        }


        return returnList;
    }


    //Wyciaga leki w kolejnosci od tego z najkrotsza data przydatnosci
    public ArrayList<Medicine> getAllMedicinesByDateAsc()
    {
        ArrayList<Medicine> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " +
                MEDICINES_TABLE + " " +
                "ORDER BY " + MEDICINE_DATE + " ASC";

        Cursor cursor = database.rawQuery(queryString, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int ID = cursor.getInt(0);
                String name = cursor.getString(1);
                int year = cursor.getInt(2);
                int month = cursor.getInt(3);
                int day = cursor.getInt(4);

                Medicine medicine = new Medicine(ID, name, year, month, day);
                returnList.add(medicine);

            }while (cursor.moveToNext());
        }

        cursor.close();

        return returnList;
    }

    public void deleteMedicine(int ID)
    {
        database.delete(MEDICINES_TABLE, "id == " + ID, null);
    }
}
