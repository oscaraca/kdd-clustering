/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author oscaraca
 */
class Termino {
    String nombreTermino;
    int noAparaciones;

    public Termino() {
    }

    public Termino(String nombreTermino, int noAparaciones) {
        this.nombreTermino = nombreTermino;
        this.noAparaciones = noAparaciones;
    }

    public int getNoAparaciones() {
        return noAparaciones;
    }

    public String getNombreTermino() {
        return nombreTermino;
    }

    public void setNoAparaciones(int noAparaciones) {
        this.noAparaciones = noAparaciones;
    }

    public void setNombreTermino(String nombreTermino) {
        this.nombreTermino = nombreTermino;
    }
    
}
