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

        initializeViews();
        setClickListeners();
        setupRecyclerView();
    }

    private void initializeViews() {
        addTaskButton = findViewById(R.id.botonAñadirItem);
        buttonAddListWithTasksToMain = findViewById(R.id.botonTerminarLista);
        imageButtonGoBack = findViewById(R.id.BotonVolverMainInfo);
        recyclerViewItems = findViewById(R.id.recyclerViewTareas);
    }

    private void setClickListeners() {
        addTaskButton.setOnClickListener(v -> createDialogAddTask());
        buttonAddListWithTasksToMain.setOnClickListener(v -> handleAddListToMain());
        imageButtonGoBack.setOnClickListener(v -> showWarningDialog());
    }

    private void setupRecyclerView() {
        tasks = getIntent().hasExtra("listaDeTareas") ? (ArrayList<TareaLista>) getIntent().getSerializableExtra("listaDeTareas") : new ArrayList<>();
        adapter = new AdaptadorItemsLista(this, tasks, this);
        recyclerViewItems.setAdapter(adapter);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
    }

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

    private void addListToMain() {
        Bundle listData = getIntent().getExtras();
        Lista newList = listData.getParcelable("newList");
        newList.setTasksList(tasks);
        MenuListasFragment.addLista(newList);
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.MensajeAdvertenciaVolverAtrasTareas));
        builder.setPositiveButton(getResources().getString(R.string.AceptarVolverAtrasTareas), (dialog, which) -> finish());
        builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createDialogAddTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String introduceDataMessage = getResources().getString(R.string.textDialogoAñadirTarea);
        builder.setMessage(introduceDataMessage);

        View selector = getLayoutInflater().inflate(R.layout.dialog_introducir_datos_items, null);
        builder.setView(selector);

        TextInputEditText inputStringTask = selector.findViewById(R.id.textInputTarea);
        Spinner spinnerTaskPriority = selector.findViewById(R.id.spinnerPrioridadLista);
        Button addTaskButton = selector.findViewById(R.id.botonAgregarTarea);

        builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
        AlertDialog dialog = builder.create();
        dialog.show();

        addTaskButton.setOnClickListener(v1 -> {
            String tasktext = inputStringTask.getText().toString();

            if (TextUtils.isEmpty(tasktext)) {
                String errorInputStringTaskMessage = getResources().getString(R.string.textoCampoObligatorio);
                inputStringTask.setError(errorInputStringTaskMessage);
                String addTaskToastTextError = getResources().getString(R.string.mensajeTareaAñadidaError);
                Toast.makeText(this, addTaskToastTextError, Toast.LENGTH_LONG).show();
                return;
            }

            String selectionPriority = spinnerTaskPriority.getSelectedItem().toString();
            addTask(tasktext, selectionPriority);
            adapter.notifyDataSetChanged();
            String addTaskToastTextSucefull = getResources().getString(R.string.mensajeTareaAñadidaExito);
            Toast.makeText(this, addTaskToastTextSucefull, Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });
    }

    private void addTask(String taskText, String priority) {
        TareaLista newTask = new TareaLista(taskText, priority);
        tasks.add(newTask);
    }

    @Override
    public void onItemClick(int position) {
        // TODO: Implementar lógica de clic en el elemento (si es necesario).
    }

    @Override
    public void onItemLongClick(int position) {
        PopupMenu popup = new PopupMenu(this, recyclerViewItems.getChildAt(position));
        popup.getMenuInflater().inflate(R.menu.activity_menu_tarea, popup.getMenu());
        popup.show();
        initializeOnClickListenerPopUp(position, popup);
    }

    private void initializeOnClickListenerPopUp(int position, PopupMenu popup) {
        popup.setOnMenuItemClickListener(item -> {
            deleteTask(position);
            return true;
        });
    }

    private void deleteTask(int numTask) {
        tasks.remove(numTask);
        adapter.notifyItemRemoved(numTask);
    }
}