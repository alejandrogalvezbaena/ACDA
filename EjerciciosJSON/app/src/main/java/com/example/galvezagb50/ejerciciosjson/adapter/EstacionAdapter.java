package com.example.galvezagb50.ejerciciosjson.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.galvezagb50.ejerciciosjson.R;
import com.example.galvezagb50.ejerciciosjson.pojo.EstacionBici;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class EstacionAdapter extends RecyclerView.Adapter<EstacionAdapter.EstacionHolder> {

    private ArrayList<EstacionBici> estaciones;
    private OnClickListener listener;

    public interface OnClickListener {
        void OnItemClicked(EstacionBici clicked);
    }

    public EstacionAdapter(OnClickListener listener) {
        estaciones = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public EstacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View estacionView = inflater.inflate(R.layout.item_bizi, null);

        EstacionHolder holder = new EstacionHolder(estacionView);
        return holder;
    }

    @Override
    public void onBindViewHolder(EstacionHolder holder, final int position) {
        EstacionBici tmp = estaciones.get(position);

        holder.txvDireccion.setText(tmp.getDireccion());
        if (tmp.isEstado())
        {
            holder.txvEstado.setText("Abierto");
        }
        else
        {
            holder.txvEstado.setText("Cerrado");
        }
        holder.txvDisponibles.setText(tmp.getDisponibles());
        holder.txvAnclajes.setText(tmp.getAnclajes());
        holder.txvModificacion.setText(tmp.getUltimaModificacion());

        holder.vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(getEstacion(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return estaciones.size();
    }

    public class EstacionHolder extends RecyclerView.ViewHolder {
        private TextView txvDireccion;
        private TextView txvEstado;
        private TextView txvDisponibles;
        private TextView txvAnclajes;
        private TextView txvModificacion;
        private View vista;

        public EstacionHolder(View v) {
            super(v);
            txvDisponibles = v.findViewById(R.id.txvDireccion);
            txvEstado = v.findViewById(R.id.txvEstado);
            txvDisponibles = v.findViewById(R.id.txvDisponibles);
            txvAnclajes = v.findViewById(R.id.txvAnclajes);
            txvModificacion = v.findViewById(R.id.txvModificacion);
            vista = v;
        }
    }

    public void addEstacion(ArrayList<EstacionBici> estaciones) {
        this.estaciones.addAll(estaciones);
        notifyDataSetChanged();
    }

    public void clear()
    {
        this.estaciones.clear();
        notifyDataSetChanged();
    }

    public EstacionBici getEstacion(int position)
    {
        try
        {
            return estaciones.get(position);
        }
        catch (IndexOutOfBoundsException e)
        {

        }
        return null;
    }
}




