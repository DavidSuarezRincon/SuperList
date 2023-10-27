package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private ArrayList<Lista> datosLista;
    private Button boton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boton = findViewById(R.id.botonIr);
        boton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        datosLista = new ArrayList<>();

        recView.findViewById(R.id.recViewLista);
        recView.setHasFixedSize(true);

        final AdaptadorLista adaptador = new AdaptadorLista(datosLista);

        adaptador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("DemoRecView", "Pulsado el elemento " + recView.getChildAdapterPosition(v));
            }

        });

        recView.setAdapter(adaptador);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        btnAniadirLista = findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aniadirLista();
                adaptador.notifyItemInserted(1);
            }

        });

    }

    private void aniadirLista() {

        Lista nuevaLista = new Lista("Lista de la compra", "Esto es una descripcion", "27/10/2023");
        datosLista.add(nuevaLista);
    }
}