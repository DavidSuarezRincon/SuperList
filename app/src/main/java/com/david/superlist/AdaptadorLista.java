package com.david.superlist;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.TitularesViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Lista> datos;
    private LayoutInflater inflater;
    private Context contexto;

    public static class TitularesViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconImagen;
        private final TextView txtTitulo;
        private final TextView txtSubtitulo;
        private final TextView txtFecha;

        public TitularesViewHolder(View itemView) {
            super(itemView);
            iconImagen =  itemView.findViewById(R.id.iconImageView);
            txtTitulo = itemView.findViewById(R.id.TxtVTitulo);
            txtSubtitulo = itemView.findViewById(R.id.TxtVDescripcion);
            txtFecha = itemView.findViewById(R.id.TxtVFecha);
        }

        public void bindTitular(Lista l) {
            iconImagen.setColorFilter(Color.parseColor(l.getColor()), PorterDuff.Mode.SRC_IN);

            txtTitulo.setText(l.getTitulo());
            txtSubtitulo.setText(l.getDescripcion());
            txtFecha.setText(l.getFecha());
        }
    }

    public AdaptadorLista(ArrayList<Lista> datos, Context contexto) {
        this.inflater = LayoutInflater.from(contexto);
        this.datos = datos;
        this.contexto = contexto;
    }

    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = inflater.inflate(R.layout.listitem_titular, null);
        //itemView.setOnClickListener(this);
        //TitularesViewHolder tvh = new TitularesViewHolder(itemView);
        return new TitularesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TitularesViewHolder viewHolder, int pos) {
        Lista item = datos.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) listener.onClick(view);
    }

    public void setItems(ArrayList<Lista> items) {

        datos = items;

    }

}
