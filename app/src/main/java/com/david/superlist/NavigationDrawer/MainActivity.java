package com.david.superlist.NavigationDrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.david.superlist.Login.LoginActivity;
import com.david.superlist.R;
import com.david.superlist.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);

//        preferenciasDeUsuario = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
//        editorDePreferencias = preferenciasDeUsuario.edit();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            // No hay un usuario autenticado, redirige al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String userEmail = currentUser.getEmail();
        TextView nombreUsuarioHeader = headerView.findViewById(R.id.TextViewNombreUsuarioHeader);
        nombreUsuarioHeader.setText(userEmail.split("@")[0]);
        TextView emailUsuarioheader = headerView.findViewById(R.id.TextViewEmailUsuarioHeader);
        emailUsuarioheader.setText(userEmail);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_MenuListas, R.id.nav_Logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            //Cuando clice en logout cierra sesión.
            if (item.getItemId() == R.id.nav_Logout) {
                return logOut();
            }

            drawer.closeDrawers();

            // Delega el manejo de los clics de los demás elementos del menú a NavigationUI
            return NavigationUI.onNavDestinationSelected(item, navController)
                    || super.onOptionsItemSelected(item);
        });
    }

    private boolean logOut() {
        //Cuando se cierra sesión se borran los datos guardados en preferences.
//                editorDePreferencias.clear();
//                editorDePreferencias.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

        String sucefullLogOutText = getResources().getString(R.string.mensajeLogoutExitoso);
        Toast.makeText(this, sucefullLogOutText, Toast.LENGTH_SHORT).show();

//        SharedPreferences preferencias = getSharedPreferences("PreferenciasUsuario", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferencias.edit();
//
//        editor.remove("estadoLogUsuario");
//        editor.apply();
        FirebaseAuth.getInstance().signOut();
        finish();
        return true;
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        SharedPreferences preferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferencias.edit();
//
//        Gson gson = new Gson();
//        String usuarios = gson.toJson(UsuariosRegistrados.getUsers());
//
//        editor.putString("usuariosRegistrados", usuarios);
//        editor.apply();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        SharedPreferences datosCompartidos = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = datosCompartidos.edit();
//
//        Gson gson = new Gson();
//        String usuarios = gson.toJson(UsuariosRegistrados.getUsers());
//
//        editor.putString("usuariosRegistrados", usuarios);
//        editor.apply();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}