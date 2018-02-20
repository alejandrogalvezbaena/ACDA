package com.example.galvezagb50.ejerciciosjson;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.galvezagb50.ejerciciosjson.adapter.EstacionAdapter;
import com.example.galvezagb50.ejerciciosjson.pojo.EstacionBici;
import java.util.ArrayList;


public class Ejercicio4 extends AppCompatActivity implements EstacionAdapter.OnClickListener {

    RecyclerView recyclerView;

    private EstacionAdapter adapter;
    private ArrayList<EstacionBici> estaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio4);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new EstacionAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void OnItemClicked(EstacionBici clicked) {

    }
}
