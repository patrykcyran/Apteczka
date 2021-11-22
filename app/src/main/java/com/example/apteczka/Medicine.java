package com.example.apteczka;

import android.content.Intent;

public class Medicine
{
    private int ID;
    private String Name;
    private int year;
    private int month;
    private int day;

    public Medicine(int ID, String name, int year, int month, int day)
    {
        this.ID = ID;
        Name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
}
