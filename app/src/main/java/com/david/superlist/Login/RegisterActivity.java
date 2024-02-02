package com.david.superlist.Login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.david.superlist.NavigationDrawer.MainActivity;
import com.david.superlist.R;
import com.david.superlist.pojos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    ImageButton goBack;
    TextInputEditText registerEmailEditText, firstPasswordEditText, secondPasswordEditText;
    Button buttonRegister;
    ConstraintLayout parentLayout;
    FirebaseAuth registermAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registermAuth = LoginActivity.mAuth;

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
                crearUsuarioFirebase(email, firstPasswordInput);
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

//        if (UsuariosRegistrados.existUser(email)) {
//            setUserAlreadyExistsError(registerEmailEditText);
//            thereIsAnError = true;
//        }

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

    private void setPasswordSoShotError(EditText et) {
        String notMatchingPasswordsErrorMessage = "La contraseña debe contener al menos 6 caracteres";
        et.setError(notMatchingPasswordsErrorMessage);
    }

    private void setUserAlreadyExistsError(EditText et) {
        String userAlreadyExistsError = getResources().getString(R.string.textoErrorUsuarioExistente);
        et.setError(userAlreadyExistsError);
    }

    private void crearUsuarioFirebase(String email, String password) {
        registermAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Se registró correctamente..",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = registermAuth.getCurrentUser();

                            // Crear un nuevo registro en la base de datos
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            Usuario nuevoUsuario = new Usuario(0, new ArrayList<>()); // 0 para rol de usuario, ArrayList vacío para las listas
                            database.child("SuperList").child(user.getUid()).setValue(nuevoUsuario);

                            updateUI(user);
                        } else {


                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG ).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, redirect to the main activity
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // This closes the RegisterActivity
        } else {
            registerEmailEditText.setText("");
            firstPasswordEditText.setText("");
            secondPasswordEditText.setText("");
        }
    }
}