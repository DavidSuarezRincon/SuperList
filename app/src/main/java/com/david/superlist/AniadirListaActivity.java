package com.david.superlist;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AniadirListaActivity extends AppCompatActivity {

    EditText nombre, descripcion;
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
        nombre = findViewById(R.id.NombreEditText);
        descripcion = findViewById(R.id.DescripcionEditText);


        botonAniadir = findViewById(R.id.botonAniadirListaActivity);
        botonAniadir.setOnClickListener(View -> {

            String elNombre = nombre.getText().toString();
            String laDescripcion = descripcion.getText().toString();

            if (TextUtils.isEmpty(elNombre.trim()) && !TextUtils.isEmpty(laDescripcion.trim())) {
                ponerError(nombre);
                ponerError(descripcion);
                return;

            }

            finish();

        });


    }

    private void ponerError(EditText et) {

        et.setError("Este campo es obligatorio.");

    }
}