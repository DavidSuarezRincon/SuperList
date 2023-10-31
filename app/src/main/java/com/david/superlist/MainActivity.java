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

            Intent intent = new Intent(this,AniadirListaActivity.class);
            intent.putExtra("MainActivity",this.getClass());

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
        datosLista.add(new Lista(4, "Lista de la compra " + posicionLista, "Esto es una descripción", "27/10/2023"));
        datosLista.add(new Lista(3, "Lista de deseos " + posicionLista, "Para los reyes", "3/12/2023"));
        datosLista.add(new Lista(2, "Pasos a seguir " + posicionLista, "Esto es una descripción", "27/10/2024"));
        datosLista.add(new Lista(1, "No se que poner " + posicionLista, "Alo alo", "27/11/2023"));
        datosLista.add(new Lista(1, "Lista de shitler " + posicionLista, "Pelicula", "28/10/2023"));

        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext());

        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);

    }

    private void aniadirLista(String nombre, String Descripcion) {
        Random rand = new Random();
        Calendar calendario = Calendar.getInstance();

        datosLista.add(new Lista(colorRandom(), nombre, Descripcion, calendario.getTime().toString()));
        adaptador.notifyDataSetChanged();
    }

    public int colorRandom() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}