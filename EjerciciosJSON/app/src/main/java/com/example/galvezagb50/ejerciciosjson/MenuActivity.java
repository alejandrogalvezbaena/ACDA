package com.example.galvezagb50.ejerciciosjson;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        Intent i;

        if (view == btn1) {
            i = new Intent(this, Ejercicio1.class);
            startActivity(i);
        }
        if (view == btn2) {
            i = new Intent(this, Ejercicio2.class);
            startActivity(i);
        }
        if (view == btn3) {
            i = new Intent(this, Ejercicio3.class);
            startActivity(i);
        }
        if (view == btn4) {
            i = new Intent(this, Ejercicio4.class);
            startActivity(i);
        }
        if (view == btn5) {
            i = new Intent(this, Ejercicio5.class);
            startActivity(i);
        }
    }
}
