package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

        iniciar();

//        adaptador.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Log.i("DemoRecView", "Pulsado el elemento ");
//            }
//
//        });


//        btnAniadirLista = findViewById(R.id.btnAniadirLista);
//        btnAniadirLista.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //datosLista.add(new Lista("Lista de la compra " + posicionLista, "Esto es una descripción", "27/10/2023"));
//                adaptador.notifyItemInserted(posicionLista);
//                posicionLista++;
//                Log.i("Estoy Apretando", "El boton para aniadir");
//            }
//
//        });

    }

    private void iniciar() {

        posicionLista = 1;

        datosLista = new ArrayList<>();
        datosLista.add(new Lista("#775447", "Lista de la compra " + posicionLista, "Esto es una descripción", "27/10/2023"));
        datosLista.add(new Lista("#607d8b", "Lista de deseos " + posicionLista, "Para los reyes", "3/12/2023"));
        datosLista.add(new Lista("#765451", "Pasos a seguir " + posicionLista, "Esto es una descripción", "27/10/2024"));
        datosLista.add(new Lista("#7b5b42", "No se que poner " + posicionLista, "Alo alo", "27/11/2023"));
        datosLista.add(new Lista("#7b5b45", "Lista de shitler " + posicionLista, "Pelicula", "28/10/2023"));

        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext());

        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);

    }

    ;
}