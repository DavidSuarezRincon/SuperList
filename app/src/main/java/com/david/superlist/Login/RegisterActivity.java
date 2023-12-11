package com.david.superlist.Login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.david.superlist.R;
import com.david.superlist.pojos.UsuariosRegistrados;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    ImageButton goBack;
    TextInputEditText registerEmailEditText, firstPasswordEditText, secondPasswordEditText;
    Button buttonRegister;
    ConstraintLayout parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmailEditText = findViewById(R.id.RegisterInputEmail);
        firstPasswordEditText = findViewById(R.id.RegisterInputFirstPassword);
        secondPasswordEditText = findViewById(R.id.RegisterInputSecondPassword);
        goBack = findViewById(R.id.imgButtonGoBackRegister);
        buttonRegister = findViewById(R.id.buttonRegister);
        parentLayout = findViewById(R.id.ContainerRegisterActivity);

        buttonRegister.setOnClickListener(v -> {
            String email = registerEmailEditText.getText().toString();
            String firstPasswordInput = firstPasswordEditText.getText().toString();
            String secondPasswordInput = secondPasswordEditText.getText().toString();

            if (!checkRegisterErrors(email, firstPasswordInput, secondPasswordInput)) {
                UsuariosRegistrados.addNormalUser(email, firstPasswordInput);
                finish();
            }
        });

        goBack.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }


    private boolean checkRegisterErrors(String email, String firstPasswordInput, String secondPasswordInput) {
        boolean thereIsAnError = false;

        if (TextUtils.isEmpty(email)) {
            setEmptyFieldError(registerEmailEditText);
            thereIsAnError = true;
        } else if (!checkEmail(email)) {
            setWrongEmailFormatError(registerEmailEditText);
            thereIsAnError = true;
        }

        if (UsuariosRegistrados.existUser(email)) {
            setUserAlreadyExistsError(registerEmailEditText);
            thereIsAnError = true;
        }

        if (TextUtils.isEmpty(firstPasswordInput)) {
            setEmptyFieldError(firstPasswordEditText);
            thereIsAnError = true;
        }
        if (TextUtils.isEmpty(secondPasswordInput)) {
            setEmptyFieldError(secondPasswordEditText);
            thereIsAnError = true;
        }

        if (!firstPasswordInput.equals(secondPasswordInput)) {
            setNotMatchingPasswordsError(secondPasswordEditText);
            thereIsAnError = true;
        }

        return thereIsAnError;
    }

    private void setEmptyFieldError(EditText et) {
        String EmptyFieldErrorMessage = getResources().getString(R.string.textoCampoObligatorio);
        et.setError(EmptyFieldErrorMessage);
    }

    private boolean checkEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void setWrongEmailFormatError(EditText et) {
        String wrongEmailFormatMessage = getResources().getString(R.string.errorDeFormatoDeEmail);
        et.setError(wrongEmailFormatMessage);
    }

    private void setNotMatchingPasswordsError(EditText et) {
        String notMatchingPasswordsErrorMessage = getResources().getString(R.string.mensajeErrorContraseñasNoCoinciden);
        et.setError(notMatchingPasswordsErrorMessage);
    }

    private void setUserAlreadyExistsError(EditText et) {
        String userAlreadyExistsError = getResources().getString(R.string.textoErrorUsuarioExistente);
        et.setError(userAlreadyExistsError);
    }
}