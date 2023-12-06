package com.david.superlist.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;

import java.util.Calendar;

public class AniadirListaActivity extends AppCompatActivity {
    private ImageButton buttonGoBack;
    private EditText txtName, txtDescription;
    private String name, description, endDate, listType;
    private Spinner typeListSpinner;
    private Button datePickerButton;
    private TextView ShowDateTextView;
    private DatePickerDialog datePickerDialog;
    private String selectedDate;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirlista_descripcion);

        buttonGoBack = findViewById(R.id.BotonVolverAniadirTarea);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }

    private void checkAndContinueLista() {
        boolean thereIsAnError = false;
        name = txtName.getText().toString();
        description = txtDescription.getText().toString();
        endDate = ShowDateTextView.getText().toString();
        listType = (String) typeListSpinner.getSelectedItem();
        System.out.println(endDate);
        System.out.println(listType);

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

        Intent intent = new Intent(this, AddItemsListaActivity.class);

        Bundle toSendData = new Bundle();
        toSendData.putString("nombreLista", name);
        toSendData.putString("descripcionLista", description);
        toSendData.putString("fechaLimiteLista", endDate);
        toSendData.putString("tipoLista", listType);

        intent.putExtras(toSendData);
        startActivityForResult(intent, 2);
    }

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

    private void ponerError(EditText et) {
        et.setError(getResources().getString(R.string.textoCampoObligatorio));
    }


}