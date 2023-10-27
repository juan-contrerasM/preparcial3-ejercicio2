
package com.example.preparcial.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtil {
    static String fechaSistema = "";

    public ArchivoUtil() {
    }

    //------------------------------ARCHIVOS TXT-----------------------------------

    public static void guardarArchivo(String ruta, String contenido, Boolean flagAnexarContenido) throws IOException {
        FileWriter fw = new FileWriter(ruta, flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }

    public static ArrayList<String> leerArchivo(String ruta) throws IOException {
        ArrayList<String> contenido = new ArrayList();
        FileReader fr = new FileReader(ruta);
        BufferedReader bfr = new BufferedReader(fr);
        String linea = "";

        while((linea = bfr.readLine()) != null) {
            contenido.add(linea);
        }

        bfr.close();
        fr.close();
        return contenido;
    }
    //----------------------LOG-------------------------------

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler = null;
        cargarFechaSistema();

        try {
            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 2:
                    LOGGER.log(Level.WARNING, accion + "," + mensajeLog + "," + fechaSistema);
                    break;
                case 3:
                    LOGGER.log(Level.SEVERE, accion + "," + mensajeLog + "," + fechaSistema);
            }
        } catch (SecurityException var12) {
            LOGGER.log(Level.SEVERE, var12.getMessage());
            var12.printStackTrace();
        } catch (IOException var13) {
            LOGGER.log(Level.SEVERE, var13.getMessage());
            var13.printStackTrace();
        } finally {
            fileHandler.close();
        }

    }
    //---------------------------------FECHA---------------------------------

    private static void cargarFechaSistema() {
        String diaN = "";
        String mesN = "";
        String añoN = "";
        Calendar cal1 = Calendar.getInstance();
        int dia = cal1.get(5);
        int mes = cal1.get(2) + 1;
        int año = cal1.get(1);
        int hora = cal1.get(10);
        int minuto = cal1.get(12);
        if (dia < 10) {
            diaN = diaN + "0" + dia;
        } else {
            diaN = diaN + dia;
        }

        if (mes < 10) {
            mesN = mesN + "0" + mes;
        } else {
            mesN = mesN + mes;
        }

        fechaSistema = "" + año + "-" + mesN + "-" + diaN;
    }

    //-----------------------------------ARCHIVOS SERIALIZADOS------------------------
    public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
        Object aux = null;
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));
            aux = ois.readObject();
        } catch (Exception var7) {
            throw var7;
        } finally {
            if (ois != null) {
                ois.close();
            }

        }

        return aux;
    }

    public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws Exception {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception var7) {
            throw var7;
        } finally {
            if (oos != null) {
                oos.close();
            }

        }

    }
//---------------------------------------------ARCHIVOS XML-----------------------------------
    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {
        XMLDecoder decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        Object objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;
    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {
        XMLEncoder codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();
    }
}
