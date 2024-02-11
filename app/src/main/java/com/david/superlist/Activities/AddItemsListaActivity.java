package com.david.superlist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.david.superlist.Adaptadores.AdaptadorItemsLista;
import com.david.superlist.Interfaces.RecyclerViewInterface;
import com.david.superlist.NavigationDrawer.MenuListas.MenuListasFragment;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
import com.david.superlist.pojos.TareaLista;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddItemsListaActivity extends AppCompatActivity implements RecyclerViewInterface {

    // Declaración de variables de instancia
    private RecyclerView recyclerViewItems;
    private AdaptadorItemsLista adapter;
    private ArrayList<TareaLista> tasks;
    private Button addTaskButton;
    private Button buttonAddListWithTasksToMain;
    private ImageButton imageButtonGoBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_itemslista);

        // Inicialización de las vistas
        initializeViews();
        // Establecimiento de los listeners de los clics
        setClickListeners();
        // Configuración del RecyclerView
        setupRecyclerView();
    }

    // Método para inicializar las vistas
    private void initializeViews() {
        addTaskButton = findViewById(R.id.botonAñadirItem);
        buttonAddListWithTasksToMain = findViewById(R.id.botonTerminarLista);
        imageButtonGoBack = findViewById(R.id.BotonVolverMainInfo);
        recyclerViewItems = findViewById(R.id.recyclerViewTareas);
    }

    // Método para establecer los listeners de los clics
    private void setClickListeners() {
        addTaskButton.setOnClickListener(v -> createDialogAddTask());
        buttonAddListWithTasksToMain.setOnClickListener(v -> handleAddListToMain());
        imageButtonGoBack.setOnClickListener(v -> showWarningDialog());
    }

    // Método para configurar el RecyclerView
    private void setupRecyclerView() {
        tasks = (getIntent().hasExtra("listaDeTareas"))
                ? (ArrayList<TareaLista>) getIntent().getSerializableExtra("listaDeTareas")
                : new ArrayList<>();

        if (tasks == null) {
            //tasks será nullo cuando no hayan tareas porque firebase borra el nodo padre
            // cuando no hay nada dentro de él. Pero si llega un intent pero es nullo.
            //Por ello se pregunta si es nulo para instanciarlo.

            tasks = new ArrayList<>();
        }

        adapter = new AdaptadorItemsLista(this, tasks, this);
        recyclerViewItems.setAdapter(adapter);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

    // Método para manejar la adición de la lista a la actividad principal
    private void handleAddListToMain() {
        if (getIntent().hasExtra("listaDeTareas")) {
            int positionTask = getIntent().getExtras().getInt("posLista");
            MenuListasFragment.changeTasks(positionTask, tasks);
        } else {
            addListToMain();
        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    // Método para añadir una nueva lista a MenuListasFragment
    private void addListToMain() {
        Bundle listData = getIntent().getExtras();
        Lista newList = listData.getParcelable("newList");

        newList.setTasksList(tasks);
        MenuListasFragment.addLista(newList);

    }

    // Método para mostrar un diálogo de advertencia
    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.MensajeAdvertenciaVolverAtrasTareas));
        builder.setPositiveButton(getResources().getString(R.string.AceptarVolverAtrasTareas), (dialog, which) -> finish());
        builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Método para crear un diálogo para añadir una nueva tarea
    private void createDialogAddTask() {
        // Crear un nuevo AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Obtener el mensaje a mostrar en el diálogo
        String introduceDataMessage = getResources().getString(R.string.textDialogoAñadirTarea);
        // Establecer el mensaje del diálogo
        builder.setMessage(introduceDataMessage);

        // Inflar la vista del diálogo
        View selector = getLayoutInflater().inflate(R.layout.dialog_introducir_datos_items, null);
        // Establecer la vista del diálogo
        builder.setView(selector);

        // Obtener las referencias a los elementos de la vista
        TextInputEditText inputStringTask = selector.findViewById(R.id.textInputTarea);
        Spinner spinnerTaskPriority = selector.findViewById(R.id.spinnerPrioridadLista);
        Button addTaskButton = selector.findViewById(R.id.botonAgregarTarea);

        // Establecer el botón negativo del diálogo
        builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
        // Crear el diálogo
        AlertDialog dialog = builder.create();
        // Mostrar el diálogo
        dialog.show();

        // Establecer el listener del botón para agregar tarea
        addTaskButton.setOnClickListener(v1 -> {
            // Obtener el texto de la tarea
            String tasktext = inputStringTask.getText().toString();

            // Verificar si el texto de la tarea está vacío
            if (TextUtils.isEmpty(tasktext)) {
                // Mostrar un mensaje de error
                String errorInputStringTaskMessage = getResources().getString(R.string.textoCampoObligatorio);
                inputStringTask.setError(errorInputStringTaskMessage);
                String addTaskToastTextError = getResources().getString(R.string.mensajeTareaAñadidaError);
                Toast.makeText(this, addTaskToastTextError, Toast.LENGTH_LONG).show();
                return;
            }

            // Obtener la prioridad seleccionada
            String selectionPriority = spinnerTaskPriority.getSelectedItem().toString();
            // Añadir la tarea
            addTask(tasktext, selectionPriority);
            // Mostrar un mensaje de éxito
            String addTaskToastTextSucefull = getResources().getString(R.string.mensajeTareaAñadidaExito);
            Toast.makeText(this, addTaskToastTextSucefull, Toast.LENGTH_LONG).show();
            // Descartar el diálogo
            dialog.dismiss();
        });
    }

    // Método para añadir una nueva tarea a la lista de tareas
    private void addTask(String taskText, String priority) {
        TareaLista newTask = new TareaLista(taskText, priority);
        tasks.add(newTask);
        adapter.notifyDataSetChanged();
    }

    // Método que se llama cuando se hace clic en un elemento del RecyclerView
    @Override
    public void onItemClick(int position) {
    }

    // Método que se llama cuando se hace un clic largo en un elemento del RecyclerView
    @Override
    public void onItemLongClick(int position) {
        PopupMenu popup = new PopupMenu(this, recyclerViewItems.getChildAt(position));
        popup.getMenuInflater().inflate(R.menu.activity_menu_tarea, popup.getMenu());
        popup.show();
        initializeOnClickListenerPopUp(position, popup);
    }

    // Método para inicializar el listener de clics para el menú emergente
    private void initializeOnClickListenerPopUp(int position, PopupMenu popup) {
        popup.setOnMenuItemClickListener(item -> {
            deleteTask(position);
            return true;
        });
    }

    // Método para eliminar una tarea de la lista de tareas
    private void deleteTask(int numTask) {
        tasks.remove(numTask);
        adapter.notifyItemRemoved(numTask);
    }
}