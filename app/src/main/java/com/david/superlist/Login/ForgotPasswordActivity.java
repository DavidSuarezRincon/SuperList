package com.david.superlist.Login;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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