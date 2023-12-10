package com.david.superlist.Adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.R;
import com.david.superlist.pojos.TareaLista;

import java.util.ArrayList;

public class AdaptadorItemsLista extends RecyclerView.Adapter<AdaptadorItemsLista.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<TareaLista> listaTareas;
    private final RecyclerViewInterface recyclerViewInterface;

    public AdaptadorItemsLista(Context context, ArrayList<TareaLista> listaTareas, RecyclerViewInterface rvi) {
        this.context = context;
        this.listaTareas = listaTareas;
        this.recyclerViewInterface = rvi;
    }

    @NonNull
    @Override
    public AdaptadorItemsLista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_items_lista, parent, false);

        return new AdaptadorItemsLista.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SharedPreferences preferences = context.getSharedPreferences("userTasksCheck", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        TareaLista newTarea = listaTareas.get(position);
        String hashCodeNewTarea = String.valueOf(newTarea.hashCode());

        holder.tarea.setText(newTarea.getTask()); //Pone el texto al CheckedTextView del layout.
        holder.tarea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferencesEditor.putBoolean(hashCodeNewTarea, isChecked);
            preferencesEditor.apply();

            if (holder.tarea.isChecked()) {
                holder.tarea.setPaintFlags(holder.tarea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tarea.setPaintFlags(holder.tarea.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });
        boolean tareaIsChecked = preferences.getBoolean(hashCodeNewTarea, false);
        holder.tarea.setChecked(tareaIsChecked);

        holder.imagen.setImageResource(R.drawable.baseline_checklist_24);
        holder.imagen.setColorFilter(getColorInt(newTarea));//Le pone el color al icono del layout.

    }

    private int getColorInt(TareaLista tarea) {

        //Method for change the color of the icon.

        String prioridad = tarea.getPriority().toLowerCase();

        int color = 0;

        switch (prioridad) {

            case "baja":
                color = Color.rgb(0, 255, 0); //Color verde
                break;
            case "media":
                color = Color.rgb(255, 255, 0); //Color amarillo
                break;
            case "alta":
                color = Color.rgb(255, 0, 0); //Color Rojo
                break;
        }

        tarea.setIconPriorityColor(color);
        return color;

    }

    @Override
    public int getItemCount() {

        // the recycler view just wants to know the number of items you want displayed

        return listaTareas.size();
    }

    @Override
    public void onClick(View v) {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // grabbing the views from our recycler_view_row layout file
        // Kinda like in the onCreate method

        ImageView imagen;
        CheckBox tarea;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenTarea);
            tarea = itemView.findViewById(R.id.checkBoxTarea);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (rvi != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            rvi.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });

        }


    }
}

