package com.david.superlist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddItemsListaActivity extends AppCompatActivity {

    RecyclerView recyclerViewItems;
    AdaptadorItemsLista adaptador;
    ArrayList<TareaLista> tareas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_itemslista);

        recyclerViewItems = findViewById(R.id.recyclerViewTareas);
        tareas = new ArrayList<>();
        addTarea("Esto es una prueba", "alta");
        addTarea("Esto es una prueba 2", "baja");
        addTarea("Esto es una prueba 2", "media");
        adaptador = new AdaptadorItemsLista(this, tareas);
        recyclerViewItems.setAdapter(adaptador);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addTarea(String tarea, String prioridad) {
        TareaLista nuevaTarea = new TareaLista(tarea, prioridad);
        tareas.add(nuevaTarea);
    }
}
