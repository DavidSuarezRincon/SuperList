package com.david.superlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton goBack = findViewById(R.id.imgButtonGoBackRegister);

        goBack.setOnClickListener(view -> {

            finish();

        });


    }
}