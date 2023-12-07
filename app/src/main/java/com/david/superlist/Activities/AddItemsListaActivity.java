package com.david.superlist.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Adaptadores.AdaptadorItemsLista;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
import com.david.superlist.pojos.TareaLista;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddItemsListaActivity extends AppCompatActivity {

    RecyclerView recyclerViewItems;
    AdaptadorItemsLista adapter;
    ArrayList<TareaLista> tasks;
    Button addTaskButton;
    Button buttonAddListWithTasksToMain;
    ImageButton imageButtonGoBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_itemslista);

        addTaskButton = findViewById(R.id.botonAñadirItem);
        addTaskButton.setOnClickListener(v -> {
            createDialogAddTask();
        });

        buttonAddListWithTasksToMain = findViewById(R.id.botonTerminarLista);
        buttonAddListWithTasksToMain.setOnClickListener(v -> {


            if (getIntent().hasExtra("listaDeTareas")) {

                int positionTask = (int) getIntent().getExtras().getInt("posLista");

                MainActivity.cambiarTareasLista(positionTask, tasks);

            } else {
                addListToMain();
            }

            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
        });

        imageButtonGoBack = findViewById(R.id.BotonVolverAniadirTarea);
        imageButtonGoBack.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.MensajeAdvertenciaVolverAtrasTareas));
            //Texto: ¿Estás seguro que quieres volver atrás? ¡Las tareas añadidas se borrarán!
            builder.setPositiveButton(getResources().getString(R.string.AceptarVolverAtrasTareas), new DialogInterface.OnClickListener() {
                //Texto: Aceptar o Accept
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
            //Texto: Cancelar o Cancel
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        recyclerViewItems = findViewById(R.id.recyclerViewTareas);

        if (getIntent().hasExtra("listaDeTareas")) {
            tasks = (ArrayList<TareaLista>) getIntent().getSerializableExtra("listaDeTareas");
        } else {
            tasks = new ArrayList<>();
        }

//        addTarea("Esto es una prueba", "alta");
//        addTarea("Esto es una prueba 2", "baja");
//        addTarea("Esto es una prueba 2", "media");
        adapter = new AdaptadorItemsLista(this, tasks);
        recyclerViewItems.setAdapter(adapter);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addListToMain() {
        //Datos recibidos de la activity crear lista.
        Bundle listData = getIntent().getExtras();

        Lista newList = listData.getParcelable("newList");
        newList.setTasksList(tasks);

        MainActivity.addLista(newList);
    }

    private void createDialogAddTask() {
        // Abre un diálogo para crear una tarea.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String introduceDataMessage = getResources().getString(R.string.textDialogoAñadirTarea);
        builder.setMessage(introduceDataMessage);

        // Infla el diseño de la vista del diálogo.
        View selector = getLayoutInflater().inflate(R.layout.dialog_introducir_datos_items, null);
        builder.setView(selector);

        // Obtiene referencias a los elementos de la vista del diálogo.
        TextInputEditText inputStringTask = selector.findViewById(R.id.textInputTarea);
        Spinner spinnerTaskPriority = selector.findViewById(R.id.spinnerPrioridadLista);
        Button addTaskButton = selector.findViewById(R.id.botonAgregarTarea);

        // Configura el botón "Cancelar" para cerrar el diálogo sin hacer nada.
        String cancelDataMessage = getResources().getString(R.string.CancelarVolverAtrasTareas);
        builder.setNegativeButton(cancelDataMessage, null);

        // Crea el diálogo y lo muestra.
        AlertDialog dialog = builder.create();
        dialog.show();

        // Configura el botón "Agregar tarea" para agregar la tarea y cerrar el diálogo.
        addTaskButton.setOnClickListener(v1 -> {
            String tasktext = inputStringTask.getText().toString();

            if (TextUtils.isEmpty(tasktext)) {
                String errorInputStringTaskMessage = getResources().getString(R.string.textoCampoObligatorio);
                inputStringTask.setError(errorInputStringTaskMessage);
                return;
            }

            String selectionPriority = spinnerTaskPriority.getSelectedItem().toString();
            addTask(tasktext, selectionPriority);
            adapter.notifyDataSetChanged();
            dialog.dismiss(); // Cierra el dialogo.
        });
    }

    public void addTask(String taskText, String priority) {
        TareaLista newTask = new TareaLista(taskText, priority);
        tasks.add(newTask);
    }
}
