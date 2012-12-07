package text_analyzer;

import java.io.IOException;   
    
 import net.sf.snowball.ext.SpanishStemmer;   
    
 import org.apache.lucene.analysis.Token;   
 import org.apache.lucene.analysis.TokenFilter;   
 import org.apache.lucene.analysis.TokenStream;   
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

    
 /**   
  * Spanish stemming algorithm.  
  */   
 public final class SpanishStemFilter extends TokenFilter {   
        
  //private final SpanishStemmer stemmer = new net.sf.snowball.ext.SpanishStemmer();
  SpanishStemmer stemmer = new net.sf.snowball.ext.SpanishStemmer();
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
  private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);
  TermAttribute term =addAttribute(TermAttribute.class);
  
  public SpanishStemFilter(TokenStream in) {
    super(in);
  }

  @Override
  
  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
     //String originalTerm = termAtt.buffer().toString();
    
      String originalTerm = term.term();
      
      stemmer.setCurrent(originalTerm);
      stemmer.stem();
      
      String finalTerm = stemmer.getCurrent();
      //System.out.println("\n");
       //System.out.println("Original :" + originalTerm + " final " + finalTerm);
      // Don't bother updating, if it is unchanged.
      if (!originalTerm.equals(finalTerm))
        term.setTermBuffer(finalTerm);
      return true;
    } else {
      return false;
    }
  }
  
        
 }   