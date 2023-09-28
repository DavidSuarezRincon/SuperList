package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        TextView texto = findViewById(R.id.loginForgotPassword);

        texto.setOnClickListener(view -> texto.setVisibility(View.INVISIBLE));

    }
}
