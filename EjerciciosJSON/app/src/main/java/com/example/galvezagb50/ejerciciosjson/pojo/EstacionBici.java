package com.example.galvezagb50.ejerciciosjson.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by galvezagb50.
 */

public class EstacionBici {

    @SerializedName("title")
    @Expose
    private String direccion;
    @SerializedName("estado")
    @Expose
    private boolean estado;
    @SerializedName("bicisDisponibles")
    @Expose
    private int disponibles;
    @SerializedName("anclajesDisponibles")
    @Expose
    private int anclajes;
    @SerializedName("lastUpdated")
    @Expose
    private String ultimaModificacion;
    @SerializedName("about")
    @Expose
    private String mapa;

    public EstacionBici(){}

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

    public int getAnclajes() {
        return anclajes;
    }

    public void setAnclajes(int anclajes) {
        this.anclajes = anclajes;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    @Override
    public String toString() {
        return direccion;
    }
}
