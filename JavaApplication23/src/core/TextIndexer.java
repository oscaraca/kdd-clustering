package core;

/**
 * Copyright Manning Publications Co.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific lan      
*/

import com.drew.metadata.Directory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.config.TikaConfig;      

import org.xml.sax.ContentHandler;
import javax.swing.JOptionPane;
import text_analyzer.*;
import java.io.*;
import javax.swing.JTextArea;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;





public class TextIndexer  {
   private IndexWriter writer;
  private boolean DEBUG = false;                     //1
  private IdManager idManager;
  

  static Set<String> textualMetadataFields           //2
        = new HashSet<String>();                     //2
  static {                                           //2
    textualMetadataFields.add(Metadata.TITLE);       //2
    textualMetadataFields.add(Metadata.AUTHOR);      //2
    textualMetadataFields.add(Metadata.COMMENTS);    //2
    textualMetadataFields.add(Metadata.KEYWORDS);    //2
    textualMetadataFields.add(Metadata.DESCRIPTION); //2
    textualMetadataFields.add(Metadata.SUBJECT);     //2
  }

 

  public TextIndexer() throws IOException {
        
  }
  
  public void setIDManager(IdManager idMan){
      this.idManager=idMan;
  }

   
  public  String getDocumentText(File f) throws Exception {
    //String pruebaDoc;//sirve para colocar el archivo de entrada temporalmente, borrar luego, OJO solo es temporal
    
    String docId;
    String documentContent="";
    Metadata metadata = new Metadata();
    metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());   // 4
   
    InputStream is = new FileInputStream(f);      // 5
    Parser parser = new AutoDetectParser();       // 6
    ContentHandler handler = new BodyContentHandler(1000000); // Se puede agregar un entero grande para aumentar la capacidad   Ejemplo new BodyContentHandler(1000000)
    
    ParseContext context = new ParseContext();   // 8
    context.set(Parser.class, parser);           // 8
    
    try {
      parser.parse(is, handler, metadata,      // 9
                   new ParseContext());        // 9
    }catch(Exception e) {
        System.out.println(e.getMessage());
    } finally {
      is.close();
    }

    Document doc = new Document();
   
    doc.add(new Field("contents", handler.toString(),           // 10
                      Field.Store.NO, Field.Index.ANALYZED));   // 10
    
    //documentContent se usara para crear el archivo que será anotado semanticamente
    documentContent=handler.toString();
     
    return documentContent;
  }//end getDocument
  
    

 
 
  public ArrayList<Documento> index(String dataDir, FileFilter filter)
    throws Exception {
    String originalDoc="";
    File[] files = new File(dataDir).listFiles();
    int i=0;
    ArrayList<Documento> documentos = new ArrayList();
    for (File f: files) {
      if (!f.isDirectory() &&
          !f.isHidden() &&
          f.exists() &&
          f.canRead() && (filter == null || filter.accept(f))) {
          Documento doc = new Documento();
          ArrayList<Termino> terminos = new ArrayList();
          doc.setNombreDocumento(f.getName());
          System.out.println("Nombre del documento"+i+": "+f.getName()+"\nParsing document...");
          originalDoc = getDocumentText(f);
          System.out.println("Parsing done");
          String tokensDoc= analyze(originalDoc);
          String tokens[] = tokensDoc.split(" ");
          System.out.println("Doc id: " + i+ " " + tokensDoc);
          System.out.println("\n");
          
          for (String token: tokens){//Añadiendo terminos con repeticiones al arreglo de terminos del documento
              terminos.add(new Termino(token, 0));
          }
          doc.setTerminos(terminos);
          doc.eliminarRepetidos();
          doc.imprimir();
          
          String dicc = "Colombia Nicaragua Mar Presidente pais soberania isla mineria datos medicina weka algoritmo pruebas software recuperación información documentos algoritmos ordenamiento sort burbuja quick merge estimacion proyectos técnicas esfuerzo costos sistema";
          String [] diccionario = new TextIndexer().analyze(dicc).split(" ");
          
          doc.filtrarTerminosDiccionario(diccionario);
          documentos.add(doc);
          i++;
      }
    }
    return documentos;
  }
 
   public String analyze(String text) throws IOException {
     
      String textProcesed="";
      textProcesed = getTokens(new BilingualAnalyzer(Version.LUCENE_30), text); // B   
      //AnalyzerUtils.displayTokensWithFullDetails(analyzer, text);
      
      System.out.println("\n");
      return textProcesed;
   }
   
   public String getTokens(Analyzer analyzer, String text) throws IOException {
            
        String tokens1="";
        TokenStream stream=analyzer.tokenStream("contents", new StringReader(text));
    
          TermAttribute term = stream.addAttribute(TermAttribute.class);
        while(stream.incrementToken()) {
            System.out.print("[" + term.term() + "] ");    //B
            tokens1= tokens1+  term.term() +" ";
    
        }
    return tokens1;
  }
   
   public static void main(String[] args) throws Exception {
    
      
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
    String textoMatriz = "";
    double matriz[][] = index.getMatriz();
    JOptionPane.showMessageDialog(null, "tamaño i: "+matriz.length+" tamaño j: "+matriz[1].length);
    for(int i=0; i<matriz.length; i++) {
        for(int j=0; j<matriz[i].length; j++) {
            textoMatriz+=matriz[i][j]+"\t";
        }
        textoMatriz+="\n";
    }
    JTextArea area = new JTextArea(textoMatriz);
    JOptionPane.showMessageDialog(null, area);
    System.out.println(textoMatriz);
  }
   
}//end TikaIndexer

