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

public class AdaptadorItemsLista extends RecyclerView.Adapter<AdaptadorItemsLista.MyViewHolder> {

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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_items_lista, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TareaLista newTarea = listaTareas.get(position);
        String hashCodeNewTarea = String.valueOf(newTarea.hashCode());

        holder.tarea.setText(newTarea.getTask());
        setupTareaCheckBox(holder, hashCodeNewTarea);
        boolean tareaIsChecked = getTareaIsChecked(hashCodeNewTarea);
        holder.tarea.setChecked(tareaIsChecked);

        setupImagenIcon(holder, newTarea);
    }

    private void setupTareaCheckBox(MyViewHolder holder, String hashCodeNewTarea) {
        SharedPreferences preferences = context.getSharedPreferences("userTasksCheck", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        holder.tarea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferencesEditor.putBoolean(hashCodeNewTarea, isChecked);
            preferencesEditor.apply();

            int paintFlags = isChecked ? Paint.STRIKE_THRU_TEXT_FLAG : 0;
            holder.tarea.setPaintFlags(paintFlags);

        });
    }

    private boolean getTareaIsChecked(String hashCodeNewTarea) {
        SharedPreferences preferences = context.getSharedPreferences("userTasksCheck", Context.MODE_PRIVATE);
        return preferences.getBoolean(hashCodeNewTarea, false);
    }

    private void setupImagenIcon(MyViewHolder holder, TareaLista tarea) {
        int color = getColorInt(tarea);
        tarea.setIconPriorityColor(color);

        holder.imagen.setImageResource(R.drawable.baseline_checklist_24);
        holder.imagen.setColorFilter(color);
    }

    private int getColorInt(TareaLista tarea) {
        String prioridad = tarea.getPriority().toLowerCase();

        switch (prioridad) {
            case "baja":
                return Color.rgb(0, 255, 0); // Verde
            case "media":
                return Color.rgb(255, 255, 0); // Amarillo
            case "alta":
                return Color.rgb(255, 0, 0); // Rojo
            default:
                return Color.WHITE;
        }
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        CheckBox tarea;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenTarea);
            tarea = itemView.findViewById(R.id.checkBoxTarea);

            itemView.setOnLongClickListener(v -> {
                if (rvi != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        rvi.onItemLongClick(pos);
                    }
                }
                return true;
            });
        }
    }
}