package com.david.superlist.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.NavigationDrawer.MenuListas.MenuListasFragment;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;

import java.util.Calendar;

// Clase para la actividad de añadir una lista
public class AniadirListaActivity extends AppCompatActivity {
    // Declaración de variables de instancia
    private ImageButton buttonGoBack;
    private EditText txtName, txtDescription;
    private String name, description, endDate, listType;
    private Spinner typeListSpinner;
    private Button datePickerButton;
    private TextView ShowDateTextView;
    private DatePickerDialog datePickerDialog;
    private String selectedDate;
    private Button addButton;

    // Método onCreate que se llama al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_descripcion);

        // Inicialización de las vistas
        buttonGoBack = findViewById(R.id.BotonVolverMainInfo);
        buttonGoBack.setOnClickListener(v -> {
            finish();
        });

        txtName = findViewById(R.id.NombreEditText);
        txtDescription = findViewById(R.id.DescripcionEditText);

        typeListSpinner = findViewById(R.id.spinnerTipoLista);

        datePickerButton = findViewById(R.id.botonAniadirFecha);
        ShowDateTextView = findViewById(R.id.SelectedDaytextView);
        startDatePicker();
        datePickerButton.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        addButton = findViewById(R.id.botonAniadirListaActivity);
        addButton.setOnClickListener(View -> {
            checkAndContinueLista();
        });
    }

    // Método que se llama cuando se devuelve un resultado de una actividad
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    // Método para verificar y continuar con la lista
    private void checkAndContinueLista() {
        boolean thereIsAnError = false;
        name = txtName.getText().toString();
        description = txtDescription.getText().toString();
        endDate = ShowDateTextView.getText().toString();
        listType = (String) typeListSpinner.getSelectedItem();
        Log.i("textoSeleccionadoSpinner", listType);
        System.out.println(endDate);
        System.out.println(listType);

        // Verificación de los campos de entrada
        if (TextUtils.isEmpty(name.trim())) {
            thereIsAnError = true;
            ponerError(txtName);
        }
        if (TextUtils.isEmpty(description.trim())) {
            thereIsAnError = true;
            ponerError(txtDescription);
        }
        if (endDate.equalsIgnoreCase(getResources().getString(R.string.textoFechaLimiteAddLista))) {
            thereIsAnError = true;
            ShowDateTextView.setError(getResources().getString(R.string.errorEleccionFecha));
        } else {
            ShowDateTextView.setError(null);
        }

        if (thereIsAnError) {
            return;
        }

        // Creación de la intención para la actividad AddItemsListaActivity
        Intent intent = new Intent(this, AddItemsListaActivity.class);
        Lista newLista = MenuListasFragment.createLista(name, description, endDate, listType, null);
        intent.putExtra("newList", newLista);
        startActivityForResult(intent, 2);
    }

    // Método para iniciar el selector de fecha
    private void startDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                ShowDateTextView.setText(selectedDate);
            }
        };

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    // Método para establecer un error en un EditText
    private void ponerError(EditText et) {
        et.setError(getResources().getString(R.string.textoCampoObligatorio));
    }
}