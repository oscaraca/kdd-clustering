/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscaraca
 */
public class Buscador {
    
    double matriz[][];
    String consulta;


    public Buscador(double[][] matriz) {
        this.matriz = matriz;
        this.consulta = consulta;
    }

    public String getConsulta() {
        return consulta;
    }

    public double[][] getMatriz() {
        return matriz;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public void setMatriz(double[][] Matriz) {
        this.matriz = Matriz;
    }
    
    public ArrayList<Rank> rankearDocumentos(ArrayList<Documento> documentos, String consulta) {
        ArrayList<Rank> rank = new ArrayList();
        for (int i=0; i<matriz.length; i++) {
            double similitud = similitudCoseno(matriz[i], consulta);
            rank.add(new Rank(documentos.get(i).getNombreDocumento(), similitud, documentos.get(i).getFile())); 
        }
        
        //Ordenar. Selection-Sort
        
        for(int i=0; i<rank.size(); i++)
        {
            int jMax=i;
            
            for(int j=i+1; j<rank.size(); j++)
            {
                if(rank.get(j).getSimilitudCoseno()>rank.get(jMax).getSimilitudCoseno())
                {
                    jMax=j;
                }
            }
            
            if(jMax!= i)
            {
                String tempDocumento=documentos.get(i).getNombreDocumento();
                Rank tempRank=rank.get(i);
                
                rank.set(i, rank.get(jMax));
                rank.set(jMax, tempRank);
                
                //documentos[i]=documentos[jMax];
                //documentos[jMax]=tempDocumento;
            }
        }
        
        ArrayList<Rank> rankFiltrado = new ArrayList();
        for (int i=0; i<rank.size(); i++) { //Filtra los que tengan similitud coseno mayor a 0.5
            if(rank.get(i).getSimilitudCoseno()>0.5) {
                rankFiltrado.add(rank.get(i));
            }
        }
        
        for (Rank rank1 : rankFiltrado) {
            System.out.println(rank1.getNombreDocumento()+" "+rank1.getSimilitudCoseno());
        }
        return rankFiltrado;
    }
    
    private double similitudCoseno(double [] vectorDocumento, String consulta) {
        System.out.println("caculando similitud para los vectores");
        double vectorConsulta[] = construirVectorConsulta(consulta);
        double numerador = productoPunto(vectorDocumento, vectorConsulta);
        double denominador = normaVector(vectorDocumento)*normaVector(vectorConsulta);
        mostrarVector(vectorDocumento);
        mostrarVector(vectorConsulta);
        double resultado = numerador/denominador;
        return resultado;
    }
    
    private void mostrarVector(double [] vector) {
        String stringVector="( ";
        for(int i=0 ; i<vector.length; i++) {
            stringVector+=vector[i]+" ";
        }
        stringVector+=")";
        System.out.println(stringVector);
    }
    
    public double [] construirVectorConsulta(String consulta) {
        String dicc = "Colombia Nicaragua Mar Presidente pais soberania isla mineria datos medicina weka algoritmo pruebas software recuperación información documentos algoritmos ordenamiento sort burbuja quick merge estimacion proyectos técnicas esfuerzo costos sistema";
        try {
            String [] diccionario = new TextIndexer().analyze(dicc).split(" ");
            String [] terminosConsulta = new TextIndexer().analyze(consulta).split(" ");
            double [] vectorConsulta = new double[diccionario.length];
            for (int i=0; i<diccionario.length; i++) {
                int existeTermino = 0; //Si el termino no existe el vector de la consulta debe tener un cero en esa palabra
                for(int j=0; j<terminosConsulta.length; j++) {
                    if(diccionario[i].equals(terminosConsulta[j])) existeTermino=1;
                }
                vectorConsulta[i]=existeTermino;
            }
            return vectorConsulta;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    private double normaVector(double[] vector)
    {
        double resul=0;
        
        for(int i=0; i<vector.length; i++)
        {
            resul+=Math.pow(vector[i], 2);
        }
        return Math.sqrt(resul);
    }
    private double productoPunto(double[] vecDoc, double[] vecConsulta)
    {
        double resul=0;
        for(int i=0; i<vecDoc.length; i++)
        {
            resul+=vecDoc[i]*vecConsulta[i];
        }
        return resul;
    }
}
