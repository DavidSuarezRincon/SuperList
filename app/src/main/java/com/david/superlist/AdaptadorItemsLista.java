package com.david.superlist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorItemsLista extends RecyclerView.Adapter<AdaptadorItemsLista.MyViewHolder> {

    Context context;
    ArrayList<TareaLista> listaTareas;

    public AdaptadorItemsLista(Context context, ArrayList<TareaLista> listaTareas) {
        this.context = context;
        this.listaTareas = listaTareas;
    }

    @NonNull
    @Override
    public AdaptadorItemsLista.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_items_lista, parent, false);

        return new AdaptadorItemsLista.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // assigning values to the views we created in the recycler_view_row layout file
        // based on the position of the recycler view

        TareaLista newTarea = listaTareas.get(position);

        holder.tarea.setText(newTarea.getTarea()); //Pone el texto al CheckedTextView del layout.
        holder.imagen.setImageResource(R.drawable.baseline_checklist_24);
        holder.imagen.setColorFilter(getColorInt(newTarea));//Le pone el color al icono del layout.

        holder.tarea.setOnClickListener(v -> {

            //Cuando el usuario marque como check un checkbox de las tareas su texto será tachado.

            if (holder.tarea.isChecked()) {
                holder.tarea.setPaintFlags(holder.tarea.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tarea.setPaintFlags(holder.tarea.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

        });
    }

    private int getColorInt(TareaLista tarea) {

        //Method for change the color of the icon.

        String prioridad = tarea.getPrioridad().toLowerCase();

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

        tarea.setColorIconoPrioridad(color);
        return color;

    }

    @Override
    public int getItemCount() {

        // the recycler view just wants to know the number of items you want displayed

        return listaTareas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // grabbing the views from our recycler_view_row layout file
        // Kinda like in the onCreate method

        ImageView imagen;
        CheckBox tarea;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenTarea);
            tarea = itemView.findViewById(R.id.checkBoxTarea);
        }
    }
}

