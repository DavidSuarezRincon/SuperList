package com.david.superlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AniadirListaActivity extends AppCompatActivity {

    TextView nombre;
    TextView descripcion;
    Button botonAniadir;
    ImageButton botonVolver;

    Class<?> claseMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista);

        claseMain = (Class<?>) getIntent().getClass();

        botonVolver = findViewById(R.id.BotonVolverAniadirLista);
        botonVolver.setOnClickListener(v -> {
            finish();
        });
        //nombre = findViewById(R.id.editTextNombreLista);
        //descripcion = findViewById(R.id.editTextDescripcionLista);


        botonAniadir = findViewById(R.id.botonAniadirListaActivity);
        botonAniadir.setOnClickListener(View -> {

            String elNombre = nombre.getText().toString();
            String laDescripcion = descripcion.getText().toString();

            if (!TextUtils.isEmpty(elNombre.trim()) && !TextUtils.isEmpty(laDescripcion.trim())) {
                finish();
            }


        });
    }
}