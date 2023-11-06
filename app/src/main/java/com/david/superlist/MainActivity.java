package com.david.superlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

// Clase MainActivity que extiende de AppCompatActivity e implementa Serializable y RecyclerViewInterface
public class MainActivity extends AppCompatActivity implements Serializable, RecyclerViewInterface {

    // Declaración de variables
    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private ArrayList<Lista> datosLista;
    private Button boton;
    private AdaptadorLista adaptador;
    private ActivityResultLauncher<Intent> resultLauncher;

    // Método onCreate que se ejecuta al iniciar la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de botones y asignación de eventos onClick
        boton = findViewById(R.id.botonIr);
        boton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        btnAniadirLista = findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(view -> {
            Intent intent = new Intent(this, AniadirListaActivity.class);
            startActivity(intent);
        });

        // Llamada al método iniciar
        iniciar();
    }

    // Método para inicializar la lista y el adaptador
    private void iniciar() {
        datosLista = new ArrayList<>();
        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext(), this);

        aniadirLista("Lista de la compra 1", "Esto es una prueba de descripción");
        aniadirLista("Lista", "Lista");

        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);
        registerForContextMenu(recView);
    }

    // Método para añadir una lista
    private void aniadirLista(String nombre, String Descripcion) {
        Random rand = new Random();
        Calendar calendario = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendario.getTime());

        datosLista.add(new Lista(colorRandom(), nombre, Descripcion, currentDate));
        adaptador.notifyDataSetChanged();
    }

    // Método para generar un color aleatorio
    public int colorRandom() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    // Método para borrar una lista
    public void borrarLista(int numLista) {
        datosLista.remove(numLista);
        adaptador.notifyDataSetChanged();
    }

    // Método que se ejecuta al hacer click en un item
    @Override
    public void onItemClick(int position) {

    }

    // Método que se ejecuta al hacer click largo en un item
    @Override
    public void onItemLongClick(int position) {
        borrarLista(position);
        adaptador.notifyItemRemoved(position);
    }
}