package com.david.superlist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorItemsLista extends RecyclerView.Adapter<AdaptadorItemsLista.ItemsViewHolder> implements View.OnClickListener {

    private final RecyclerViewInterface recyclerViewInterface;


    public AdaptadorItemsLista(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {

        // Declaración de variables
        private final TextView txtTitulo;
        private final TextView txtSubtitulo;
        private final TextView txtFecha;

        // Constructor de la clase
        public ItemsViewHolder(View itemView, RecyclerViewInterface rvi) {
            super(itemView);

            // Inicialización de las vistas
            txtTitulo = itemView.findViewById(R.id.TxtVTitulo);
            txtSubtitulo = itemView.findViewById(R.id.TxtVTipoLista);
            txtFecha = itemView.findViewById(R.id.TxtVFecha);

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
            txtTitulo.setText(l.getTitulo());
            txtTitulo.setTextColor(l.getColor());
            txtSubtitulo.setText(l.getDescripcion());
            txtFecha.setText(l.getFechaCreacion());
        }
    }
}


