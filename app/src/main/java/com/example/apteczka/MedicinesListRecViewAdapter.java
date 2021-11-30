package com.example.apteczka;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MedicinesListRecViewAdapter extends RecyclerView.Adapter<MedicinesListRecViewAdapter.ViewHolder>
{
    private ArrayList<Medicine> medicines = new ArrayList<>();

    public MedicinesListRecViewAdapter(){}

    //Metoda wywołana przy tworzezniu adaptera
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicines_list, parent, false);
        MedicinesListRecViewAdapter.ViewHolder vHolder = new MedicinesListRecViewAdapter.ViewHolder(view);

        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //Przepisane daty z wartości int do stringa
        String date = new String();
        date += String.valueOf(medicines.get(holder.getAdapterPosition()).getDay());
        date += ".";
        date += String.valueOf(medicines.get(holder.getAdapterPosition()).getMonth());
        date += ".";
        date += String.valueOf(medicines.get(holder.getAdapterPosition()).getYear());
        date += ".";

        //Ustawienie pol tekstowych wedlug wartosci z klasy Medicine
        holder.MedicineName.setText(medicines.get(holder.getAdapterPosition()).getName());
        holder.MedicineDate.setText(date);
    }

    //Metoda zwraca ilosc pozycji wyswietlanych w recyclerView
    @Override
    public int getItemCount()
    {
        return medicines.size();
    }

    public void setMedicines(ArrayList<Medicine> medicines)
    {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView MedicineName, MedicineDate;
        private Button editButton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            MedicineName = itemView.findViewById(R.id.medicine_name);
            MedicineDate = itemView.findViewById(R.id.medicine_date);
            editButton = itemView.findViewById(R.id.medicine_edit_button);

            editButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(itemView.getContext(), EditMedicineActivity.class);
                    intent.putExtra("ID", medicines.get(getAdapterPosition()).getID());
                    intent.putExtra("NAME", medicines.get(getAdapterPosition()).getName());
                    intent.putExtra("DAY", medicines.get(getAdapterPosition()).getDay());
                    intent.putExtra("MONTH", medicines.get(getAdapterPosition()).getMonth());
                    intent.putExtra("YEAR", medicines.get(getAdapterPosition()).getYear());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
