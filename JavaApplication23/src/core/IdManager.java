/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;


/**
 *
 * @author Oswaldo
 */
public class IdManager {
    int docID=0;
    
    public IdManager(){
       
    };
    
    
    
    public String  getNextId(){  
        int tmp;
        tmp=docID;       
        docID ++; //Se actualiza el id para el proximo documento;
        return tmp+"";
    }
    
    //Obtiene el proximo id de los documentos de la Base de datos
    public void configure(){
        
    }
    
    public void updateNextDocumentID(){        
       
        System.out.print("Doc ID final: "+ docID) ;
    }
    
    public int getDocID(){
        return docID;
    }//end

    
    //obtiene el ultimo doc id analizado en la anotacion semantica, lo consulta en la BD
   
    
    //Actualiza en la BD el campo last_doc_id_analyzed
    public void updateLastDocIDAnalyzed(int lastID){
        
    }//end
    
    
    
}//end class
