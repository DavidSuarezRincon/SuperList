package com.david.superlist.Login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.david.superlist.NavigationDrawer.MainActivity;
import com.david.superlist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPassword;
    private TextView register;
    private EditText userEmailEditText;
    private EditText userPasswordEditText;
    private Button SignInButton;

    //Firebase
    public static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

//        SharedPreferences datosCompartidos = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        boolean esPrimeraVez = datosCompartidos.getBoolean("esPrimeraVez", true);

//        if (userAlreadyLoggedIn()) {
//            redirectToHomeActivity();
//            finish();
//            return;
//        }

//        if (esPrimeraVez) {
//            // Se ejecuta solo la primera vez
//            UsuariosRegistrados.addAdminlUser("root", "root");
//
//            // Establece la bandera a falso para las próximas veces
//            SharedPreferences.Editor editor = datosCompartidos.edit();
//            editor.putBoolean("esPrimeraVez", false);
//            editor.apply();
//
//            Log.d("LoginActivity", "Se ha ejecutado la lógica de la primera vez");
//        } else {
//            // No es la primera vez, carga los usuarios guardados
//            String usuariosJson = datosCompartidos.getString("usuariosRegistrados", null);
//            Type type = new TypeToken<ArrayList<Usuario>>() {
//            }.getType();
//
//            ArrayList<Usuario> usuariosGuardados = gson.fromJson(usuariosJson, type);
//
//            if (usuariosGuardados != null) {
//                UsuariosRegistrados.setUsers(usuariosGuardados);
//            }
//
//            Log.d("LoginActivity", "No es la primera vez");
//        }

        // Referencias a los elementos de la interfaz de usuario
        forgotPassword = findViewById(R.id.loginForgotPassword);
        register = findViewById(R.id.loginRegister);
        userEmailEditText = findViewById(R.id.userEmailData);
        userPasswordEditText = findViewById(R.id.userDataPassword);
        SignInButton = findViewById(R.id.buttonLogIn);

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

            logUser(email, password);

        });

        // Establece un escuchador de clics en el texto de "olvidé mi contraseña"
        forgotPassword.setOnClickListener(view -> {
            // Inicia la actividad de recuperación de contraseña
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        // Establece un escuchador de clics en el texto de "registrarse"
        register.setOnClickListener(view -> {
            // Inicia la actividad de registro
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    private void logUser(String email, String password) {
//        addPreferenciaString("emailUsuarioLogueado", user.getEmail());
//        addPreferenciaInt("rolUsuarioLogueado", user.getRol());
//        addPreferenciaBoolean("estadoLogUsuario", true);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // Añade el nombre de usuario al intent
//        claseMain.putExtra("usuarioLogeado", user);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent claseMain = new Intent(this, MainActivity.class);
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            startActivity(claseMain);
            finish();
        } else {
            Toast.makeText(this, "El usuario no existe.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar un error cuando un campo está vacío
    private void setFalloDatos(EditText introdTexto) {
        // Muestra un mensaje de error en el campo de texto
        introdTexto.setError("Este campo es obligatorio.");
    }
}