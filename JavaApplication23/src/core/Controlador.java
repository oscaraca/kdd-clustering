/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import Archivo.Escritor;
import clustering.Kmeans;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.apache.tika.config.TikaConfig;

/**
 *
 * @author oscaraca
 */
public class Controlador {
    
    Index index;
    Kmeans kmeans;
    ArrayList<Rank> documentosRankeados;

    public ArrayList<Rank> getDocumentosRankeados() {
        return documentosRankeados;
    }
    
    public static void main(String [] args) throws IOException, Exception {

    String busqueda = "prueba caja";
    Controlador p = new Controlador();
    //p.pruebaIndexarYBuscar(busqueda);
    p.pruebaIndexarYEscribirMatriz();
    }

    public Controlador() {
        index = new Index();
    }
    
    
    public ArrayList<Documento> pruebaIndexarCarpeta(String dataDir) {
        try {
            TikaConfig config = TikaConfig.getDefaultConfig();  //3

            long start = new Date().getTime();
            TextIndexer indexer = new TextIndexer();  


            ArrayList<Documento> documentosIndexados = indexer.index(dataDir, null);
            int numIndexed = documentosIndexados.size();
            //indexer.close();
            long end = new Date().getTime();

            System.out.println("Indexing " + numIndexed + " files took "
            + (end - start) + " milliseconds");

            index = new Index(documentosIndexados);
            index.crearMatriz();
            return documentosIndexados;
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    public ArrayList<Rank> pruebaBuscar(String busqueda, ArrayList<Documento> documentosIndexados) throws IOException, Exception {
        Buscador buscador = new Buscador(index.getMatriz());
        documentosRankeados = new ArrayList();
        documentosRankeados = buscador.rankearDocumentos(documentosIndexados, busqueda);
        String documentosRelacionados = "";
        return documentosRankeados;
    }
    
    public JPanel seleccionarDocumento(File file) {
        //mostrarDocumentosRelacionados(file);
        JPanel panelConPDF = crearPanelConDocumento(file);
        return panelConPDF;
    }
    
    public void pruebaIndexarYEscribirMatriz() throws IOException, Exception {
        TikaConfig config = TikaConfig.getDefaultConfig();  //3

        String  dataDir="/afs/eisc/user/pregrado/2010/oscaraca/Desktop/kdd/proyecto clustering/data/docs/pdf";

        long start = new Date().getTime();
        TextIndexer indexer = new TextIndexer();  


        ArrayList<Documento> documentosIndexados = indexer.index(dataDir, null);
        int numIndexed = documentosIndexados.size();
        //indexer.close();
        long end = new Date().getTime();

        System.out.println("Indexing " + numIndexed + " files took "
        + (end - start) + " milliseconds");

        Index index = new Index(documentosIndexados);
        index.crearMatriz();
        double[][] matriz = index.getMatriz();
        
       index.guardarMatrizEnArchivo("archivo matriz.osc");
    }
    
    public void applyKMeans(ArrayList<Documento> documentosIndexados) {
        kmeans = new Kmeans(index.getMatriz(), documentosIndexados);
        kmeans.groupByKMeans(5);
    }
    
    public ArrayList<Documento> mostrarDocumentosRelacionados(File file, JList listaDocumentosRelacionados) {
        ArrayList<Documento> documentosRelacionados = kmeans.buscarDocumentosEnCluster(file);
        System.out.println("Documentos relacionados con "+file.getName()+":");
        DefaultListModel modelo = new DefaultListModel();
        for (int i=0; i<documentosRelacionados.size(); i++) {
            System.out.println(documentosRelacionados.get(i).getFile().getName());
            modelo.addElement(documentosRelacionados.get(i).getFile().getName());
        }
        
        listaDocumentosRelacionados.setModel(modelo);
        return documentosRelacionados;
    }
    
    public JPanel crearPanelConDocumento(File file) {
        System.out.println("Construyendo JPanel con "+file.getPath());
        JPanelConPDF jp = new JPanelConPDF(file.getPath());
        return jp;
    }
}
