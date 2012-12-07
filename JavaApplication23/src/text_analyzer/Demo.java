/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text_analyzer;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import net.sf.snowball.ext.SpanishStemmer;   

import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.LowerCaseTokenizer;

/**
 *
 * @author Oswaldo
 */
public class Demo {
    TokenStream in, out;
    SpanishStemmer stemmer;
     String result="";
  public  Demo (TokenStream stream) throws IOException {
      this.in= stream;
      stemmer = new SpanishStemmer();
      out = filter();
      
      
  }  
  
  public TokenStream  filter() throws IOException {
     
      String tmp;
      TermAttribute term = in.addAttribute(TermAttribute.class);
      System.out.println(" +++++++++++++++++++ ");
    
      while(in.incrementToken()) { 
          tmp="";
          stemmer.setCurrent(term.term());   
          stemmer.stem();
          tmp= stemmer.getCurrent();
          result = result + " " + tmp;
           System.out.println(" result  " + result);
          
     System.out.print("[-" + tmp + "-] ");    //B
    }
      TokenStream tok;
      SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_30);
      
      tok= new LowerCaseTokenizer(new StringReader(result));
     // tok = analyzer.tokenStream("contents", new StringReader(result));
       //System.out.println("\n");
      System.out.println(" result  " + result);
       System.out.println(" demo demo " + tok.toString());
      return tok;
      
  }
}
