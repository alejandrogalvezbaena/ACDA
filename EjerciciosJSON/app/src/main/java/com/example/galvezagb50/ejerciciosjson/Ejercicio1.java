package com.example.galvezagb50.ejerciciosjson;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.galvezagb50.ejerciciosjson.network.RestClient;
import com.example.galvezagb50.ejerciciosjson.pojo.Tiempo;
import com.example.galvezagb50.ejerciciosjson.utils.Analisis;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class Ejercicio1 extends AppCompatActivity {

    RadioButton rbMalaga, rbMadrid, rbBarcelona;
    ImageView imageView;
    TextView txvGrados, txvDescripcion, txvViento, txvHumedad, txvPresion, txvMinima, txvMaxima, txvCoordenadas;
    RadioGroup radioGroup;

    Tiempo miTiempo;
    String url="http://api.openweathermap.org/data/2.5/weather?q=Malaga,ES&appid=7c481480309b7f74418d8e2a8b831e2e";
    String img="http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);

        imageView=(ImageView)findViewById(R.id.imageView3);
        txvGrados=(TextView)findViewById(R.id.txvGrados);
        txvDescripcion=(TextView)findViewById(R.id.txvEstado);
        txvViento=(TextView)findViewById(R.id.txvViento);
        txvHumedad=(TextView)findViewById(R.id.txvHumedad);
        txvPresion=(TextView)findViewById(R.id.txvPresion);
        txvMinima=(TextView)findViewById(R.id.txvMinima);
        txvMaxima=(TextView)findViewById(R.id.txvMaxima);
        txvCoordenadas=(TextView)findViewById(R.id.txvCoordenadas);
        rbMalaga=(RadioButton)findViewById(R.id.rbMalaga);
        rbMadrid=(RadioButton)findViewById(R.id.rbMadrid);
        rbBarcelona=(RadioButton)findViewById(R.id.rbBarcelona);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==rbMalaga.getId())
                {
                    url="http://api.openweathermap.org/data/2.5/weather?q=Malaga,ES&appid=7c481480309b7f74418d8e2a8b831e2e";
                }
                if (checkedId==rbMadrid.getId())
                {
                    url="http://api.openweathermap.org/data/2.5/weather?q=Madrid,ES&appid=7c481480309b7f74418d8e2a8b831e2e";
                }
                if (checkedId==rbBarcelona.getId())
                {
                    url="http://api.openweathermap.org/data/2.5/weather?q=Barcelona,ES&appid=7c481480309b7f74418d8e2a8b831e2e";
                }

                descarga(url);
            }
        });

        descarga(url);
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
                    miTiempo=new Tiempo();
                    miTiempo = Analisis.analizarTiempo(response);
                    mostrar();
                }
                catch (Exception e)
                {
                    Toast.makeText(Ejercicio1.this, "Error al mostrar el tiempo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                progreso.dismiss();
                Toast.makeText(Ejercicio1.this, "Ha fallado la descarga", Toast.LENGTH_SHORT).show();
            }

        } );
    }

    private void mostrar()
    {
        Picasso.with(this).load(img+miTiempo.getImagen()+".png").into(imageView);
        txvGrados.setText(miTiempo.getGrados());
        txvDescripcion.setText(miTiempo.getDescripcion());
        txvViento.setText(miTiempo.getViento());
        txvPresion.setText(miTiempo.getPresion());
        txvHumedad.setText(miTiempo.getHumedad());
        txvMinima.setText(miTiempo.getGradosMinimos());
        txvMaxima.setText(miTiempo.getGradosMaximos());
        txvCoordenadas.setText("["+miTiempo.getCoordenadas1()+","+miTiempo.getCoordenadas2()+"]");
    }
}
