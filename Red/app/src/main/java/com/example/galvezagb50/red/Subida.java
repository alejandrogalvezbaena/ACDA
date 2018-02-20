package com.example.galvezagb50.red;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class Subida extends AppCompatActivity implements View.OnClickListener {

    public final static String WEB = "http://alumno.mobi/~alumno/superior/galvez/upload.php";
    EditText texto;
    TextView informacion;
    Button subir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subida);

        texto=(EditText)findViewById(R.id.edtRuta);
        informacion=(TextView)findViewById(R.id.txvInfo);
        subir=(Button)findViewById(R.id.btnSubir);
        subir.setOnClickListener(this);
    }


    private void subida() {
        String fichero = texto.getText().toString();
        final ProgressDialog progreso = new ProgressDialog(Subida.this);
        File myFile;
        Boolean existe = true;
        myFile = new File(Environment.getExternalStorageDirectory(), fichero);
        //File myFile = new File("/path/to/file.png");
        RequestParams params = new RequestParams();
        try {
            params.put("fileToUpload", myFile);
        } catch (FileNotFoundException e) {
            existe = false;
            informacion.setText("Error en el fichero: " + e.getMessage());
            //Toast.makeText(this, "Error en el fichero: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (existe)
            RestClient.post(WEB, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    // called before request is started
                    progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progreso.setMessage("Conectando . . .");
                    //progreso.setCancelable(false);
                    progreso.setOnCancelListener(new DialogInterface.OnCancelListener(){
                        public void onCancel(DialogInterface dialog){
                            RestClient.cancelRequests(getApplicationContext(), true);
                        }
                    });
                    progreso.show();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String response) {
                    // called when response HTTP status is "200 OK"
                    progreso.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String response, Throwable t) {
                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    progreso.dismiss();
                }
            });
    }

    @Override
    public void onClick(View view) {
        if (view==subir)
        {
            subida();
        }
    }
}
