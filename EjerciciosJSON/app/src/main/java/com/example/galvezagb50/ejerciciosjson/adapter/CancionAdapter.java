package com.example.galvezagb50.ejerciciosjson.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galvezagb50.ejerciciosjson.R;
import com.example.galvezagb50.ejerciciosjson.pojo.Cancion;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class CancionAdapter extends BaseAdapter {

    private  Activity activity;
    private ArrayList<Cancion> canciones;

    public CancionAdapter(Activity activity, ArrayList<Cancion> canciones)
    {
        this.activity=activity;
        this.canciones=canciones;
    }

    @Override
    public int getCount() {
        return canciones.size();
    }

    @Override
    public Object getItem(int position) {
        return canciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v=convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_cancion, null);
        }

        final Cancion item = canciones.get(position);

        ImageView imageView = (ImageView) v.findViewById(R.id.imgCaratula);
        imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.caratulavacia));


        TextView txvCancion =(TextView)v.findViewById(R.id.txvCancion);
        txvCancion.setText(item.getNombre());

        TextView txvArtista =(TextView)v.findViewById(R.id.txvArtista);
        txvArtista.setText(item.getArtista());

        return v;
    }
}
