package com.david.superlist.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.R;
import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.pojos.Lista;

import java.util.ArrayList;

// Clase AdaptadorLista que extiende de RecyclerView.Adapter
public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ListaViewHolder> implements View.OnClickListener {

    // Declaración de variables
    private View.OnClickListener listener;
    private ArrayList<Lista> datos; // Lista de datos a mostrar en el RecyclerView
    private LayoutInflater inflater; // Inflater para inflar la vista de cada item del RecyclerView
    private final RecyclerViewInterface recyclerViewInterface; // Interfaz para gestionar los clicks en los items del RecyclerView

    // Constructor de la clase
    public AdaptadorLista(ArrayList<Lista> datos, Context contexto, RecyclerViewInterface rvi) {
        this.inflater = LayoutInflater.from(contexto);
        this.datos = datos;
        this.recyclerViewInterface = rvi;
    }

    // Método para crear el ViewHolder
    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = inflater.inflate(R.layout.activity_lista, null);
        return new ListaViewHolder(itemView, recyclerViewInterface);
    }

    // Método para vincular los datos con el ViewHolder
    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, int pos) {
        Lista item = datos.get(pos);
        viewHolder.bindLista(item);
    }

    // Método para obtener el número de elementos en los datos
    @Override
    public int getItemCount() {
        return datos.size();
    }

    // Método para establecer el listener de click
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    // Método para gestionar el evento de click
    @Override
    public void onClick(View view) {
        if (listener != null) listener.onClick(view);
    }

    // Método para establecer los elementos de la lista
    public void setItems(ArrayList<Lista> items) {
        datos = items;
    }

    // Clase ListaViewHolder que extiende de RecyclerView.ViewHolder
    public static class ListaViewHolder extends RecyclerView.ViewHolder {

        // Declaración de variables
        private final TextView txtTitulo;
        private final TextView txtDescripcion;
        private final TextView txtFecha;

        // Constructor de la clase
        public ListaViewHolder(View itemView, RecyclerViewInterface rvi) {
            super(itemView);

            // Inicialización de las vistas
            txtTitulo = itemView.findViewById(R.id.TxtVTitulo);
            txtDescripcion = itemView.findViewById(R.id.TxtVTipoLista);
            txtFecha = itemView.findViewById(R.id.TxtVFecha);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rvi != null) {

                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            rvi.onItemClick(pos);
                        }

                    }
                }
            });

            // Establecimiento del listener de click largo
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

        // Método para vincular los datos con las vistas
        public void bindLista(Lista l) {
            txtTitulo.setText(l.getName());
            txtTitulo.setTextColor(l.getColor());
            txtDescripcion.setText(l.getDescription());
            txtFecha.setText(l.getCreationDate());
        }
    }
}


