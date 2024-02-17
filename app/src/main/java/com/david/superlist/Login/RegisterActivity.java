package com.david.superlist.Login;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.david.superlist.NavigationDrawer.MainActivity;
import com.david.superlist.R;
import com.david.superlist.pojos.Lista;
import com.david.superlist.pojos.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

// Esta actividad permite al usuario registrarse en la aplicación
public class RegisterActivity extends AppCompatActivity {

    // Declaración de variables de instancia
    ImageButton goBack;
    TextInputEditText registerEmailEditText, firstPasswordEditText, secondPasswordEditText;
    Button buttonRegister;
    ConstraintLayout parentLayout;
    FirebaseAuth registermAuth;
    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.contexto = this.getApplicationContext();

        // Inicialización de FirebaseAuth
        registermAuth = LoginActivity.mAuth;

        // Inicialización de las vistas
        registerEmailEditText = findViewById(R.id.RegisterInputEmail);
        firstPasswordEditText = findViewById(R.id.RegisterInputFirstPassword);
        secondPasswordEditText = findViewById(R.id.RegisterInputSecondPassword);
        goBack = findViewById(R.id.imgButtonGoBackRegister);
        buttonRegister = findViewById(R.id.buttonRegister);
        parentLayout = findViewById(R.id.ContainerRegisterActivity);

        // Establecimiento de los listeners de los clics
        buttonRegister.setOnClickListener(v -> {
            String email = Objects.requireNonNull(registerEmailEditText.getText()).toString();
            String firstPasswordInput = Objects.requireNonNull(firstPasswordEditText.getText()).toString();
            String secondPasswordInput = Objects.requireNonNull(secondPasswordEditText.getText()).toString();

            // Verificación de los errores de registro
            if (!checkRegisterErrors(email, firstPasswordInput, secondPasswordInput)) {
                // Creación del usuario en Firebase
                crearUsuarioFirebase(email, firstPasswordInput);
            }
        });

        goBack.setOnClickListener(view -> {
            // Finalización de la actividad y transición de animación
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    // Método para verificar los errores de registro
    private boolean checkRegisterErrors(String email, String firstPasswordInput, String secondPasswordInput) {
        boolean thereIsAnError = false;

        // Verificación de los campos de entrada
        if (TextUtils.isEmpty(email)) {
            setEmptyFieldError(registerEmailEditText);
            thereIsAnError = true;
        } else if (!checkEmail(email)) {
            setWrongEmailFormatError(registerEmailEditText);
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

        if (firstPasswordInput.length() < 6) {
            setPasswordSoShotError(firstPasswordEditText);
            thereIsAnError = true;
        }

        return thereIsAnError;
    }

    // Método para mostrar un error cuando un campo está vacío
    private void setEmptyFieldError(EditText et) {
        String EmptyFieldErrorMessage = getResources().getString(R.string.textoCampoObligatorio);
        et.setError(EmptyFieldErrorMessage);
    }

    // Método para verificar el formato del email
    private boolean checkEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Método para mostrar un error cuando el formato del email es incorrecto
    private void setWrongEmailFormatError(EditText et) {
        String wrongEmailFormatMessage = getResources().getString(R.string.errorDeFormatoDeEmail);
        et.setError(wrongEmailFormatMessage);
    }

    // Método para mostrar un error cuando las contraseñas no coinciden
    private void setNotMatchingPasswordsError(EditText et) {
        String notMatchingPasswordsErrorMessage = getResources().getString(R.string.mensajeErrorContraseñasNoCoinciden);
        et.setError(notMatchingPasswordsErrorMessage);
    }

    // Método para mostrar un error cuando la contraseña es demasiado corta
    private void setPasswordSoShotError(EditText et) {
        String notMatchingPasswordsErrorMessage = "La contraseña debe contener al menos 6 caracteres";
        et.setError(notMatchingPasswordsErrorMessage);
    }

    // Método para crear un usuario en Firebase
    private void crearUsuarioFirebase(String email, String password) {
        registermAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Si el registro es exitoso, actualiza la interfaz de usuario con la información del usuario
                        Log.d(TAG, "createUserWithEmail:success");
                        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.textoSeRegistroCorrectamente),
                                Toast.LENGTH_SHORT).show();
                        FirebaseUser user = registermAuth.getCurrentUser();

                        // Crear un nuevo registro en la base de datos
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                        ArrayList<Lista> listas = new ArrayList<>();
                        listas.add(Lista.nuevaListaDefault(contexto));

                        assert user != null;
                        Usuario nuevoUsuario = new Usuario(user.getUid(), 0, listas, email.split("@")[0], email, false); // 0 para rol de usuario, ArrayList vacío para las listas
                        database.child("SuperList").child(user.getUid()).setValue(nuevoUsuario);

                        updateUI(user);
                    } else {
                        // Si el registro falla, muestra un mensaje al usuario
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }
                });
    }

    // Método para actualizar la interfaz de usuario
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Si el usuario está registrado, redirige a la actividad principal
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Esto cierra la RegisterActivity
        } else {
            // Si el registro falla, limpia los campos de texto
            registerEmailEditText.setText("");
            firstPasswordEditText.setText("");
            secondPasswordEditText.setText("");
        }
    }
}