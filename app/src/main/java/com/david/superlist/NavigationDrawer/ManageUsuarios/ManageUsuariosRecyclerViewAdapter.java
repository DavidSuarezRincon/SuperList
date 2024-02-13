package com.david.superlist.NavigationDrawer.ManageUsuarios;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.databinding.FragmentManageUsuariosBinding;
import com.david.superlist.placeholder.PlaceholderContent.PlaceholderItem;
import com.david.superlist.pojos.Usuario;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ManageUsuariosRecyclerViewAdapter extends RecyclerView.Adapter<ManageUsuariosRecyclerViewAdapter.ViewHolder> {

    private final List<Usuario> mValues;

    public ManageUsuariosRecyclerViewAdapter(List<Usuario> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentManageUsuariosBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Usuario usuario = mValues.get(position);
        holder.mIdView.setText(usuario.getNombre()); // Asume que Usuario tiene un método getNombre
        holder.mContentView.setText(usuario.getEmail()); // Asume que Usuario tiene un método getEmail
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentManageUsuariosBinding binding) {
            super(binding.getRoot());
            mIdView = binding.emailUsuario;
            mContentView = binding.nombreUsuario;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}