/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import core.Documento;
import java.util.ArrayList;

/**
 *
 * @author Cesar
 */
public class Kmeans {
    
    double [][]matrizIndexada;
    ArrayList<Documento> documentosIndexados;
    ArrayList<Cluster> clusters;
    
    public Kmeans(double [][] matrizIndexada, ArrayList<Documento> documentosIndexados) {    
        this.matrizIndexada = matrizIndexada;
        this.documentosIndexados = documentosIndexados;
        clusters = new ArrayList();
    }
    
    private double distanciaEuclideana(double[] vector1, double[] centroide) {
        double resultado=0;
        if(vector1.length!=centroide.length) {
            System.out.println("tamaño distinto de los vectores para calcular la distancia euclidiana");
            return 0;
        }
        else {
            System.out.println("calculando distancia euclideana");
            double sumatoriaDiferencias = 0;
            for(int i=0; i<vector1.length; i++) {
                double diferencia = vector1[i]-centroide[i];
                sumatoriaDiferencias+= Math.pow(diferencia, 2);
            }
            resultado = Math.sqrt(sumatoriaDiferencias);
        }
        return resultado;
    }
    
    public void groupByKMeans(int k) {
        iniciarClustersYCentroides(k);
        boolean centroidesIguales = false;
        int iteraccion = 1 ;
        while(!centroidesIguales) {
            System.out.println("Iteraccion " + iteraccion);
            asignarDocumentosAClusters(k);
            for (int i=0; i<clusters.size(); i++) {
                double [] centroideAnterior = clusters.get(i).getCentroide();
                clusters.get(i).calcularNuevoCentroide();
                double[] nuevoCentroide = clusters.get(i).getCentroide();
                centroidesIguales = centroidesIguales(centroideAnterior,nuevoCentroide);
                if (centroidesIguales) {
                    System.out.println("Centroides iguales"+ i);
                    break;
                }
            }
            iteraccion++;
        }
        
        for(int i=0; i<clusters.size(); i++) {
            System.out.println("Cluster "+i+" "+clusters.get(i).getDocumentos().size() +" Documentos:");
            Cluster cluster = clusters.get(i);
            for (int j=0; j<cluster.getDocumentos().size(); j++) {
                System.out.println(cluster.getDocumentos().get(j).getNombreDocumento());
            }
        }
    }
    
    private void asignarDocumentosAClusters(int k) {
        double [] distancias = new double[k];
        for (int i=0; i<matrizIndexada.length; i++) {
            boolean primeraIteraccion = true;
            double menorDistancia = 0;
            for (int m=0; m<k ; m++) {
                double dist = distanciaEuclideana(matrizIndexada[i], clusters.get(m).getCentroide());
                distancias[m] = dist;
                if(primeraIteraccion) {
                    menorDistancia=dist;
                    primeraIteraccion=false;
                }
                if(menorDistancia>dist) {
                    menorDistancia=dist;
                }
            }
            for (int n=0; n<k; n++) {
                double dist = distanciaEuclideana(matrizIndexada[i], clusters.get(n).getCentroide());
                
                if(dist==menorDistancia) {
                    clusters.get(n).reiniciarDocumentos();
                    clusters.get(n).addDocumento(matrizIndexada[i], documentosIndexados.get(i));
                }
            }
        }
    }
    
    private boolean centroidesIguales (double [] c1, double [] c2) {
        boolean bool = true;
        for (int i=0; i<c1.length; i++) {
            if(c1[i]!=c2[i]) {
                bool = false;
            }
        }
        return bool;
    }
    
    private void iniciarClustersYCentroides(int k) {
        for (int i=0; i<k; i++){
            clusters.add(new Cluster());
        }
        for (int i=0; i<k; i++){
            clusters.get(i).setCentroide(matrizIndexada[i]); 
        }
    }
    
}
