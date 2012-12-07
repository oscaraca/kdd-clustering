/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author oscaraca
 */
public class Documento {
    String nombreDocumento;
    ArrayList<Termino> terminos;
    ArrayList<Termino> terminosSinRepetir;
    ArrayList<Termino> terminosDiccionarioDocumento;

    public ArrayList<Termino> getTerminosDiccionarioDocumento() {
        return terminosDiccionarioDocumento;
    }

    public void setTerminosDiccionarioDocumento(ArrayList<Termino> terminosDiccionarioDocumento) {
        this.terminosDiccionarioDocumento = terminosDiccionarioDocumento;
    }
    
    public Documento() {
        terminos = new ArrayList();
        terminosSinRepetir = new ArrayList();
        terminosDiccionarioDocumento = new ArrayList();
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public ArrayList<Termino> getTerminos() {
        return terminos;
    }

    public ArrayList<Termino> getTerminosSinRepetir() {
        return terminosSinRepetir;
    }

    public void setTerminos(ArrayList<Termino> Terminos) {
        this.terminos = Terminos;
    }

    public void setTerminosSinRepetir(ArrayList<Termino> TerminosSinRepetir) {
        this.terminosSinRepetir = TerminosSinRepetir;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public void eliminarRepetidos(){
        for (Termino termino :terminos) {
            int resultadoBusqueda = buscarTermino(termino.getNombreTermino());
            if(resultadoBusqueda==-1) terminosSinRepetir.add(new Termino(termino.getNombreTermino(), 1));
            else {
                Termino terminoRepetido = terminosSinRepetir.get(resultadoBusqueda);
                int apariciones = terminoRepetido.getNoAparaciones();
                terminoRepetido.setNoAparaciones(apariciones+1);
            }
        }
    }
    
    public int buscarTermino(String termino) {
        for(int i=0; i<terminosSinRepetir.size(); i++) {
            if(termino.equals(terminosSinRepetir.get(i).getNombreTermino())) {
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<Termino> filtrarTerminosDiccionario(String [] terminosDiccionario) {
        
        for (String terminoDiccionario : terminosDiccionario) {
            int existeTermino = this.existeTermino(terminoDiccionario);
            if(existeTermino==-1) { //retorna -1 si el termino no esta en el diccionario, de lo contrario retorna el indice del termino en el arreglo de todos los terminos del documento
                terminosDiccionarioDocumento.add(new Termino(terminoDiccionario, 0));
            }
            else {
                terminosDiccionarioDocumento.add(terminosSinRepetir.get(existeTermino));
            }
        }
        return terminosDiccionarioDocumento;
    }
    
    public void imprimir() {
        String terminos="";
        String terminosSinRepetir="";
        for(Termino termino : this.terminos) {
            terminos += termino.getNombreTermino()+" ";
        }
        for(Termino termino : this.terminosSinRepetir) {
            terminosSinRepetir += termino.getNombreTermino()+" ";
        }
        System.out.println(this.terminos.size()+" terminos repetidos: "+terminos);
        System.out.println(this.terminosSinRepetir.size()+" terminos sin repetir: "+terminosSinRepetir);
    }
    
    private int existeTermino(String nombreTermino) {
        for (int i=0 ; i<terminosSinRepetir.size(); i++) {
            Termino terminoDocumento = terminosSinRepetir.get(i);
            if (terminoDocumento.getNombreTermino().equals(nombreTermino)) return i;
        }
        return -1;
    }
}
