package com.example.galvezagb50.xml;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.galvezagb50.xml.network.RestClient;
import com.example.galvezagb50.xml.utils.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CreacionXML extends AppCompatActivity implements View.OnClickListener {


    public static final String RSS = "http://www.alejandrosuarez.es/feed/";
    public static final String TEMPORAL = "alejandro.xml";
    public static final String FICHERO_XML = "resultado.xml";
    Button boton;
    ArrayList<Noticia> noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_xml);

        boton = (Button) findViewById(R.id.boton);
        boton.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(RSS, TEMPORAL);
    }

    private void descarga(String rss, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        final File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temporal);
        RestClient.get(rss, new FileAsyncHttpResponseHandler(miFichero) {

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "FICHERO DESCARGADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                try {
                    Analisis.crearXML(Analisis.analizarNoticias(miFichero), FICHERO_XML);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fallo: "+throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


}
