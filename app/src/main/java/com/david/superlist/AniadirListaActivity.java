package com.david.superlist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AniadirListaActivity extends AppCompatActivity {
    private ImageButton botonVolver;
    private EditText nombre, descripcion;
    private String elNombre, laDescripcion;
    private Spinner tipoListaSpinner;
    private Button botonDatePicker;
    private TextView ShowDateTextView;
    private DatePickerDialog datePickerDialog;
    private String fechaElegida;
    private Button botonAniadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista);


        botonVolver = findViewById(R.id.BotonVolverAniadirLista);
        botonVolver.setOnClickListener(v -> {
            finish();
        });

        nombre = findViewById(R.id.NombreEditText);
        descripcion = findViewById(R.id.DescripcionEditText);

        elNombre = nombre.getText().toString();
        laDescripcion = descripcion.getText().toString();

        tipoListaSpinner = findViewById(R.id.spinnerTipoLista);

        botonDatePicker = findViewById(R.id.botonAniadirFecha);
        ShowDateTextView = findViewById(R.id.SelectedDaytextView);
        iniciarDatePicker();
        botonDatePicker.setOnClickListener(v -> {
            datePickerDialog.show();
        });


        botonAniadir = findViewById(R.id.botonAniadirListaActivity);
        botonAniadir.setOnClickListener(View -> {
            Boolean thereIsAnError = false;

            if (TextUtils.isEmpty(elNombre.trim())) {
                thereIsAnError = true;
                ponerError(nombre);
            }
            if (TextUtils.isEmpty(laDescripcion.trim())) {
                thereIsAnError = true;
                ponerError(descripcion);
            }
            if (ShowDateTextView.getText().equals("Fecha límite")) {
                thereIsAnError = true;
                ShowDateTextView.setError("Elige la fecha");
            }
            if (thereIsAnError) {
                return;
            }

            finish();
        });
    }

    private void iniciarDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                fechaElegida = dayOfMonth + "/" + month + "/" + year;
                ShowDateTextView.setText(fechaElegida);
            }
        };

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        //int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private void ponerError(EditText et) {
        et.setError("Este campo es obligatorio.");
    }
}