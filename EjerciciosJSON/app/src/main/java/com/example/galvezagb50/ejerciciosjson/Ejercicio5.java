package com.example.galvezagb50.ejerciciosjson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.galvezagb50.ejerciciosjson.adapter.CancionAdapter;
import com.example.galvezagb50.ejerciciosjson.network.RestClient;
import com.example.galvezagb50.ejerciciosjson.pojo.Cancion;
import com.example.galvezagb50.ejerciciosjson.utils.Analisis;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class Ejercicio5 extends AppCompatActivity implements View.OnClickListener{

    TextView txvResultados;
    EditText edtBusqueda;
    Button btnBusqueda;
    ListView listView;

    Activity activity;
    ArrayList<Cancion> resultados;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio5);

        txvResultados=(TextView)findViewById(R.id.txvResultados);
        edtBusqueda=(EditText)findViewById(R.id.edtBusqueda);
        btnBusqueda=(Button)findViewById(R.id.btnBuscar);
        btnBusqueda.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.listView);

        activity=this;
        resultados=new ArrayList<Cancion>();
    }

    @Override
    public void onClick(View v) {

        if (v==btnBusqueda)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            if (edtBusqueda.getText().toString().equals(""))
            {
                Toast.makeText(Ejercicio5.this, "Introduce una búsqueda", Toast.LENGTH_SHORT).show();
            }
            else
            {
                url="https://api.musixmatch.com/ws/1.1/track.search?format=json&q_lyrics="+edtBusqueda.getText().toString()+"&apikey=0c0b1d2ea717df5dc9741f4abf4b11b9";
                descarga(url);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int pos = position;
                        Uri uri = Uri.parse(resultados.get(position).getDireccion());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        if (intent.resolveActivity(getPackageManager()) != null)
                            startActivity(intent);
                        else
                            Toast.makeText(getApplicationContext(), "No existe ningún navegador", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }

    }

    private void descarga(String web)
    {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(web, new JsonHttpResponseHandler()
        {
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
                progreso.dismiss();
                try
                {
                    resultados = Analisis.analizarLyrics(response, activity);
                    CancionAdapter adapter = new CancionAdapter(activity, resultados);
                    txvResultados.setText("Resultados de la búsqueda: "+resultados.size()+" canciones");
                    listView.setAdapter(adapter);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Ejercicio5.this, "Error de argumentos de E/S", Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Ejercicio5.this, "Error en el fichero JSON", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                progreso.dismiss();
                Toast.makeText(Ejercicio5.this, "Ha fallado la descarga", Toast.LENGTH_SHORT).show();
            }
        } );
    }


}
