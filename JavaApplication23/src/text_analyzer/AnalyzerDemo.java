package text_analyzer;

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

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import java.io.IOException;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;
import org.tartarus.snowball.SnowballStemmer;



// From chapter 4

/**
 * Adapted from code which first appeared in a java.net article
 * written by Erik
 */
public class AnalyzerDemo {
  private static final String[] examples = {
    " El jovencito está jugando con otro joven en la clase de computacion con su computador",
    "XY&Z Corporation - xyz@example.com",
    "El niño está caminando,  la jugando, leer y leyendo los LIBROS de la profesora Asunción bibliotecario algoritmos",
    "Velocidad, cansancio, computacion, computadora, privacidad"
  };
  
  

  private static final Analyzer[] analyzers = new Analyzer[] { 
    //new WhitespaceAnalyzer(),
    //new SimpleAnalyzer(),
    //new StopAnalyzer(Version.LUCENE_30),
    //new StandardAnalyzer1(Version.LUCENE_30),
    //new EnglishAnalyzer(Version.LUCENE_30),     
    //new SpanishAnalyzer2(Version.LUCENE_30),
    //new SpanishAnalyzer2(Version.LUCENE_30),
    //new SpanishAnalyzer3(),
    //new SpanishAnalyzer4(),
    new BilingualAnalyzer(Version.LUCENE_30)
    
     
  };
     

    
      

  public static void main(String[] args) throws IOException {

    String[] strings = examples;
    if (args.length > 0) {    // A
      strings = args;
    }

    for (String text : strings) {
      analyze(text);
    }
  }

  private static void analyze(String text) throws IOException {
    
    for (Analyzer analyzer : analyzers) {
      String name = analyzer.getClass().getSimpleName();
      System.out.println("  " + name + ":");
      System.out.print("    ");
      AnalyzerUtils.displayTokens1(analyzer, text); // B   
      //AnalyzerUtils.displayTokensWithFullDetails(analyzer, text);
      
      System.out.println("\n");
    }
  }
  
  
  
   
 
}


// #A Analyze command-line strings, if specified
// #B Real work done in here
