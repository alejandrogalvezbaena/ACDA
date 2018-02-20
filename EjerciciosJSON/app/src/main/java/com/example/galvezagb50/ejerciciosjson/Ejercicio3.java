package com.example.galvezagb50.ejerciciosjson;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.galvezagb50.ejerciciosjson.network.RestClient;
import com.example.galvezagb50.ejerciciosjson.utils.Analisis;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import cz.msebera.android.httpclient.Header;

public class Ejercicio3 extends AppCompatActivity implements View.OnClickListener{

    EditText edtDolares, edtEuros;
    RadioButton rbDolaresEuros, rbEurosDolares;
    Button btnConvertir;
    RadioGroup radioGroup;

    private static final String URL="https://api.currencies.zone/v1/quotes/USD/EUR/json?quantity=1&key=55|quU~xjh*soJdWBpD7CmYuHoLp2XSjQ~y";
    Double cambio=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio3);

        edtDolares=(EditText)findViewById(R.id.edtDolares);
        edtEuros=(EditText)findViewById(R.id.edtEuros);
        rbDolaresEuros=(RadioButton)findViewById(R.id.rbDolaresEuros);
        rbEurosDolares=(RadioButton)findViewById(R.id.rbDolaresEuros);
        btnConvertir=(Button)findViewById(R.id.btnConvertir);
        btnConvertir.setOnClickListener(this);
        radioGroup=(RadioGroup)findViewById(R.id.groupRadio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==rbDolaresEuros.getId())
                {
                    edtDolares.setEnabled(true);
                    edtDolares.setText("");
                    edtDolares.setHint("Introduce dólares");
                    edtEuros.setText("");
                    edtEuros.setHint("");
                    edtEuros.setEnabled(false);
                }
                else
                {
                    edtEuros.setEnabled(true);
                    edtEuros.setText("");
                    edtEuros.setHint("Introduce euros");
                    edtDolares.setText("");
                    edtDolares.setHint("");
                    edtDolares.setEnabled(false);
                }
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        if (v==btnConvertir)
        {
            if(rbDolaresEuros.isChecked())
            {
                if (edtDolares.getText().equals(""))
                {
                    Toast.makeText(Ejercicio3.this, "Introduce un valor válido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    descarga(URL);
                }
            }
            else
            {
                if (edtEuros.getText().equals(""))
                {
                    Toast.makeText(Ejercicio3.this, "Introduce un valor válido", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    descarga(URL);
                }
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
                    cambio=Double.parseDouble(Analisis.analizarCambio(response));
                    if (rbDolaresEuros.isChecked())
                    {
                        edtEuros.setText(String.format("%.2f",Double.parseDouble(edtDolares.getText().toString()) * cambio));
                    }
                    else
                    {
                        edtDolares.setText(String.format("%.2f",Double.parseDouble(edtEuros.getText().toString()) / cambio));
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Ejercicio3.this, "Error de argumentos de E/S", Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Ejercicio3.this, "Error en el fichero JSON", Toast.LENGTH_SHORT).show();
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Ejercicio3.this, "Introduce un valor válido", Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                progreso.dismiss();
                Toast.makeText(Ejercicio3.this, "Ha fallado la descarga", Toast.LENGTH_SHORT).show();
            }
        } );
    }


}
