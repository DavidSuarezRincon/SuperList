package com.david.superlist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddItemsListaActivity extends AppCompatActivity {

    RecyclerView recyclerViewItems;
    AdaptadorItemsLista adaptador;
    ArrayList<TareaLista> tareas;
    Button addTareaButton;
    Button botonFinalizarAniadirTareas;
    ImageButton imageButtonVolverAtras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_itemslista);

        addTareaButton = findViewById(R.id.botonAñadirItem);
        addTareaButton.setOnClickListener(v -> {
            createDialogAddTask();
        });

        botonFinalizarAniadirTareas = findViewById(R.id.botonTerminarLista);
        botonFinalizarAniadirTareas.setOnClickListener(v -> {

            MainActivity.aniadirLista("nombre", "descripcion", "fechafin", "tipo", tareas);


        });

        imageButtonVolverAtras = findViewById(R.id.BotonVolverAniadirTarea);
        imageButtonVolverAtras.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que quieres volver atrás? ¡Las tareas añadidas se borrarán!");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("Cancelar", null);
            AlertDialog dialog = builder.create();
            dialog.show();


        });


        recyclerViewItems = findViewById(R.id.recyclerViewTareas);
        tareas = new ArrayList<>();
        addTarea("Esto es una prueba", "alta");
        addTarea("Esto es una prueba 2", "baja");
        addTarea("Esto es una prueba 2", "media");
        adaptador = new AdaptadorItemsLista(this, tareas);
        recyclerViewItems.setAdapter(adaptador);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createDialogAddTask() {
        // Abre un diálogo para crear una tarea.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Introduzca la tarea");

        // Infla el diseño de la vista del diálogo.
        View selector = getLayoutInflater().inflate(R.layout.dialog_introducir_datos_items, null);
        builder.setView(selector);

        // Obtiene referencias a los elementos de la vista del diálogo.
        TextInputEditText inputTarea = selector.findViewById(R.id.textInputTarea);
        Spinner spinnerPrioridadTarea = selector.findViewById(R.id.spinnerPrioridadLista);
        Button botonAgregarTarea = selector.findViewById(R.id.botonAgregarTarea);

        // Configura el botón "Cancelar" para cerrar el diálogo sin hacer nada.
        builder.setNegativeButton("Cancelar", null);

        // Crea el diálogo y lo muestra.
        AlertDialog dialog = builder.create();
        dialog.show();

        // Configura el botón "Agregar tarea" para agregar la tarea y cerrar el diálogo.
        botonAgregarTarea.setOnClickListener(v1 -> {
            String textoTarea = inputTarea.getText().toString();
            String prioridadSeleccionada = spinnerPrioridadTarea.getSelectedItem().toString();
            addTarea(textoTarea, prioridadSeleccionada);
            adaptador.notifyDataSetChanged();
            dialog.dismiss(); // Agrega esta línea para cerrar el diálogo.
        });
    }

    public void addTarea(String tarea, String prioridad) {
        TareaLista nuevaTarea = new TareaLista(tarea, prioridad);
        tareas.add(nuevaTarea);
    }
}
