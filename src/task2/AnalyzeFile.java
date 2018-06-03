
package task2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author S.Gaidai
 */
public class AnalyzeFile {
    
    private BufferedReader cin;
    private StringTokenizer tokenizer;    
    private int cities; // total number of cities in test
    private int testsNumber;
    private String [] names; // array for the city names    
    private ArrayList<Integer> adj[]; //array for the adjacencies
    private ArrayList<Integer> weight[]; //weight for adjacency ( transportation cost)   
    
    AnalyzeFile(String URL)  {
        try {
            cin = new BufferedReader(new FileReader(URL));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Can't find this file : " + URL );
        }        
    }
    
    public void startAnalyzation() throws IOException{        
        tokenizer = new StringTokenizer(cin.readLine());
        testsNumber = Integer.parseInt(tokenizer.nextToken());  // reading the number of tests
        if(testsNumber >10 || testsNumber<=0){  // Validation for number of tests
            throw new NullPointerException("The number of tests is invalid ! It must be from 1 to 10.");
        }
        parseEachTest();
        
    }
    
    private void parseEachTest() throws IOException{
        for (int test=0; test<testsNumber; test++){
            //analyze of cities amount
            parseCitiesNumber();                                   
             //analyze for each city
            parseEachCity(test);
            
            int pathes = parseEachPath(test);          
            
            for ( int i = 0; i < pathes; i++ ){
                tokenizer = new StringTokenizer(cin.readLine());
                String source = tokenizer.nextToken(); //reading the source city
                String destination = tokenizer.nextToken(); //reading the destination city
                if(source.equals(destination) || invalidDestinationSource(source) || invalidDestinationSource(destination)){  
                    // Validation for the path 
                    throw new NullPointerException("Test:"+(test+1)+"  path: "+(i+1)+" The path is invalid! The source and destination can not be equal. They must be in the cities list." );
                }   
                int startIndex = 0;
                int destinationIndex = 0;        
                for ( int iter = 0; iter < names.length; iter++ ){   // transformation names to indexes 
                    if (names[iter].equals(source)){
                       startIndex = iter;
                    }
                    if (names[iter].equals(destination)){
                       destinationIndex = iter;
                    }
                }
                createDijkstraData(startIndex, destinationIndex);              
            }            
            cin.readLine();
            System.out.println();
        }
        cin.close();
    }
    private void parseCitiesNumber() throws IOException{
        tokenizer = new StringTokenizer(cin.readLine());
        cities = Integer.parseInt(tokenizer.nextToken());  // reading the number of cities
        if(cities >10000 || cities<=0){  // Validation for number of cities
            throw new NullPointerException("The number of cities is invalid ! It must be from 1 to 10000.");
        } 
        weight = new ArrayList[cities];
        adj = new ArrayList[cities];
        names = new String [cities] ;        
    }
    private void parseEachCity(int test) throws IOException{
        //read information for each city
        for (int city = 0; city < cities; ++city) {

            String cityName = cin.readLine(); 
            names[city] = cityName; // put the current name to array
            validateCityName(cityName, city, test);
            
            int ways = parseNeighbourNumber(city, test);
            
            adj[city] = new ArrayList<>(); 
            weight[city] = new ArrayList<>(); 

            for(int j = 0; j < ways; j++){   
               parseNeighbour(city, test);
            }
        }
    }
    private int parseEachPath(int test) throws IOException{
        tokenizer = new StringTokenizer(cin.readLine());
        int r = Integer.parseInt(tokenizer.nextToken()); //reading the number of path
        if( r > 100 || r <= 0){  // Validation for the number of paths
            throw new NullPointerException("Test:"+(test+1)+" "+"The number of paths is invalid ! It must be from 1 to 100." );
        }
        return r;            
    }   
    private void validateCityName(String cityName, int city, int test){
        if(cityName.equals("") || cityName.length()>10 || namescan(cityName) || invalidCreation( city)) {  // Validation for city name 
                throw new NullPointerException("Test:"+(test+1)+" city:"+(city+1)
                    +" The city name is invalid.The name should not be repeated in the list."
                    + " The name of a city is a String containing characters a,...,z and is at most 10 characters long !");
            }
    }
    private boolean namescan (String name){     
        // validation of city name for each symbol
      
        char[] array =  name.toCharArray();
        for (int i = 0; i < array.length; i++){
            char symbol = array[i];
            if ( Character.isLetter(symbol) ){   }else{  return true; }
        }        
        return false;        
    }
    
    private boolean invalidDestinationSource(String city){
        // validation : list of cities must contain the source and destiation
        for (String name : names) {
            if (name.equals(city)) {
                return false;
            }
        }   
        return true;    
    }
    
    public boolean invalidCreation(int city){ // validation of city name using the array of entered cities
        if (city != 0){
            //The name should not be repeated 
            for( int k = 0; k< city ; k++){            
                if( names[k].equals(city) ){
                    return true;
                }            
            } 
        }
        return false;
    }
    private int parseNeighbourNumber(int city, int test) throws IOException{
        tokenizer = new StringTokenizer(cin.readLine());
        int ways = Integer.parseInt(tokenizer.nextToken()); // reading the number of neighbours
        if(ways >= cities || ways < 0){  // Validation for number of neighbours
            throw new NullPointerException("Test:"+(test+1)+" city:"+(city+1)
                +" "+"The number of neighbours is invalid ! It must be less then number of cities." );
        }
        return ways;
    }
    private void parseNeighbour(int city, int test) throws IOException{
        tokenizer = new StringTokenizer(cin.readLine());
        int v = Integer.parseInt(tokenizer.nextToken()); //reading the index of neighbour
        if(v > cities || v <= 0){  // Validation for index of neighbour
            throw new NullPointerException("Test:"+(test+1)+" city:" + (city+1) + " "
                + "The index of neighbour is invalid !  It must be from 1 to n - the number of cities." );
        }
        int w = Integer.parseInt(tokenizer.nextToken()); // reading the value of cost
        if( w > 200000 || w <= 0){  // Validation for the cost value
            throw new NullPointerException("Test:"+(test+1)+" city:" +( city+1 ) 
                    + " " + "The cost is invalid ! It must be from 1 to 200000." );
        }
        v--;  // modification number to array index
        adj[city].add(v);  
        weight[city].add(w);
    }
    private void createDijkstraData(int startIndex, int destinationIndex){
        Dijkstra dijkstraAlg = new Dijkstra(); 
        // set data for Dijkstra algorithm  
        dijkstraAlg.setAdj(adj);        
        dijkstraAlg.setWeight(weight);
        dijkstraAlg.setStart(startIndex);
        dijkstraAlg.setDestination(destinationIndex);
        dijkstraAlg.dejkstraAlgorithm();
    }
    
}
