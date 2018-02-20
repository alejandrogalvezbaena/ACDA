package com.example.usuario.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EscribirInterna extends AppCompatActivity implements View.OnClickListener{

    EditText operando1,operando2, propiedades;
    TextView resultado;
    Button boton;

    public final static String NOMBREFICHERO="resultado.txt";
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_interna);
        resultado=(TextView)findViewById(R.id.txvResultado);
        propiedades=(EditText) findViewById(R.id.edtDescripcion);
        boton=(Button)findViewById(R.id.btnOperando);
        boton.setOnClickListener(this);
        operando1=(EditText)findViewById(R.id.edtOperando1);
        operando2=(EditText)findViewById(R.id.edtOperando2);

        miMemoria=new Memoria(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        int r;
        String op1=operando1.getText().toString();
        String op2=operando2.getText().toString();
        String texto="0";
        if (view==boton)
        {
            try
            {
                r = Integer.parseInt(op1) + Integer.parseInt(op2);
                texto = String.valueOf(r);
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT);
            }
            resultado.setText(texto);
            if (miMemoria.escribirInterna(NOMBREFICHERO, texto, false, "UTF-8"))
            {
                propiedades.setText(miMemoria.mostrarPropiedadesInterna(NOMBREFICHERO));
            }
        }
    }
}
