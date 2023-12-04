package com.david.superlist.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.david.superlist.R;

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