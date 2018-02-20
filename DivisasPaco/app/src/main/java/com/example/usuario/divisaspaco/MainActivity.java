package com.example.usuario.divisaspaco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boton;
    EditText euros, dolares;
    RadioButton radioEuros, radioDolares;
    double cambio=0.84;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        euros=(EditText) findViewById(R.id.text_euros);
        dolares=(EditText) findViewById((R.id.text_dolares));
        radioEuros=(RadioButton) findViewById(R.id.radio_euros);
        radioDolares=(RadioButton) findViewById((R.id.radio_dolares));
        boton=(Button) findViewById(R.id.boton_convertir);
        boton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==boton)
            if (radioEuros.isChecked()) {
                dolares.setText(convertirADolares(euros.getText().toString()));
            }
            else{
                euros.setText(convertirAEuros(dolares.getText().toString()));
            }
    }

    public String convertirADolares(String cantidad) {
        double valor = Double.parseDouble(cantidad) / cambio;
        return Double.toString(Math.round(valor*100d)/100d);
        //return String.valueOf(valor);
    }

    public String convertirAEuros(String cantidad) {
        double valor = Double.parseDouble(cantidad) * cambio;
        //return String.format("%.2f", valor);

        return Double.toString(Math.round(valor*100d)/100d);
    }
}
