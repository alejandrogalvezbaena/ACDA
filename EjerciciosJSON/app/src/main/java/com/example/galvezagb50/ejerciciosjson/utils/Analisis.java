package com.example.galvezagb50.ejerciciosjson.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.example.galvezagb50.ejerciciosjson.pojo.Cancion;
import com.example.galvezagb50.ejerciciosjson.pojo.Forecast;
import com.example.galvezagb50.ejerciciosjson.pojo.Tiempo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class Analisis
{
    public static Tiempo analizarTiempo(JSONObject texto) throws JSONException
    {
        Tiempo miTiempo=null;

        JSONObject jsonObject;
        JSONObject coordenadasJSON;
        JSONArray weatherJSON;
        JSONObject weatherobjectJSON;
        JSONObject mainJSON;
        JSONObject windJSON;
        JSONObject sysJSON;
        jsonObject = texto;

        for (int i = 0; i < jsonObject.length(); i++)
        {
            miTiempo=new Tiempo();

            coordenadasJSON=jsonObject.getJSONObject("coord");
            miTiempo.setCoordenadas1(coordenadasJSON.getString("lon"));
            miTiempo.setCoordenadas2(coordenadasJSON.getString("lat"));

            weatherJSON=jsonObject.getJSONArray("weather");
            weatherobjectJSON=weatherJSON.getJSONObject(0);
            miTiempo.setEstado(weatherobjectJSON.getString("main"));
            miTiempo.setDescripcion(weatherobjectJSON.getString("description"));
            miTiempo.setImagen(weatherobjectJSON.getString("icon"));

            mainJSON=jsonObject.getJSONObject("main");
            miTiempo.setGrados(mainJSON.getString("temp"));
            miTiempo.setPresion(mainJSON.getString("pressure"));
            miTiempo.setHumedad(mainJSON.getString("humidity"));
            miTiempo.setGradosMinimos(mainJSON.getString("temp_min"));
            miTiempo.setGradosMaximos(mainJSON.getString("temp_max"));

            windJSON=jsonObject.getJSONObject("wind");
            miTiempo.setViento(windJSON.getString("speed"));

            sysJSON=jsonObject.getJSONObject("sys");
            miTiempo.setPais(sysJSON.getString("country"));

            miTiempo.setCiudad(jsonObject.getString("name"));

        }
        return miTiempo;
    }

    public static ArrayList<Cancion> analizarLyrics(JSONObject texto, Activity activity) throws JSONException, IOException
    {
        Cancion miCancion=null;
        ArrayList<Cancion> resultados=new ArrayList<Cancion>();

        JSONObject jsonObject;
        JSONObject messageJSON;
        JSONArray listJSON;
        JSONObject bodyJSON;
        JSONObject itemJSON;
        JSONObject trackJSON;

        jsonObject = texto;
        messageJSON=jsonObject.getJSONObject("message");
        bodyJSON=messageJSON.getJSONObject("body");
        listJSON=bodyJSON.getJSONArray("track_list");

        for (int i = 0; i < listJSON.length(); i++) {

            String urlImage;
            miCancion=new Cancion();

            itemJSON=listJSON.getJSONObject(i);
            trackJSON=itemJSON.getJSONObject("track");
            miCancion.setNombre(trackJSON.getString("track_name"));
            miCancion.setArtista(trackJSON.getString("artist_name"));
            miCancion.setDireccion(trackJSON.getString("track_share_url"));

            resultados.add(miCancion);
        }
        return resultados;
    }

    public static ArrayList<Tiempo> analizarForecast(File file) throws XmlPullParserException, IOException
    {
        int eventType;
        ArrayList<Tiempo> tiempos = new ArrayList<Tiempo>();
        Tiempo miTiempo = new Tiempo();
        boolean dentroName=false;
        boolean dentroTime = false;
        boolean dentroSymbol =false;
        boolean dentroTemperature=false;
        boolean dentroPressure=false;
        boolean entrar=false;
        boolean primeraVez=false;
        String aux="";
        String aux2="";
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();
        while (eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("name"))
                    {
                        dentroName=true;
                        miTiempo.setCiudad(xpp.nextText());
                    }
                    if (xpp.getName().equals("time"))
                    {
                        dentroTime=true;
                        String[] partes = xpp.getAttributeValue(0).split("T");
                        if (entrar&(aux.equals(partes[0])))
                        {
                            aux2=partes[1];
                        }
                        else
                        {
                            if (primeraVez)
                            {
                                tiempos.add(miTiempo);
                                miTiempo=new Tiempo();
                            }
                            aux = partes[0];
                            aux2 =partes[1];
                        }
                        miTiempo.setFecha(aux);
                        miTiempo.setHora(aux2);
                    }
                    if ((dentroTime)&(xpp.getName().equals("symbol")))
                    {
                        dentroSymbol=true;
                        miTiempo.setImagenes(xpp.getAttributeValue(2));
                    }
                    if ((dentroTime)&(xpp.getName().equals("temperature")))
                    {
                        dentroTemperature=true;
                        miTiempo.setMinimas(xpp.getAttributeValue(2));
                        miTiempo.setMaximas(xpp.getAttributeValue(3));
                    }
                    if ((dentroTime)&(xpp.getName().equals("pressure")))
                    {
                        dentroPressure=true;
                        miTiempo.setPresiones(xpp.getAttributeValue(1));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("name"))
                    {
                        dentroName=false;
                    }
                    if (xpp.getName().equals("time"))
                    {
                        dentroTime=false;
                        primeraVez=true;
                        entrar=true;
                    }
                    if ((dentroTime)&(xpp.getName().equals("symbol")))
                    {
                        dentroSymbol=false;
                    }
                    if ((dentroTime)&(xpp.getName().equals("temperature")))
                    {
                        dentroTemperature=false;
                    }
                    if ((dentroTime)&(xpp.getName().equals("pressure")))
                    {
                        dentroPressure=false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return tiempos;
    }

    public static void escribirGSON(ArrayList<Tiempo> fechas, String fichero) throws IOException
    {
        OutputStreamWriter out;
        File miFichero;
        Forecast miForecast;
        String texto;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd-MM-yyyy");
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        miForecast = new Forecast();
        miForecast.setWeb("http://www.openweathermap.org/");
        miForecast.setCiudad(fechas.get(0).getCiudad());
        miForecast.setTiempos(fechas);
        texto = gson.toJson(miForecast);
        miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero);
        out = new FileWriter(miFichero);
        out.write(texto);
        out.close();
    }

    public static void escribirXML(ArrayList<Tiempo> fechas, String fichero) throws IOException
    {
        FileOutputStream fout;
        fout = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero));
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(fout, "UTF-8");
        serializer.startDocument(null, true);

        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "forecast");
        serializer.attribute(null, "ciudad", fechas.get(0).getCiudad());
        for (int i = 0; i < fechas.size(); i++)
        {

            serializer.startTag(null, "time");
            serializer.attribute(null, "fecha", fechas.get(i).getFecha());

            for (int j = 0; j < fechas.get(i).getHora().size(); j++)
            {
                serializer.startTag(null, "featur");
                serializer.attribute(null, "hora", fechas.get(i).getHora().get(j));
                serializer.attribute(null, "imagen", fechas.get(i).getImagenes().get(j));
                serializer.attribute(null, "minima", fechas.get(i).getMinimas().get(j));
                serializer.attribute(null, "maxima", fechas.get(i).getMaximas().get(j));
                serializer.attribute(null, "presion", fechas.get(i).getPresiones().get(j));
                serializer.endTag(null, "featur");
            }

            serializer.endTag(null, "time");
        }

        serializer.endTag(null, "forecast");
        serializer.endDocument();
        serializer.flush();
        fout.close();
    }

    public static String analizarCambio(JSONObject texto) throws JSONException, IOException
    {
        String cambio;
        JSONObject jsonObject;
        JSONObject resultObject;

        jsonObject = texto;
        resultObject=jsonObject.getJSONObject("result");

        cambio=resultObject.getString("amount");

        return cambio;
    }
}
