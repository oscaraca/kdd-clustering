package core;


import Archivo.ConversorPDF;
import java.awt.BorderLayout;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dash
 */
public class JPanelConPDF extends JPanel
{
    public JPanelConPDF(String ruta) 
    {
        SwingController controller = new SwingController(); 

        SwingViewBuilder factory = new SwingViewBuilder(controller); 

        JPanel viewerComponentPanel = factory.buildViewerPanel(); 

        // add copy keyboard command 
        ComponentKeyBinding.install(controller, viewerComponentPanel); 

        // add interactive mouse link annotation support via callback 
        controller.getDocumentViewController().setAnnotationCallback( 
           new org.icepdf.ri.common.MyAnnotationCallback( 
                 controller.getDocumentViewController())); 


        // Now that the GUI is all in place, we can try openning a PDF 
        
        controller.openDocument(ruta);
        

        // show the component 
        this.add( viewerComponentPanel, BorderLayout.CENTER); 
    
        setVisible(true);
        setSize(650, 650);
        
    }
    
    public static void main (String []args) {
        JFrame v = new JFrame();
        JPanelConPDF jp = new JPanelConPDF("/afs/eisc/user/pregrado/2010/oscaraca/Desktop/desarrollo de software II/data/docs/pdf/GSQA-FTPruebasFuncionales.doc");
        v.add(jp);
        v.pack();
        v.setVisible(true);
        v.setResizable(false);
    }
    
}

