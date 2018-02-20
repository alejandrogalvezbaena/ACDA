package com.example.galvezagb50.red;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ConexionAAHC extends AppCompatActivity implements View.OnClickListener {

    EditText direccion;
    Button conectar;
    WebView web;
    TextView tiempo;
    long fin, inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_aahc);

        iniciar();
    }

    private void iniciar() {
        direccion = (EditText) findViewById(R.id.direccion);
        conectar = (Button) findViewById(R.id.conectar);
        conectar.setOnClickListener(this);
        web = (WebView) findViewById(R.id.web);
        tiempo = (TextView) findViewById(R.id.resultado);
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }

    @Override
    public void onClick(View view) {
        if (view == conectar) {
            AAHC();
        }
    }

    private void AAHC() {
        final String texto = direccion.getText().toString();
        //final long inicio;
        //final long[] fin = new long[1];
        final ProgressDialog progreso = new ProgressDialog(ConexionAAHC.this);
        inicio = System.currentTimeMillis();
        RestClient.get(texto, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                //progreso.setCancelable(false);
                progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {RestClient.cancelRequests(getApplicationContext(), true);}
                        });
                progreso.show();
            }
            /*@Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                fin=System.currentTimeMillis();
                progreso.dismiss();
                web.loadDataWithBaseURL(null, "Error: "+statusCode+" "+responseBytes.toString()+" "+throwable.getMessage().toString(), "text/html", "UTF-8", null );
                tiempo.setText("Duracion: "+String.valueOf(fin-inicio)+" milisegundos");
            }*/

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                fin=System.currentTimeMillis();
                progreso.dismiss();
                web.loadDataWithBaseURL(null, "Error: "+statusCode+" "+responseString+" "+throwable.getMessage().toString(), "text/html", "UTF-8", null  );
                tiempo.setText("Duracion: "+String.valueOf(fin-inicio)+" milisegundos");
            }

            @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        fin=System.currentTimeMillis();
                        progreso.dismiss();
                        web.loadDataWithBaseURL(null, responseString, "text/html", "UTF-8", null  );
                        tiempo.setText("Duracion; "+String.valueOf(fin-inicio)+" milisegundos");


                    }
                }
        );
    }
}


