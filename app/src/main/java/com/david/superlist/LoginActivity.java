package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView forgotPassword = findViewById(R.id.loginForgotPassword);
        TextView register = findViewById(R.id.loginRegister);
        EditText introducirEmail = findViewById(R.id.userEmailData);
        EditText introducirPassword = findViewById(R.id.userDataPassword);
        Button botonSignIn = findViewById(R.id.buttonLogIn);


        botonSignIn.setOnClickListener(v -> {

            String email = String.valueOf(introducirEmail.getText());
            String password = String.valueOf(introducirPassword.getText());

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                setFalloDatos(introducirEmail);
                setFalloDatos(introducirPassword);

                return;

            }
        });


        forgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        register.setOnClickListener(
                view ->
                        startActivity(new Intent(this, RegisterActivity.class))
        );
    }

    private void setFalloDatos(EditText introdTexto) {

        introdTexto.setError("Este campo es obligatorio.");

    }
}
