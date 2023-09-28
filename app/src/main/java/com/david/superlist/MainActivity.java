package com.david.superlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        boton = findViewById(R.id.botonIr);
//
//        boton.setOnClickListener(view -> startActivity(new Intent(this,LoginActivity.class)));

    }
}