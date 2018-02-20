package com.example.usuario.hola;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText nombre, direccion;
    Button saludar, navegar;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        nombre=(EditText)findViewById(R.id.edtNombre);
        direccion=(EditText)findViewById(R.id.edtDireccion);
        saludar=(Button) findViewById(R.id.btnSaludar);
        saludar.setOnClickListener(this);
        navegar=(Button) findViewById(R.id.btnNavegar);
        navegar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==saludar){
            i = new Intent (this, Segunda.class);
            i.putExtra("nombre", nombre.getText().toString());
            startActivity(i);
        }
        if (view==navegar){
            openWebPage(direccion.getText().toString());
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
