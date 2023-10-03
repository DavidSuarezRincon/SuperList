package com.david.superlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {

//    Button botonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        botonLogin = findViewById(R.id.buttonLogIn);
//
//        botonLogin.setOnClickListener(view -> {
//
//
//
//        });

        TextView forgotPassword = findViewById(R.id.loginForgotPassword);

        forgotPassword.setOnClickListener(view -> forgotPassword.setVisibility(View.INVISIBLE));

        TextView register = findViewById(R.id.loginRegister);

        register.setOnClickListener(
            view ->
                System.out.println("hola")

        );

    }
}
