package com.example.usuario.hola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Segunda extends AppCompatActivity {

    TextView saludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        saludo = (TextView) findViewById(R.id.txvMostrar);
        Intent i = this.getIntent();
        saludo.setText("Hola " + i.getStringExtra("nombre"));
    }
}
