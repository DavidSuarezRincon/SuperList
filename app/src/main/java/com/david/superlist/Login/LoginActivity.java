package com.david.superlist.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.NavigationDrawer.MainActivity;
import com.david.superlist.R;
import com.david.superlist.pojos.Usuario;
import com.david.superlist.pojos.UsuariosRegistrados;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences datosCompartidos = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        boolean esPrimeraVez = datosCompartidos.getBoolean("esPrimeraVez", true);

        if (esPrimeraVez) {
            // Se ejecuta solo la primera vez
            UsuariosRegistrados.addAdminlUser("root", "root");

            // Establece la bandera a falso para las próximas veces
            SharedPreferences.Editor editor = datosCompartidos.edit();
            editor.putBoolean("esPrimeraVez", false);
            editor.apply();

            Log.d("LoginActivity", "Se ha ejecutado la lógica de la primera vez");
        } else {
            // No es la primera vez, carga los usuarios guardados
            String usuariosJson = datosCompartidos.getString("usuariosRegistrados", null);
            Type type = new TypeToken<ArrayList<Usuario>>() {
            }.getType();

            ArrayList<Usuario> usuariosGuardados = gson.fromJson(usuariosJson, type);

            if (usuariosGuardados != null) {
                UsuariosRegistrados.setUsers(usuariosGuardados);
            }

            Log.d("LoginActivity", "No es la primera vez");
        }


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

                    addPreferenciaString("emailUsuarioLogueado", user.getEmail());
                    addPreferenciaInt("rolUsuarioLogueado", user.getRol());
                    Intent claseMain = new Intent(this, MainActivity.class);//todo
                    // Añade el nombre de usuario al intent
                    claseMain.putExtra("usuarioLogeado", user);

                    startActivity(claseMain);
                    finish();
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

    private void addPreferenciaString(String key, String data) {
        SharedPreferences datosCompartidos = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = datosCompartidos.edit();
        editor.putString(key, data);
        editor.apply();
    }

    private void addPreferenciaInt(String key, int number) {
        SharedPreferences datosCompartidos = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = datosCompartidos.edit();
        editor.putInt(key, number);
        editor.apply();
    }
}