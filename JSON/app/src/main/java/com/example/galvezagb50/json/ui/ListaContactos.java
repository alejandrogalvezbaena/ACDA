package com.example.galvezagb50.json.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.galvezagb50.json.R;
import com.example.galvezagb50.json.network.RestClient;
import com.example.galvezagb50.json.pojo.Contacto;
import com.example.galvezagb50.json.utils.Analisis;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListaContactos extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    //public static final String WEB = "http://192.168.1.20/acceso/contactos.json";
    public static final String WEB = "https://www.portadaalta.mobi/acceso/contactos.json";
    Button boton;
    ListView lista;
    ArrayList<Contacto> contactos;
    ArrayAdapter<Contacto> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(this);
        lista = (ListView) findViewById(R.id.listView);
        lista.setOnItemClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == boton)
            descarga(WEB);
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(web, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    progreso.dismiss();
                    contactos = Analisis.analizarContactos(response);
                    mostrar();
                }
                catch (Exception e)
                {
                    Toast.makeText(ListaContactos.this, "Error al mostrar contactos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                progreso.dismiss();
                Toast.makeText(ListaContactos.this, "Ha fallado la descarga", Toast.LENGTH_SHORT).show();
            }

        } );
    }
    private void mostrar() {
        if (contactos != null)
            if (adapter == null) {
                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);
                lista.setAdapter(adapter);
            } else {
                adapter.clear();
                adapter.addAll(contactos);
            }
        else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Móvil: " + contactos.get(position).getTelefono().getMovil(), Toast.LENGTH_SHORT).show();
    }
}
