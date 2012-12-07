/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Database
 */
public class Escritor {
    
    public void escribirEnArchivo(ArrayList<String> documento, String nombreArchivo) {
        System.out.println("Escribiendo en: " + nombreArchivo);
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(nombreArchivo);
            pw = new PrintWriter(fichero);
            
            for(String linea : documento) {
                pw.println(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        System.out.println("ok");
    }
}
 
