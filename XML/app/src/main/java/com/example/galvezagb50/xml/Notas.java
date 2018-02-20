package com.example.galvezagb50.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.galvezagb50.xml.utils.Analisis;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Notas extends AppCompatActivity {

    TextView mostrarNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        mostrarNotas=(TextView)findViewById(R.id.txvMostrarNotas);

        try {
            mostrarNotas.setText(Analisis.analizarNombres(this));

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            mostrarNotas.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarNotas.setText(e.getMessage());
        }
    }
}
