package com.david.superlist.NavigationDrawer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.david.superlist.Login.LoginActivity;
import com.david.superlist.R;
import com.david.superlist.databinding.ActivityMainBinding;
import com.david.superlist.pojos.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// Esta clase representa la actividad principal de la aplicación
public class MainActivity extends AppCompatActivity {

    // Declaración de variables de instancia
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private FirebaseAuth.AuthStateListener authStateListener;

    // Método onCreate que se llama al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflado de la vista de la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración de la barra de herramientas
        setSupportActionBar(binding.appBarMain.toolbar);

        // Inicialización del cajón de navegación y la vista de navegación
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Obtención de la vista de cabecera de la vista de navegación
        View headerView = navigationView.getHeaderView(0);

        // Obtención del usuario actual de Firebase
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Si no hay un usuario autenticado, redirige al usuario a la pantalla de inicio de sesión
        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String userId = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("SuperList").child(userId);

        userRef.addValueEventListener(new ValueEventListener() {

            //Checkeo si es usuario esta baneado. Si lo está lo redirige al login.
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                if (usuario != null) {

                    boolean isBaned = usuario.isBaned();
                    if (isBaned) {
                        // isBaned es true
                        Toast.makeText(MainActivity.this, "Has sido baneado, no puedes iniciar sesión", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error al obtener el valor de isChecked
                Log.e("OnCanceledError", "onCancelled: MainActivity");
            }
        });


        MenuItem navGrafico = navigationView.getMenu().findItem(R.id.nav_grafico);
        MenuItem navAdminUsuarios = navigationView.getMenu().findItem(R.id.nav_administrar_usuarios);
        userRef.child("role").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rol = dataSnapshot.getValue(Integer.class);
                if (rol == 1) {
                    // El usuario es administrador
                    navGrafico.setVisible(true);
                    navAdminUsuarios.setVisible(true);
                } else {
                    // El usuario es normal
                    navGrafico.setVisible(false);
                    navAdminUsuarios.setVisible(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error al obtener el rol
                Log.e("OnCanceledError", "onCancelled: MainActivity");
            }
        });


        // Extracción del nombre de usuario del correo electrónico
        String userEmail = currentUser.getEmail();
        TextView nombreUsuarioHeader = headerView.findViewById(R.id.TextViewNombreUsuarioHeader);
        nombreUsuarioHeader.setText(userEmail.split("@")[0]);
        TextView emailUsuarioheader = headerView.findViewById(R.id.TextViewEmailUsuarioHeader);
        emailUsuarioheader.setText(userEmail);

        // Configuración de la AppBar
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_MenuListas, R.id.nav_administrar_usuarios, R.id.nav_grafico, R.id.nav_Logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Configuración del listener de la vista de navegación
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

    // Método para cerrar sesión
    private boolean logOut() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

        String sucefullLogOutText = getResources().getString(R.string.mensajeLogoutExitoso);
        Toast.makeText(this, sucefullLogOutText, Toast.LENGTH_SHORT).show();

        FirebaseAuth.getInstance().signOut();
        finish();
        return true;
    }

    // Método para manejar la navegación hacia arriba
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}