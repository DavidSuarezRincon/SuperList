package com.david.superlist.NavigationDrawer.ManageUsuarios;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Adaptadores.ManageUsuariosRecyclerViewAdapter;
import com.david.superlist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManageUsuariosFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public ManageUsuariosFragment() {
    }

    public static ManageUsuariosFragment newInstance(int columnCount) {
        ManageUsuariosFragment fragment = new ManageUsuariosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_usuarios_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // Obtén el usuario actual de Firebase
            FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();

            // Configura el adaptador con el usuario actual de Firebase
            recyclerView.setAdapter(new ManageUsuariosRecyclerViewAdapter(usuarioActual));
        }
        return view;
    }
}