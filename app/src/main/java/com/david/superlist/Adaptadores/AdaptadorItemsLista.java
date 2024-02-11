package com.david.superlist.Adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
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

// Adaptador para el RecyclerView que muestra la lista de tareas
public class AdaptadorItemsLista extends RecyclerView.Adapter<AdaptadorItemsLista.MyViewHolder> {

    // Declaración de variables de instancia
    private Context context;
    private ArrayList<TareaLista> listaTareas;
    private final RecyclerViewInterface recyclerViewInterface;

    // Constructor de la clase
    public AdaptadorItemsLista(Context context, ArrayList<TareaLista> listaTareas, RecyclerViewInterface rvi) {
        this.context = context;
        this.listaTareas = (listaTareas == null) ? new ArrayList<>() : listaTareas;
        this.recyclerViewInterface = rvi;
    }

    // Método para crear el ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_items_lista, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    // Método para vincular los datos con el ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TareaLista newTarea = listaTareas.get(position);
        Log.i("posicion", position+"");

        holder.tarea.setText(newTarea.getTask());
        holder.tarea.setChecked(newTarea.isChecked()); // Usa el nuevo campo

        Log.i("estaChekeado", newTarea.isChecked() + "");

        if (newTarea.isChecked()){
            holder.tarea.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder.tarea.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newTarea.setChecked(isChecked); // Actualiza el estado de la tarea

            int flag = isChecked ? Paint.STRIKE_THRU_TEXT_FLAG : 0;
            holder.tarea.setPaintFlags(flag);
        });

        setupImagenIcon(holder, newTarea);
    }

    // Método para configurar el icono de la tarea
    private void setupImagenIcon(MyViewHolder holder, TareaLista tarea) {
        int color = getColorInt(tarea);
        tarea.setIconPriorityColor(color);

        holder.imagen.setImageResource(R.drawable.baseline_checklist_24);
        holder.imagen.setColorFilter(color);
    }

    // Método para obtener el color correspondiente a la prioridad de la tarea
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

    // Método para obtener el número de elementos en la lista de tareas
    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    // Clase ViewHolder para el RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // Declaración de variables de instancia
        ImageView imagen;
        CheckBox tarea;

        // Constructor de la clase
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvi) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenTarea);
            tarea = itemView.findViewById(R.id.checkBoxTarea);

            // Establecimiento del listener de clic largo
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