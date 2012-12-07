/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text_analyzer;

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


public class SpanishAnalyzer2 extends Analyzer{
     Set<?> STOP_WORDS_SET;
     Version LUCENE_VERSION;
     String STOP_WORDS;
     public final static String DEFAULT_STOPWORD_FILE = "spanish_stop1.txt";
     
     public SpanishAnalyzer2(){
         LUCENE_VERSION =Version.LUCENE_30;
         loadStopWords();
         

    }
    
    public SpanishAnalyzer2(Set stopWord){
         STOP_WORDS_SET = stopWord;
         LUCENE_VERSION =Version.LUCENE_30;
    }
    
    public SpanishAnalyzer2(Version matchVersion){
         LUCENE_VERSION = matchVersion;
         loadStopWords();
    }
    
    public  void loadStopWords() {
                  
      try {
        
        STOP_WORDS_SET= WordlistLoader.getWordSet(SpanishAnalyzer2.class, DEFAULT_STOPWORD_FILE);
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
         result = new StopFilter(LUCENE_VERSION, result, STOP_WORDS_SET); 
         //result = new SpanishStemFilter(result);    
         //result = new org.apache.lucene.analysis.es.SpanishLightStemFilter(result);
                 
         return result; 
        
   }  
    
    
    public static void main(String a[]){
        SpanishAnalyzer2 an= new SpanishAnalyzer2();
        an.loadStopWords();
    }
}
