/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.File;

/**
 *
 * @author oscaraca
 */
public class Rank {
    String nombreDocumento;
    double similitudCoseno;
    File file;

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public Rank() {
    }

    public Rank(String nombreDocumento, double similitudCoseno, File file) {
        this.nombreDocumento = nombreDocumento;
        this.similitudCoseno = similitudCoseno;
        this.file = file;
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
