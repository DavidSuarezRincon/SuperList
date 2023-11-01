package com.david.superlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private ArrayList<Lista> datosLista;
    private Button boton;

    private int posicionLista;

    private AdaptadorLista adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.botonIr);
        boton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        btnAniadirLista = findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(view -> {

            Intent intent = new Intent(this, AniadirListaActivity.class);
            intent.putExtra("MainActivity", this.getClass());

            startActivity(intent);

        });
        iniciar();
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DemoRecView", "Pulsado el elemento ");
            }

        });
    }

    private void iniciar() {

        posicionLista = 1;

        datosLista = new ArrayList<>();
        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext());
        aniadirLista("Lista de la compra 1", "Esto es una prueba de descripción");
        aniadirLista("Lista", "Lista");



        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);

    }

    private void aniadirLista(String nombre, String Descripcion) {
        Random rand = new Random();
        Calendar calendario = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendario.getTime());

        datosLista.add(new Lista(colorRandom(), nombre, Descripcion, currentDate ));
        adaptador.notifyDataSetChanged();
    }

    public int colorRandom() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }
}