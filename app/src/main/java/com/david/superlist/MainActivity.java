package com.david.superlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
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

public class MainActivity extends AppCompatActivity implements Serializable {

    private RecyclerView recView;
    private FloatingActionButton btnAniadirLista;
    private ArrayList<Lista> datosLista;
    private Button boton;
    private AdaptadorLista adaptador;

    private ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.botonIr);
        boton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));

        btnAniadirLista = findViewById(R.id.btnAniadirLista);
        btnAniadirLista.setOnClickListener(view -> {
            Intent intent = new Intent(this, AniadirListaActivity.class);
//            resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override
//                        public void onActivityResult(ActivityResult result) {
//                            if (result.getResultCode() == Activity.RESULT_OK) {
//
//                                Intent data = result.getData();
//                                String nombreLista = data.getStringExtra("NombreLista");
//                                String descripcionLista = data.getStringExtra("DescripcionLista");
//
//                                aniadirLista(nombreLista,descripcionLista);
//
//                            }
//                        }
//                    });
            startActivity(intent);


        });
        iniciar();

    }

    private void iniciar() {

        datosLista = new ArrayList<>();
        adaptador = new AdaptadorLista(datosLista, this.getApplicationContext());

        aniadirLista("Lista de la compra 1", "Esto es una prueba de descripción");
        aniadirLista("Lista", "Lista");

        recView = findViewById(R.id.rvLista);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(adaptador);
        registerForContextMenu(recView);

        public void onCreateContextMenu (ContextMenu menu, View v){
            super.onCreateContextMenu(menu, v, menuInfo);

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_ctx_etiqueta, menu);
        }


    }

    private void aniadirLista(String nombre, String Descripcion) {
        Random rand = new Random();
        Calendar calendario = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String currentDate = sdf.format(calendario.getTime());

        datosLista.add(new Lista(colorRandom(), nombre, Descripcion, currentDate));
        adaptador.notifyDataSetChanged();
    }

    public int colorRandom() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(150), random.nextInt(150), random.nextInt(150));
    }

    public void borrarLista(int numLista) {

        datosLista.remove(numLista);
        adaptador.notifyDataSetChanged();

    }
}