/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text_analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class EnglishAnalyzer extends Analyzer {
    Set<?> STOP_WORDS_SET;
    Version LUCENE_VERSION;
    public final static String DEFAULT_STOPWORD_FILE = "english_stop.txt";
    
    public EnglishAnalyzer(){
         STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET; 
         LUCENE_VERSION =Version.LUCENE_30;

    }
    
    
    
    public EnglishAnalyzer(Version matchVersion){
         LUCENE_VERSION = matchVersion;
         STOP_WORDS_SET = StopAnalyzer.ENGLISH_STOP_WORDS_SET; 
    }
    
     public EnglishAnalyzer(Version matchVersion, String FileName){
         LUCENE_VERSION = matchVersion;
         loadStopWords();
    }
    
     public  void loadStopWords() {
                  
      try {
        
        STOP_WORDS_SET= WordlistLoader.getWordSet(EnglishAnalyzer.class, DEFAULT_STOPWORD_FILE);
      } catch (IOException ex) {
        // default set should always be present as it is part of the
        // distribution (JAR)
        throw new RuntimeException("Unable to load default stopword set");
      }
      
      System.out.print("English stop  words from file: \n" + STOP_WORDS_SET.toString());
      System.out.print("\n \n");
     
                  
    }
    
    
  public final TokenStream tokenStream(String fieldName, Reader reader) {
       
         TokenStream result = new StandardTokenizer(LUCENE_VERSION, reader);       
         result = new StandardFilter(result);  
         result = new LowerCaseFilter(result);  
         result = new StopFilter(LUCENE_VERSION, result, STOP_WORDS_SET);
         result = new PorterStemFilter(result);
         //System.out.println("\n  *****+++++++++++++++++++");
         //System.out.println("Stopwords: " + STOP_WORDS_SET);
          //System.out.println("\n");
         
         return result; 
        
   }  
    
}
