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
import com.david.superlist.pojos.Usuario;
import com.david.superlist.pojos.UsuariosRegistrados;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsuariosRegistrados.addAdminlUser("root", "root");

        // Referencias a los elementos de la interfaz de usuario
        TextView forgotPassword = findViewById(R.id.loginForgotPassword);
        TextView register = findViewById(R.id.loginRegister);
        EditText userEmailEditText = findViewById(R.id.userEmailData);
        EditText userPasswordEditText = findViewById(R.id.userDataPassword);
        Button SignInButton = findViewById(R.id.buttonLogIn);

        // Establece un escuchador de clics en el botón de inicio de sesión
        SignInButton.setOnClickListener(v -> {

            // Obtiene el email y la contraseña introducidos por el usuario
            String email = String.valueOf(userEmailEditText.getText());
            String password = String.valueOf(userPasswordEditText.getText());

            // Comprueba si el email está vacío
            if (TextUtils.isEmpty(email)) {
                setFalloDatos(userEmailEditText);

                // Si la contraseña no está vacía, retorna
                if (!TextUtils.isEmpty(password)) {
                    return;
                }
            }

            // Comprueba si la contraseña está vacía
            if (TextUtils.isEmpty(password)) {
                setFalloDatos(userPasswordEditText);
                return;
            }

            // Si el email y la contraseña son "root", inicia la actividad principal
            if (UsuariosRegistrados.existUser(email)) {

                Usuario user = UsuariosRegistrados.getUser(email);

                if (user.hasThisPassword(password)) {
                    Intent claseMain = new Intent(this, MainActivity.class);
                    // Añade el nombre de usuario al intent
                    claseMain.putExtra("usuarioLogeado", user);

                    startActivity(claseMain);
                } else {
                    String passwordIncorrectError = getResources().getString(R.string.errorContraseñaNoValida);
                    userPasswordEditText.setError(passwordIncorrectError);
                }
            } else {
                String notUserFoundError = getResources().getString(R.string.errorUsuarioNoExiste);
                userEmailEditText.setError(notUserFoundError);
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