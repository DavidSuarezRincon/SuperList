package com.david.superlist;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdaptadorLista
        extends RecyclerView.Adapter<AdaptadorLista.TitularesViewHolder>
        implements View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Lista> datos;

    public static class TitularesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtSubtitulo;

        public TitularesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.LblTitulo);
            txtSubtitulo = itemView.findViewById(R.id.LblDescripcion);
        }

        public void bindTitular(Lista l) {
            txtTitulo.setText(l.getTitulo());
            txtSubtitulo.setText(l.getDescripcion());
        }
    }

    public AdaptadorLista(ArrayList<Lista> datos) {
        this.datos = datos;
    }

    @Override
    public TitularesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_titular, viewGroup, false);

        itemView.setOnClickListener(this);

        TitularesViewHolder tvh = new TitularesViewHolder(itemView);

        return tvh;
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
        if(listener != null)
            listener.onClick(view);
    }
    /*@Override
    public void onClick(View view) {
        Log.i("DemoRecView", "Pulsado el elemento " + ((RecyclerView)view.getParent()).getChildAdapterPosition(view));
    }*/
}
