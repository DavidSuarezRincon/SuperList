package com.david.superlist.Activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;
import com.david.superlist.pojos.Lista;

public class activity_mostrarInfo_Lista extends AppCompatActivity {

    private Lista list;
    private TextView nameListaTextView, descriptionListaTextView, creationDateListaTextView, deadlineListaTextView,
            typeListaTextView, taksNumberListaTextView;
    private ImageButton goBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_info_lista);

        nameListaTextView = findViewById(R.id.NombreListaInfoTextView);
        descriptionListaTextView = findViewById(R.id.DescripcionListaInfoTextView);
        creationDateListaTextView = findViewById(R.id.FechaCreacionListaInfoTextView);
        deadlineListaTextView = findViewById(R.id.FechaLimiteListaInfoTextView);
        typeListaTextView = findViewById(R.id.TipoListaInfoTextView);
        taksNumberListaTextView = findViewById(R.id.NumeroTareasListaInfoTextView);
        goBack = findViewById(R.id.BotonVolverMainInfo);

        goBack.setOnClickListener(v -> finish());
        
        list = getIntent().getParcelableExtra("listaSeleccionada");

        initializeTextsViews();
    }

    private void initializeTextsViews() {
        String listName = list.getName();
        String listDescription = list.getDescription();
        String listCreationDate = list.getCreationDate();
        String listDeadline = list.getEndDate();
        String listType = list.getType();
        String listNumberTasks = String.valueOf(list.getTasksList().size());

        nameListaTextView.setText(listName);
        descriptionListaTextView.setText(listDescription);
        creationDateListaTextView.setText(listCreationDate);
        deadlineListaTextView.setText(listDeadline);
        typeListaTextView.setText(listType);
        taksNumberListaTextView.setText(listNumberTasks);
    }
}