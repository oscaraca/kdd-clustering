/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Archivo.Escritor;
import Archivo.Lector;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author oscaraca
 */
public class Index {
    static double [][] matriz;
    ArrayList<Documento> documentos;
    String[] diccionario;

    public Index() {
    }

    public Index(ArrayList<Documento> documentos) throws IOException {
        this.documentos = documentos;
        String dicc = "Colombia Nicaragua Mar Presidente pais soberania isla mineria datos medicina weka algoritmo pruebas software recuperación información documentos algoritmos ordenamiento sort burbuja quick merge estimacion proyectos técnicas esfuerzo costos sistema";
        diccionario = new TextIndexer().analyze(dicc).split(" ");
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public double[][] getMatriz() {
        return matriz;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }

    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }

    public void crearMatriz() {
        matriz = new double[documentos.size()][diccionario.length];
        System.out.println("matriz de tamaño "+documentos.size()+"x"+diccionario.length);
        System.out.println();
        double totalDocumentos = documentos.size();
        for(int i=0; i<totalDocumentos; i++) {
            System.out.println("documento "+i+": "+documentos.get(i).getNombreDocumento());
            Documento documentoActual = documentos.get(i);
            
            double frecuenciaMaximaDoc = frecuenciaMaximaDocumento(documentoActual);
            
            for(int j=0; j<diccionario.length; j++) {
                Termino term = documentoActual.getTerminosDiccionarioDocumento().get(j);
                double frecuenciaTermino = term.getNoAparaciones();
                double documentosDondeApareceTermino = documentosDondeApareceTermino(j);
                double cocienteFrecuenciaInversa = totalDocumentos/documentosDondeApareceTermino;
                double frecuenciaInversa = Math.log(cocienteFrecuenciaInversa); if (frecuenciaInversa<0) JOptionPane.showMessageDialog(null, "frecuencia inversa de "+term.getNombreTermino()+" "+frecuenciaInversa);                                
                double pesoGlobalTermino = (frecuenciaTermino/frecuenciaMaximaDoc)*frecuenciaInversa;

                matriz[i][j] = pesoGlobalTermino;
                System.out.println(term.getNombreTermino()+"#"+pesoGlobalTermino);
            }
            System.out.println("");
        }
    }
    
    public void guardarMatrizEnArchivo(String nombreArchivo) {
        ArrayList<String> lineasMatrizIndexada = new ArrayList();
        for(int i=0 ; i<matriz.length; i++) {
            String filaMatriz = "";
            for(int j=0; j<matriz[i].length; j++) {
                filaMatriz+=matriz[i][j]+" ";
            }
            lineasMatrizIndexada.add(filaMatriz);
        }
        new Escritor().escribirEnArchivo(lineasMatrizIndexada, nombreArchivo);
    }
    
    public void cargarMatrizDesdeArchivo(String nombreArchivo) {
        ArrayList<String> lineasMatrizIndexada = new Lector().leerArchivo(nombreArchivo);
        for(int i=0; i<lineasMatrizIndexada.size(); i++) {
            String [] filaMatrizString = lineasMatrizIndexada.get(i).split(" ");
            
            for (int j=0; j<filaMatrizString.length; j++) {
                double datoMatriz = Double.parseDouble(filaMatrizString[j]);
                matriz[i][j] = datoMatriz;
            }
        }
        System.out.println("carga de matriz finalizada");
    }
    
    private double frecuenciaMaximaDocumento(Documento documento) {
        ArrayList<Termino> terminosDiccionariDocumento = documento.getTerminosDiccionarioDocumento();
        double maximo = 0;
        for (Termino terminoDiccionario : terminosDiccionariDocumento) {
            if(terminoDiccionario.getNoAparaciones()>maximo) {
                maximo = terminoDiccionario.getNoAparaciones();
            }
        }
        if (maximo ==0) {
            JOptionPane.showMessageDialog(null, "El documento no tiene ninguna de las palabras");
        } 
        return maximo;
    }
    
    private double documentosDondeApareceTermino(int indiceTermino) {
        double noDocumentos = 0;
        for (int i=0; i<documentos.size(); i++) {
            ArrayList<Termino> terminosDiccionarioDocumento = documentos.get(i).getTerminosDiccionarioDocumento();
            if (terminosDiccionarioDocumento.get(indiceTermino).getNoAparaciones() >0) {
                noDocumentos++;
            }
        }
        if (noDocumentos==0) {
            noDocumentos =1;
            System.out.println("el termino "+indiceTermino+" no se encuentra en ningun documento");
        }
        return noDocumentos;
    }
    
}
