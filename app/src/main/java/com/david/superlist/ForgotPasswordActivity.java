package com.david.superlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        ImageButton botonBack = findViewById(R.id.imgButtonGoBackForgotPassword);


        botonBack.setOnClickListener(view -> {

            finish();

        });



    }
}