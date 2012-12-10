package Archivo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import java.io.File;

/**
 *
 * @author oscaraca
 */
public class ConversorPDF {

    public ConversorPDF() {
    }
    
    public void convert(File input, File output) throws Exception {

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        connection.connect();
        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(input, output);
        connection.disconnect();
        
    }
}
