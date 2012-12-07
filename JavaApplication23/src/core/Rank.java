/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author oscaraca
 */
public class Rank {
    String nombreDocumento;
    double similitudCoseno;

    public Rank() {
    }

    public Rank(String nombreDocumento, double similitudCoseno) {
        this.nombreDocumento = nombreDocumento;
        this.similitudCoseno = similitudCoseno;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public double getSimilitudCoseno() {
        return similitudCoseno;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public void setSimilitudCoseno(double similitudCoseno) {
        this.similitudCoseno = similitudCoseno;
    }
    
}
