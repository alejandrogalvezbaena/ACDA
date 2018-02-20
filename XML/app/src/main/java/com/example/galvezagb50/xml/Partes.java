package com.example.galvezagb50.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.galvezagb50.xml.utils.Analisis;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Partes extends AppCompatActivity {

    public static final String TEXTO = "<texto><uno>Hello World!</uno><dos>Goodbye</dos></texto>";

    TextView informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partes);

        informacion=(TextView)findViewById(R.id.txvInfo);

        try {
            informacion.setText(Analisis.analizar(TEXTO));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            informacion.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            informacion.setText(e.getMessage());
        }
    }
}
