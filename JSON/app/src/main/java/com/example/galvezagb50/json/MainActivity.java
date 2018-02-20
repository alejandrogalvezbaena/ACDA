package com.example.galvezagb50.json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.galvezagb50.json.ui.ListaContactos;
import com.example.galvezagb50.json.ui.Primitiva;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button2);
        btn3=(Button)findViewById(R.id.button3);
        btn4=(Button)findViewById(R.id.button4);
        btn5=(Button)findViewById(R.id.button5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        if (view==btn1)
        {
            i=new Intent(this, Primitiva.class);
            startActivity(i);

        }
        if (view==btn2)
        {
            i=new Intent(this, ListaContactos.class);
            startActivity(i);
        }
        if (view==btn3)
        {

        }
        if (view==btn4)
        {

        }
        if (view==btn5)
        {

        }
    }
}
