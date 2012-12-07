/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text_analyzer;

/**
 *
 * @author Oswaldo
 */

 import java.io.BufferedReader;
   import java.io.IOException;
   import java.io.InputStreamReader;
   import java.io.Reader;
   import java.util.ArrayList;
   import java.util.Set;

   import org.apache.lucene.analysis.Analyzer;
   import org.apache.lucene.analysis.LowerCaseFilter;
   import org.apache.lucene.analysis.StopFilter;
   import org.apache.lucene.analysis.TokenStream;
   import org.apache.lucene.analysis.standard.StandardFilter;
   import org.apache.lucene.analysis.standard.StandardTokenizer;

    import org.apache.lucene.analysis.*;
    import org.apache.lucene.util.Version;


public class BilingualAnalyzer extends Analyzer{
     Set<?> SPANISH_STOP_WORDS_SET;
     Set<?> ENGLISH_STOP_WORDS_SET;
     
     Version LUCENE_VERSION;
     String STOP_WORDS;
     public final static String SPANISH_DEFAULT_STOPWORD_FILE = "spanish_stop1.txt";
     public final static String ENGLISH_DEFAULT_STOPWORD_FILE = "english_stop.txt";
     
     public BilingualAnalyzer(){
         LUCENE_VERSION =Version.LUCENE_30;
         loadStopWords();     
         

    }
    
    
    
    public BilingualAnalyzer(Version matchVersion){
         LUCENE_VERSION = matchVersion;
         loadStopWords();
    }
    
    public  void loadStopWords() {
                  
      try {
        
        SPANISH_STOP_WORDS_SET= WordlistLoader.getWordSet(SpanishAnalyzer2.class, SPANISH_DEFAULT_STOPWORD_FILE);
        ENGLISH_STOP_WORDS_SET= WordlistLoader.getWordSet(SpanishAnalyzer2.class, ENGLISH_DEFAULT_STOPWORD_FILE);
        System.out.println("\n Stop words loaded successfull \n");
      } catch (IOException ex) {
        // default set should always be present as it is part of the
        // distribution (JAR)
        throw new RuntimeException("Unable to load default stopword set");
      }
      
      //System.out.print("stop" + STOP_WORDS_SET.toString());
     
                  
    }
    
    
     public final TokenStream tokenStream(String fieldName, Reader reader) {
       
         TokenStream result = new StandardTokenizer(LUCENE_VERSION, reader);       
         result = new StandardFilter(LUCENE_VERSION, result);  
         result = new LowerCaseFilter(LUCENE_VERSION, result);  
         result =  new ISOLatin1AccentFilter(result);    
         result = new StopFilter(LUCENE_VERSION, result, ENGLISH_STOP_WORDS_SET); 
         result = new StopFilter(LUCENE_VERSION, result, SPANISH_STOP_WORDS_SET); 
         result = new PorterStemFilter(result);
         result = new SpanishStemFilter(result);
         
         System.out.println("Token stream" + result);       
         return result; 
        
   }  
    
    
    public static void main(String a[]){
        SpanishAnalyzer2 an= new SpanishAnalyzer2();
        an.loadStopWords();
    }
}
