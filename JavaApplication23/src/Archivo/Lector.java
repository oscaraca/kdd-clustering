/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Database
 */
public class Lector {
    
    public ArrayList<String> leerArchivo(String nombreArchivo) {
        ArrayList<String> documento = new ArrayList();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "nada";
        try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (nombreArchivo);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         
         while((linea=br.readLine())!=null) documento.add(linea) ;
         
         return documento;
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
        return documento;
    }
}
