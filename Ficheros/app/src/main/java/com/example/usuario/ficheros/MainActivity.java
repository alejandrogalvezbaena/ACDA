package com.example.usuario.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button boton1, boton2, boton3, boton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton1=(Button) findViewById(R.id.button);
        boton1.setOnClickListener(this);
        boton2=(Button) findViewById(R.id.button2);
        boton2.setOnClickListener(this);
        boton3=(Button) findViewById(R.id.button3);
        boton3.setOnClickListener(this);
        boton4=(Button) findViewById(R.id.button4);
        boton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;

        if (view==boton1)
        {
            i=new Intent(this, EscribirInterna.class);
            startActivity(i);
        }
        if (view==boton2)
        {
            i=new Intent(this, EscribirExterna.class);
            startActivity(i);
        }
        if (view==boton3)
        {
            i=new Intent(this, LeerFicheros.class);
            startActivity(i);
        }
        if (view==boton4)
        {
            i=new Intent(this, Codificacion.class);
            startActivity(i);
        }

    }
}
