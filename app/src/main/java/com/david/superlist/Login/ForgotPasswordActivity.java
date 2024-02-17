package com.david.superlist.Login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        TextInputEditText email = findViewById(R.id.txtEmailForgotPassword);
        TextInputEditText secondEmail = findViewById(R.id.txtSecondEmailForgotPassword);

        ImageButton botonBack = findViewById(R.id.imgButtonGoBackForgotPassword);
        Button btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);

        btnEnviarCorreo.setOnClickListener(view -> {
            // Enviar correo de recuperación de contraseña

            if (!checkEmail(Objects.requireNonNull(email.getText()).toString())) {
                setError("El formato del email no es el correcto.", email);
                return;
            }

            if (!email.getText().toString().equals(Objects.requireNonNull(secondEmail.getText()).toString())) {
                setError("Los emails no coinciden.", secondEmail);
                return;
            }


            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email.getText().toString(); // reemplaza esto con el correo electrónico del usuario

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // El correo electrónico se envió correctamente
                            Toast.makeText(this, getResources().getString(R.string.textoRecuperarContraseñaEnvioCorrecto), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Hubo un error al enviar el correo electrónico
                            Toast.makeText(this, getResources().getString(R.string.textoRecuperarContraseñaEnvioIncorrecto), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        botonBack.setOnClickListener(view -> {
            // Regresar a la pantalla de inicio de sesión
            finish();
        });
    }

    private boolean checkEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private void setError(String error, TextInputEditText editText) {
        // Mostrar mensaje de error
        editText.setError(error);
    }
}