package com.david.superlist.NavigationDrawer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.david.superlist.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Esta clase representa el fragmento de inicio en la aplicación
public class HomeFragment extends Fragment {

    // Variable para el enlace de la vista del fragmento
    private FragmentHomeBinding binding;

    // Método para crear la vista del fragmento
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicialización del ViewModel
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Obtención del usuario actual de Firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            // Extracción del nombre de usuario del correo electrónico
            String username = currentUser.getEmail();
            homeViewModel.setTextUserName(username.split("@")[0]);
        }

        // Inflado de la vista del fragmento
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observación del texto del ViewModel para actualizar la vista
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    // Método para limpiar la vista cuando el fragmento es destruido
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}