package com.example.galvezagb50.ejerciciosjson.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.galvezagb50.ejerciciosjson.R;
import com.example.galvezagb50.ejerciciosjson.pojo.Tiempo;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class ForecastAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Tiempo> tiempos;

    public ForecastAdapter(Activity activity, ArrayList<Tiempo> tiempos)
    {
        this.activity=activity;
        this.tiempos=tiempos;
    }

    @Override
    public int getCount() {return tiempos.size();}

    @Override
    public Object getItem(int position) {return tiempos.get(position);}

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        final String img="http://openweathermap.org/img/w/";

        if (convertView == null)
        {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_forecast, null);
        }

        final Tiempo item = tiempos.get(position);

        TextView txvFecha =(TextView)v.findViewById(R.id.txvFecha);
        txvFecha.setText(item.getFecha());

        MaterialBetterSpinner spinner=(MaterialBetterSpinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(activity, R.layout.item_spinner, item.getHora()));

        final ImageView imageView = (ImageView) v.findViewById(R.id.imgIcon);
        final TextView txvMinimas =(TextView)v.findViewById(R.id.txvMinimas);
        final TextView txvMaximas =(TextView)v.findViewById(R.id.txvMaximas);
        final TextView txvPresion =(TextView)v.findViewById(R.id.txvPresion);

        spinner.setSelection(0);
        Picasso.with(activity).load(img+item.getImagenes().get(0)+".png").into(imageView);
        txvMinimas.setText("Mínimas de: "+item.getMinimas().get(0));
        txvMaximas.setText("Máximas de: "+item.getMaximas().get(0));
        txvPresion.setText("Presión: "+item.getPresiones().get(0));

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Picasso.with(activity).load(img+item.getImagenes().get(position)+".png").into(imageView);
                txvMinimas.setText("Mínimas de: "+item.getMinimas().get(position));
                txvMaximas.setText("Máximas de: "+item.getMaximas().get(position));
                txvPresion.setText("Presión: "+item.getPresiones().get(position));
            }
        });

        return v;
    }
}
