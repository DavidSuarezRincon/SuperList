package com.david.superlist.Activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;
import com.david.superlist.pojos.Lista;

// Esta actividad muestra la información detallada de una lista seleccionada
public class activity_mostrarInfo_Lista extends AppCompatActivity {

    // Declaración de variables de instancia
    private Lista list; // La lista seleccionada
    private TextView nameListaTextView, descriptionListaTextView, creationDateListaTextView, deadlineListaTextView,
            typeListaTextView, taksNumberListaTextView; // Los TextViews para mostrar la información de la lista
    private ImageButton goBack; // El botón para volver a la actividad anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info_lista);

        // Inicialización de las vistas
        nameListaTextView = findViewById(R.id.NombreListaInfoTextView);
        descriptionListaTextView = findViewById(R.id.DescripcionListaInfoTextView);
        creationDateListaTextView = findViewById(R.id.FechaCreacionListaInfoTextView);
        deadlineListaTextView = findViewById(R.id.FechaLimiteListaInfoTextView);
        typeListaTextView = findViewById(R.id.TipoListaInfoTextView);
        taksNumberListaTextView = findViewById(R.id.NumeroTareasListaInfoTextView);
        goBack = findViewById(R.id.BotonVolverMainInfo);

        // Establecimiento del listener del botón para volver
        goBack.setOnClickListener(v -> finish());

        // Obtención de la lista seleccionada del Intent
        list = getIntent().getParcelableExtra("listaSeleccionada");

        // Inicialización de los TextViews con la información de la lista
        initializeTextsViews();
    }

    // Método para inicializar los TextViews con la información de la lista
    private void initializeTextsViews() {
        String listName = list.getName();
        String listDescription = list.getDescription();
        String listCreationDate = list.getCreationDate();
        String listDeadline = list.getEndDate();
        String listType = list.getType();
        String listNumberTasks = String.valueOf(list.getTasksList().size());

        // Establecimiento de los textos de los TextViews
        nameListaTextView.setText(listName);
        descriptionListaTextView.setText(listDescription);
        creationDateListaTextView.setText(listCreationDate);
        deadlineListaTextView.setText(listDeadline);
        typeListaTextView.setText(listType);
        taksNumberListaTextView.setText(listNumberTasks);
    }
}