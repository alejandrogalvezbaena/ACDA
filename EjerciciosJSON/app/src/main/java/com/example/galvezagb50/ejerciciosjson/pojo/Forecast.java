package com.example.galvezagb50.ejerciciosjson.pojo;

import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class Forecast
{
    private ArrayList<Tiempo> tiempos;
    private String web;
    private String ciudad;

    public ArrayList<Tiempo> getTiempos() {
        return tiempos;
    }

    public void setTiempos(ArrayList<Tiempo> tiempos) {
        this.tiempos = tiempos;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
