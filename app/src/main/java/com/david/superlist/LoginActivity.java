package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button botonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botonLogin = findViewById(R.id.buttonLogIn);

        botonLogin.setOnClickListener(view -> {

            System.out.println("Hola");

        });

    }
}
