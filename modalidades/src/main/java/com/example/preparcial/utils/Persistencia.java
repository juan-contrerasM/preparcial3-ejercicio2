package com.example.preparcial.utils;

import com.example.preparcial.model.Progama;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class Persistencia {

    //------------------------------------Rutas-------------------------------
    public static final String RUTA_ARCHIVO_LOG = "C:\\Users\\juanj\\OneDrive\\Escritorio\\preparcial\\modalidades\\src\\main\\resources\\com\\example\\preparcial\\archivos\\log";
    public static final String RUTA_ARCHIVO_MODELO_BANCO_XML = "C:\\Users\\juanj\\OneDrive\\Escritorio\\preparcial\\modalidades\\src\\main\\resources\\com\\example\\preparcial\\archivos\\modelo.xml";

    public Persistencia() {
    }
//-----------------------------Log------------------------------------------
    public static void guardaRegistroLog(String mensaje, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensaje, nivel, accion, "C:\\Users\\juanj\\OneDrive\\Escritorio\\preparcial\\modalidades\\src\\main\\resources\\com\\example\\preparcial\\archivos\\log");
    }
//-----------------------------------------------archivos xml--------------------------------------
    public static ArrayList<Progama> cargarRecursoBancoXML() {
        ArrayList<Progama> lista = null;

        try {
            lista = (ArrayList)ArchivoUtil.cargarRecursoSerializadoXML("C:\\Users\\juanj\\OneDrive\\Escritorio\\preparcial\\modalidades\\src\\main\\resources\\com\\example\\preparcial\\archivos\\modelo.xml");
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return lista;
    }
    public static void guardarRecursoBancoXML(ArrayList<Progama> LISTADEPROGAMAS) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML("C:\\Users\\juanj\\OneDrive\\Escritorio\\preparcial\\modalidades\\src\\main\\resources\\com\\example\\preparcial\\archivos\\modelo.xml", LISTADEPROGAMAS);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
//--------------------------------cargar archivos modalidades--------------------------------------------------------
    public static ArrayList<String> archivoPropiedades() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("modalidades", new Locale("", ""));
        ArrayList<String>modalidades=new ArrayList<>();
        modalidades.add(resourceBundle.getString("modalidad1"));
        modalidades.add(resourceBundle.getString("modalidad2"));
        return modalidades;
    }


}
