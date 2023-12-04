package com.david.superlist.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.david.superlist.R;

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