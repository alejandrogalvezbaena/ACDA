package com.example.galvezagb50.ejerciciosjson.pojo;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class Tiempo {

    private String ciudad;
    private String pais;
    private String estado;
    private String grados;
    private String gradosMinimos;
    private String gradosMaximos;
    private String descripcion;
    private String viento;
    private String presion;
    private String humedad;
    private String coordenadas1;
    private String coordenadas2;
    private String imagen;
    private String fecha;
    private ArrayList<String> hora;
    private ArrayList<String> imagenes;
    private ArrayList<String> minimas;
    private ArrayList<String> maximas;
    private ArrayList<String> presiones;

    public Tiempo()
    {
        this.hora=new ArrayList<String>();
        this.imagenes=new ArrayList<String>();
        this.minimas=new ArrayList<String>();
        this.maximas=new ArrayList<String>();
        this.presiones=new ArrayList<String>();
    }

    public String getGrados() {
        return grados;
    }

    public void setGrados(String grados) {
        DecimalFormat df = new DecimalFormat("#");
        double aux= Double.parseDouble(grados);
        aux=aux-273.15;
        this.grados = String.valueOf(df.format(aux))+"ºC";
    }

    public String getGradosMinimos() {
        return gradosMinimos;
    }

    public void setGradosMinimos(String gradosMinimos) {
        DecimalFormat df = new DecimalFormat("#");
        double aux= Double.parseDouble(gradosMinimos);
        aux=aux-273.15;
        this.gradosMinimos = String.valueOf(df.format(aux))+"ºC";
    }

    public String getGradosMaximos() {
        return gradosMaximos;
    }

    public void setGradosMaximos(String gradosMaximos) {
        DecimalFormat df = new DecimalFormat("#");
        double aux= Double.parseDouble(gradosMaximos);
        aux=aux-273.15;
        this.gradosMaximos = String.valueOf(df.format(aux))+"ºC";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento+" m/s";
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion+" hpa";
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad+"%";
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCoordenadas1() {
        return coordenadas1;
    }

    public void setCoordenadas1(String coordenadas1) {
        this.coordenadas1 = coordenadas1;
    }

    public String getCoordenadas2() {
        return coordenadas2;
    }

    public void setCoordenadas2(String coordenadas2) {
        this.coordenadas2 = coordenadas2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public ArrayList<String> getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora.add(hora);
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes.add(imagenes);
    }

    public ArrayList<String> getMinimas() {
        return minimas;
    }

    public void setMinimas(String minimas) {
        DecimalFormat df = new DecimalFormat("#");
        double aux= Double.parseDouble(minimas);
        aux=aux-273.15;
        this.minimas.add(String.valueOf(df.format(aux))+"ºC");
    }

    public ArrayList<String> getMaximas() {
        return maximas;
    }

    public void setMaximas(String maximas) {
        DecimalFormat df = new DecimalFormat("#");
        double aux= Double.parseDouble(maximas);
        aux=aux-273.15;
        this.maximas.add(String.valueOf(df.format(aux))+"ºC");
    }

    public ArrayList<String> getPresiones() {
        return presiones;
    }

    public void setPresiones(String presiones) {
        this.presiones.add(presiones+" hpa");
    }
}
