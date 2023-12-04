package com.david.superlist.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.Activities.MainActivity;
import com.david.superlist.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referencias a los elementos de la interfaz de usuario
        TextView forgotPassword = findViewById(R.id.loginForgotPassword);
        TextView register = findViewById(R.id.loginRegister);
        EditText introducirEmail = findViewById(R.id.userEmailData);
        EditText introducirPassword = findViewById(R.id.userDataPassword);
        Button botonSignIn = findViewById(R.id.buttonLogIn);

        // Establece un escuchador de clics en el botón de inicio de sesión
        botonSignIn.setOnClickListener(v -> {

            // Obtiene el email y la contraseña introducidos por el usuario
            String email = String.valueOf(introducirEmail.getText());
            String password = String.valueOf(introducirPassword.getText());

            // Comprueba si el email está vacío
            if (TextUtils.isEmpty(email)) {
                setFalloDatos(introducirEmail);

                // Si la contraseña no está vacía, retorna
                if (!TextUtils.isEmpty(password)) {
                    return;
                }
            }

            // Comprueba si la contraseña está vacía
            if (TextUtils.isEmpty(password)) {
                setFalloDatos(introducirPassword);
                return;
            }

            // Si el email y la contraseña son "root", inicia la actividad principal
            if (email.equals("root") && password.equals("root")) {

                Intent claseMain = new Intent(this, MainActivity.class);

                // Añade el nombre de usuario al intent
                getIntent().putExtra("nombreUsuario", email.split("@")[0]);

                // Inicia la actividad principal
                startActivity(claseMain);

            }
        });

        // Establece un escuchador de clics en el texto de "olvidé mi contraseña"
        forgotPassword.setOnClickListener(view -> {
            // Inicia la actividad de recuperación de contraseña
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        // Establece un escuchador de clics en el texto de "registrarse"
        register.setOnClickListener(view ->
                // Inicia la actividad de registro
                startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    // Método para mostrar un error cuando un campo está vacío
    private void setFalloDatos(EditText introdTexto) {
        // Muestra un mensaje de error en el campo de texto
        introdTexto.setError("Este campo es obligatorio.");
    }
}