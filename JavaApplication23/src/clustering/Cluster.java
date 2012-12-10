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
public class Cluster {
    double[] centroide;
    double[] nuevoentroide;
    ArrayList<double[]> vectoresDocumentos;
    ArrayList<Documento> documentos;

    public Cluster() {
    }

    public void setNuevoentroide(double[] nuevoentroide) {
        this.nuevoentroide = nuevoentroide;
    }

    public double[] getNuevoentroide() {
        return nuevoentroide;
    }

    public double[] getCentroide() {
        return centroide;
    }
    
    public double[] getCentroideAnterior() {
        double [] centroide = new double[this.centroide.length];
        for(int i=0; i<this.centroide.length; i++) {
            centroide[i] = this.centroide[i];
        }
        return centroide;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public ArrayList<double[]> getVectoresDocumentos() {
        return vectoresDocumentos;
    }

    public void setCentroide(double[] centroide) {
        this.centroide = centroide;
    }
    
    public void setCentroide(int[] centroide) {
        for (int i=0; i<centroide.length; i++) {
            this.centroide[i] = centroide[i];
        }
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }
    
    public void reiniciarDocumentos() {
        this.vectoresDocumentos = new ArrayList();
        this.documentos = new ArrayList();
    }
    
    public void addDocumento(double[] vectorDocumento, Documento documento) {
        this.vectoresDocumentos.add(vectorDocumento);
        this.documentos.add(documento);
    }

    public void calcularNuevoCentroide() {
        for (int j=0 ;j<29; j++) {
            double promedio;
            double suma = 0;
            for(int i=0; i<vectoresDocumentos.size(); i++) {
                suma+=vectoresDocumentos.get(i)[j];
            }
            promedio = suma/vectoresDocumentos.size();
            centroide[j] = promedio;
        }
    }
}
