
package task2;

import java.io.IOException;

/**
 * @author S.Gaidai
 */

public class App {
    
    public static void main(String[] args)  {
        
        try {
            AnalyzeFile inputFile = new AnalyzeFile("cities.txt");            
            // URL for txt file initial data
            inputFile.startAnalyzation();
        } catch (IOException ex) {
            System.err.println("Parsing Exception!");
            ex.printStackTrace();
        }
       
        
    }    
}
