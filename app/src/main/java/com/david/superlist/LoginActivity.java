package com.david.superlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

        ImageButton goBack = findViewById(R.id.imgButtonGoBackLogin);

        goBack.setOnClickListener(view -> {

            finish();

        });

        TextView forgotPassword = findViewById(R.id.loginForgotPassword);

        forgotPassword.setOnClickListener(view -> forgotPassword.setVisibility(View.INVISIBLE));

        TextView register = findViewById(R.id.loginRegister);

        register.setOnClickListener(
            view ->
                    startActivity(new Intent(this,RegisterActivity.class))

        );

    }
}
