package com.example.galvezagb50.ejerciciosjson;

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
import com.example.galvezagb50.ejerciciosjson.adapter.ForecastAdapter;
import com.example.galvezagb50.ejerciciosjson.network.RestClient;
import com.example.galvezagb50.ejerciciosjson.pojo.Tiempo;
import com.example.galvezagb50.ejerciciosjson.utils.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.io.File;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class Ejercicio2 extends AppCompatActivity implements View.OnClickListener {

    MaterialBetterSpinner spinner;
    Button btnDescargar;

    ArrayList<Tiempo> tiempos;
    Activity activity;
    ListView listView;
    String URL, ciudad;

    final String[] PROVINCIAS = {"Alava", "Albacete", "Alicante", "Almería", "Badajoz", "Barcelona", "Burgos", "Cáceres",
            "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara",
            "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra",
            "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
            "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);

        spinner= (MaterialBetterSpinner) findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, PROVINCIAS));
        btnDescargar=(Button)findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.listView);

        tiempos=new ArrayList<Tiempo>();
        activity=this;
        URL="";
        ciudad="";

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setAdapter(null);
                ciudad=parent.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v==btnDescargar)
        {
            if (ciudad.equals(""))
            {
                Toast.makeText(Ejercicio2.this, "Debes elegir una ciudad",Toast.LENGTH_SHORT).show();
            }
            else
            {
                URL = "http://api.openweathermap.org/data/2.5/forecast?q=" + ciudad + ",ES&appid=7c481480309b7f74418d8e2a8b831e2e&mode=xml";
                descarga(URL);
            }
        }
    }

    private void descarga(String url) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(url, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(Ejercicio2.this, "Ciudad no encontrada",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                try {
                    progreso.dismiss();
                    tiempos = Analisis.analizarForecast(file);
                    ForecastAdapter adapter = new ForecastAdapter(activity, tiempos);
                    listView.setAdapter(adapter);
                    Analisis.escribirGSON(tiempos,"forecast.json");
                    Analisis.escribirXML(tiempos, "forecast.xml");
                    Toast.makeText(Ejercicio2.this, "Archivos forecast.json y forecast.xml creados en memoria",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Ejercicio2.this, "Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Descargando");
                progreso.setCancelable(false);
                progreso.show();
            }
        });
    }
}
