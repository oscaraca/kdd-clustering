/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

/**
 *
 * @author Cesar
 */
public class pruebas {
    public static void main(String args[]) {
        double [] a = {2,4,5, 6.6, 7};
        double [] b = {2,4,5, 6.6, 7};
        
        boolean bool = true;
        for(int i=0; i<a.length; i++) {
            if(a[i]!=b[i]) bool=false;
        }
        if (bool) {
            System.out.println("son iguales");
        }
        else {
            System.out.println("no son iguales");
        }
    }
}
