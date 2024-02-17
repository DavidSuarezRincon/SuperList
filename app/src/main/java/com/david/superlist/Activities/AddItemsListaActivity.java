package com.david.superlist.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AddItemsListaActivity extends AppCompatActivity implements RecyclerViewInterface {

    // Declaración de variables de instancia
    private RecyclerView recyclerViewItems;
    private AdaptadorItemsLista adapter;
    private ArrayList<TareaLista> tasks;
    private Button addTaskButton;
    private Button buttonEnd;
    private ImageButton imageButtonGoBack;
    private ImageButton download_pdf_button;
    private static final int WRITE_REQUEST_CODE = 1;

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
        buttonEnd = findViewById(R.id.botonTerminarLista);
        imageButtonGoBack = findViewById(R.id.BotonVolverMainInfo);
        recyclerViewItems = findViewById(R.id.recyclerViewTareas);
        download_pdf_button = findViewById(R.id.download_pdf_button);
    }

    // Método para establecer los listeners de los clics
    private void setClickListeners() {
        addTaskButton.setOnClickListener(v -> createDialogAddTask());
        buttonEnd.setOnClickListener(v -> handleAddListToMain());
        imageButtonGoBack.setOnClickListener(v -> showWarningDialog());

        download_pdf_button.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_TITLE, "tareas.pdf");

            startActivityForResult(intent, WRITE_REQUEST_CODE);
        });
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
            int positionTask = Objects.requireNonNull(getIntent().getExtras()).getInt("posLista");
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
        assert listData != null;
        Lista newList = listData.getParcelable("newList");

        assert newList != null;
        newList.setTasksList(tasks);
        MenuListasFragment.addLista(newList);
    }

    // Método para mostrar un diálogo de advertencia
    private void showWarningDialog() {
        if (!getIntent().hasExtra("listaDeTareas")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.MensajeAdvertenciaVolverAtrasTareas));
            builder.setPositiveButton(getResources().getString(R.string.AceptarVolverAtrasTareas), (dialog, which) -> finish());
            builder.setNegativeButton(getResources().getString(R.string.CancelarVolverAtrasTareas), null);
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {
            finish();
        }
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
            String tasktext = Objects.requireNonNull(inputStringTask.getText()).toString();

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
        if (getIntent().hasExtra("listaDeTareas")) {
            int positionTask = Objects.requireNonNull(getIntent().getExtras()).getInt("posLista");
            MenuListasFragment.changeTasks(positionTask, tasks);
        }
    }

    // Método que se llama cuando se hace clic en un elemento del RecyclerView
    @Override
    public void onItemClick(int position) {
    }

    // Método que se llama cuando se hace un clic largo en un elemento del RecyclerView
    @Override
    public void onItemLongClick(int position) {
        // Obtener el ViewHolder para la posición
        RecyclerView.ViewHolder viewHolder = recyclerViewItems.findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            // Obtener la vista del ViewHolder
            View view = viewHolder.itemView;

            // Crear y mostrar el PopupMenu
            PopupMenu popup = new PopupMenu(this, view);
            popup.getMenuInflater().inflate(R.menu.activity_menu_tarea, popup.getMenu());
            popup.show();
            initializeOnClickListenerPopUp(position, popup);
        }
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

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private void createPdfFromDataList(ArrayList<TareaLista> tasks, Uri uri, String title) {
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
            assert pfd != null;
            FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());

            // Crear un documento
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();

            // Agregar el título al documento
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 50, Font.BOLD);
            Paragraph titleParagraph = new Paragraph(title, titleFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);

            // Crear un ViewGroup temporal
            ViewGroup tempViewGroup = new FrameLayout(this);

            // Agregar las tareas al documento
            for (TareaLista task : tasks) {
                // Inflar la vista de la tarea
                View taskView = inflateTaskView(task);

                // Añadir la vista al ViewGroup temporal
                tempViewGroup.addView(taskView);

                // Medir y dibujar la vista
                taskView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                taskView.layout(0, 0, taskView.getMeasuredWidth(), taskView.getMeasuredHeight());

                // Forzar un dibujo de la vista
                taskView.setDrawingCacheEnabled(true);
                taskView.buildDrawingCache();
                Bitmap bitmap = getBitmapFromView(taskView);

                // Convertir la imagen en un objeto Image de iText
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());

                // Cambiar el tamaño de la imagen
                image.scaleAbsolute(530f, 130f); // Cambia el tamaño de la imagen a 50x50

                // Agregar la imagen al documento
                document.add(image);

                // Eliminar la vista del ViewGroup temporal
                tempViewGroup.removeView(taskView);

                // Limpiar el cache de dibujo
                taskView.setDrawingCacheEnabled(false);
            }

            document.close();
            fos.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }


    private View inflateTaskView(TareaLista task) {
        // Inflar el layout XML de la tarea
        LayoutInflater inflater = LayoutInflater.from(this);
        View taskView = inflater.inflate(R.layout.activity_items_lista, null, false);

        // Configurar los datos de la tarea en la vista
        CheckBox checkBox = taskView.findViewById(R.id.checkBoxTarea);
        StringBuilder sf = new StringBuilder(task.getTask());
        sf.setLength(40);
        checkBox.setText(sf.toString());

        // Configurar el color del icono de la tarea
        ImageView imageView = taskView.findViewById(R.id.imagenTarea);
        int color = getColorInt(task);
        imageView.setColorFilter(color);

        // Medir y dibujar la vista
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        taskView.measure(widthMeasureSpec, heightMeasureSpec);
        taskView.layout(0, 0, taskView.getMeasuredWidth(), taskView.getMeasuredHeight());

        return taskView;
    }

    private int getColorInt(TareaLista tarea) {
        String prioridad = tarea.getPriority().toLowerCase();



        String prioridadAltaTexto = getResources().getString(R.string.textoPrioridadAlta).toLowerCase();
        String prioridadMediaTexto = getResources().getString(R.string.textoPrioridadMedia).toLowerCase();
        String prioridadBajaTexto = getResources().getString(R.string.textoPrioridadBaja).toLowerCase();

        if (prioridad.equals(prioridadAltaTexto)) {
            return Color.rgb(255, 0, 0); // Rojo
        }
        if (prioridad.equals(prioridadMediaTexto)) {
            return Color.rgb(255, 255, 0); // Amarillo
        }
        if (prioridad.equals(prioridadBajaTexto)) {
            return Color.rgb(0, 255, 0); // Verde
        }

        return Color.WHITE;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();

                String nombreLista = "";
                if (bundle != null) {
                    nombreLista = bundle.getString("nombreLista");
                }

                createPdfFromDataList(tasks, uri, nombreLista);
            }
        }
    }
}