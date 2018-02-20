package com.example.galvezagb50.xml.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.util.Xml;

import com.example.galvezagb50.xml.Noticia;
import com.example.galvezagb50.xml.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by galvezagb50.
 */

public class Analisis {

    public static String analizar(String texto) throws XmlPullParserException, IOException {
        StringBuilder cadena = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();

        xpp.setInput(new StringReader(texto));
        int eventType = xpp.getEventType();
        cadena.append("Inicio . . . \n");
        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_DOCUMENT) {
                cadena.append("START DOCUMENT "+'\n');
            } else if (eventType == XmlPullParser.START_TAG) {
                cadena.append("START TAG "+ xpp.getName()+'\n');
            } else if (eventType == XmlPullParser.TEXT) {
                cadena.append("TEXT " + xpp.getText()+'\n');
            } else if (eventType == XmlPullParser.END_TAG) {
                cadena.append("END TAG " + xpp.getName()+'\n');
            }
            eventType = xpp.next();
        }

        //System.out.println("End document");
        cadena.append("End document" + "\n" + "Fin");
        return cadena.toString();
    }

    public static String analizarNombres(Context c) throws XmlPullParserException,IOException {
        boolean esNombre = false;
        boolean esNota = false;
        StringBuilder cadena = new StringBuilder();
        Double suma = 0.0;
        int contador = 0;
        XmlResourceParser xrp = c.getResources().getXml(R.xml.alumnos);
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser. END_DOCUMENT ) {
            switch (eventType) {
                case XmlPullParser.START_TAG :
                    if (xrp.getName().equals("nombre"))
                    {
                        esNombre=true;
                        contador++;
                    }
                    if (xrp.getName().equals("nota"))
                    {
                        esNota=true;
                        cadena.append("asignatura: "+xrp.getAttributeValue(0)+"\n");
                        cadena.append("fecha: "+xrp.getAttributeValue(1)+"\n");
                    }
                    break;
                case XmlPullParser.TEXT :
                    if (esNombre)
                    {
                        cadena.append("Alumno: "+xrp.getText()+"\n");
                    }
                    if (esNota)
                    {
                        cadena.append("Nota "+xrp.getText()+"\n");
                        suma=suma+Double.parseDouble(xrp.getText());
                    }
                    break;
                case XmlPullParser.END_TAG :
                    if (xrp.getName().equals("alumno"))
                    {
                        cadena.append("\n\n");
                    }
                    if (xrp.getName().equals("nombre"))
                    {
                        cadena.append("\n");
                    }
                    if (xrp.getName().equals("nota"))
                    {
                        cadena.append("\n");
                    }
                    esNombre=false;
                    esNota=false;
                    break;
            }
            eventType = xrp.next();
        }
        cadena.append("\nMedia: "+Math.round(suma/contador));
        xrp.close();
        return cadena.toString();
    }

    public static String analizarRSS(File file) throws NullPointerException, XmlPullParserException,IOException {
        boolean dentroItem = false;
        boolean dentroTitle = false;
        StringBuilder builder = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item"))
                    {
                        dentroItem=true;
                    }
                    if ((xpp.getName().equals("title"))&(dentroItem))
                    {
                        dentroTitle=true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (dentroTitle)
                    {
                        builder.append(xpp.getText()+"\n");
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("item"))
                    {
                        builder.append("\n");
                    }
                    dentroItem=false;
                    dentroTitle=false;
                    break;
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }

    public static ArrayList<Noticia> analizarNoticias(File file) throws XmlPullParserException, IOException {
        int eventType;
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        Noticia actual = new Noticia();
        boolean dentroItem = false;
        boolean dentroTitle =false;
        boolean dentroLink=false;
        boolean dentroDescription=false;
        boolean dentropubDate=false;
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        eventType=xpp.getEventType();
        while (eventType!=XmlPullParser.END_DOCUMENT){
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (xpp.getName().equals("item"))
                    {
                        dentroItem=true;
                    }
                    if ((dentroItem)&(xpp.getName().equals("title")))
                    {
                        dentroTitle=true;
                    }
                    if ((dentroItem)&(xpp.getName().equals("link")))
                    {
                        dentroLink=true;
                    }
                    if ((dentroItem)&(xpp.getName().equals("description")))
                    {
                        dentroDescription=true;
                    }
                    if ((dentroItem)&(xpp.getName().equals("pubDate")))
                    {
                        dentropubDate=true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if (dentroTitle)
                    {
                        actual.setTitle(xpp.getText().toString());
                    }
                    if (dentroLink)
                    {
                        actual.setLink(xpp.getText().toString());
                    }
                    if (dentroDescription)
                    {
                        actual.setDescription(xpp.getText().toString());
                    }
                    if (dentropubDate)
                    {
                        actual.setPubDate(xpp.getText().toString());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (xpp.getName().equals("item"))
                    {
                        dentroItem=false;
                        //dentroDescription=false;
                        //dentroLink=false;
                        //dentropubDate=false;
                        //dentroTitle=false;
                        noticias.add(actual);
                        actual=new Noticia();

                    }
                    if ((dentroItem)&(xpp.getName().equals("title")))
                    {
                        dentroTitle=false;
                    }
                    if ((dentroItem)&(xpp.getName().equals("link")))
                    {
                        dentroLink=false;
                    }
                    if ((dentroItem)&(xpp.getName().equals("description")))
                    {
                        dentroDescription=false;
                    }
                    if ((dentroItem)&(xpp.getName().equals("pubDate")))
                    {
                        dentropubDate=false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return noticias;
    }

    public static void crearXML(ArrayList<Noticia> noticias, String fichero) throws IOException {
        FileOutputStream fout;
        fout = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fichero));
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(fout, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true); //poner tabulación
        serializer.startTag(null, "titulares");
        for (int i = 0; i < noticias.size(); i++) {
            serializer.startTag(null, "item");
            serializer.startTag(null, "titulo");
            serializer.attribute(null, "fecha", noticias.get(i).getPubDate());
            serializer.text(noticias.get(i).getTitle());
            serializer.endTag(null, "titulo");
            serializer.startTag(null, "enlace");
            serializer.text(noticias.get(i).getLink());
            serializer.endTag(null, "enlace");
            serializer.startTag(null, "descripcion");
            serializer.text(noticias.get(i).getDescription());
            serializer.endTag(null, "descripcion");
            serializer.endTag(null, "item");

        }
        serializer.endTag(null, "titulares");
        serializer.endDocument();
        serializer.flush();
        fout.close();
    }

}


