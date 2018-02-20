package com.example.galvezagb50.red;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ConexionAsincrona extends AppCompatActivity implements View.OnClickListener {

    EditText direccion;
    RadioButton radioJava, radioApache;
    Button conectar;
    WebView web;
    TextView tiempo;
    public static final String JAVA="Java";
    public static final String APACHE="Apache";
    long inicio, fin;
    TareaAsincrona tareaAsincrona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexion_http);

        iniciar();
    }

    private void iniciar() {
        direccion = (EditText) findViewById(R.id.direccion);
        radioJava = (RadioButton) findViewById(R.id.radioJava);
        radioApache = (RadioButton) findViewById(R.id.radioApache);
        conectar = (Button) findViewById(R.id.conectar);
        conectar.setOnClickListener(this);
        web = (WebView) findViewById(R.id.web);
        tiempo = (TextView) findViewById(R.id.resultado);
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
    }
    @Override
    public void onClick(View v) {
        String texto = direccion.getText().toString();
        //long inicio, fin;
        Resultado resultado;
        String tipo=APACHE;
        if (v == conectar) {
            inicio = System.currentTimeMillis();
            if (radioJava.isChecked())
                tipo=JAVA;
            tareaAsincrona=new TareaAsincrona(this);
            tareaAsincrona.execute(tipo,texto);
            tiempo.setText("Descargando la pagina");

        }
    }

    public class TareaAsincrona extends AsyncTask<String, Integer, Resultado > {
        private ProgressDialog progreso;
        private Context context;
        public TareaAsincrona(Context context){
            this.context = context;
        }
        protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Conectando . . .");
            progreso.setCancelable(true);
            progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                public void onCancel(DialogInterface dialog){
                    TareaAsincrona.this.cancel(true);
                }
            });
            progreso.show();
        }
        protected Resultado doInBackground(String... cadena) {
            Resultado resultado;
            int i = 1;
            try {
                // operaciones en el hilo secundario
                publishProgress(i++);
                if (cadena[0].equals(JAVA))
                    resultado = Conexion.conectarJava(cadena[1]);
                else
                    resultado = Conexion.conectarApache(cadena[1]);

            } catch (Exception e) {
                Log.e("HTTP", e.getMessage(), e);
                resultado = null;
                cancel(true);
            }
            return resultado;
        }
        protected void onProgressUpdate(Integer... progress) {
            progreso.setMessage("Conectando " + Integer.toString(progress[0]));
        }
        protected void onPostExecute(Resultado resultado) {
            progreso.dismiss();
            // mostrar el resultado
            fin = System.currentTimeMillis();
            if (resultado.getCodigo())
                web.loadDataWithBaseURL(null, resultado.getContenido(),"text/html", "UTF-8", null);
            else
                web.loadDataWithBaseURL(null, resultado.getMensaje(),"text/html", "UTF-8", null);
            tiempo.setText("Duración: " + String.valueOf(fin - inicio) + " milisegundos");

        }
        protected void onCancelled() {
            progreso.dismiss();
// mostrar cancelación

        }
    }
}
