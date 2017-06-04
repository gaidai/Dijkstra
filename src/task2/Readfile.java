
package task2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author S.Gaidai
 */
public class Readfile {
    
    private BufferedReader cin;
    private PrintWriter cout;
    private StringTokenizer tokenizer;
    
    private static int INF = 200000; // the max cost
    private int m; // the number of adjacencies for city
    private int n; // total number of cities in test
   
    private String [] name; // array for the city names
    
    private ArrayList<Integer> adj[]; //array for the adjacencies
    private ArrayList<Integer> weight[]; //weight for adjacency ( transportation cost
   
    
   
    
    
    
    public void input(String URL) throws IOException{
        
        cin = new BufferedReader(new FileReader(URL));
        tokenizer = new StringTokenizer(cin.readLine());
        int s = Integer.parseInt(tokenizer.nextToken());  // reading the number of cities
        if(s >10 || s<=0){  // Validation for number of tests
            throw new NullPointerException("The number of tests is invalid ! It must be from 1 to 10.");
        }
        for (int test=0; test<s; test++){
            tokenizer = new StringTokenizer(cin.readLine());
            n = Integer.parseInt(tokenizer.nextToken());  // reading the number of cities
            if(n >10000 || n<=0){  // Validation for number of cities
                throw new NullPointerException("The number of cities is invalid ! It must be from 1 to 10000.");
            }
            Dijkstra di = new Dijkstra(); // creating a new object for Dijkstra algorithm
            weight = new ArrayList[n];
            adj = new ArrayList[n];
            name = new String [n] ;

            //read information for each city
            for (int i = 0; i < n; ++i) {

                String city = cin.readLine(); // reading city name 
                if(city.equals("") || city.length()>10 || namescan(city) || invalidcreation(city, name,i)) {  // Validation for city name 
                    throw new NullPointerException("Test:"+(test+1)+" city:"+(i+1)+" The city name is invalid.The name should not be repeated in the list. The name of a city is a String containing characters a,...,z and is at most 10 characters long !");
                }
                name[i] = city; // put the current name to array
                tokenizer = new StringTokenizer(cin.readLine());
                int ways = Integer.parseInt(tokenizer.nextToken()); // reading the number of neighbours
                if(ways>=n || ways<0){  // Validation for number of neighbours
                    throw new NullPointerException("Test:"+(test+1)+" city:"+(i+1)+" "+"The number of neighbours is invalid ! It must be less then number of cities." );
                }
                adj[i] = new ArrayList<Integer>(); // initialization for adjacency list in the array
                weight[i] = new ArrayList<Integer>(); // initialization for weight list in the array

                for(int j = 0; j<ways; j++){    

                    tokenizer = new StringTokenizer(cin.readLine());
                    int v = Integer.parseInt(tokenizer.nextToken()); //reading the index of neighbour
                    if(v>n || v<=0){  // Validation for index of neighbour
                        throw new NullPointerException("Test:"+(test+1)+" city:"+(i+1)+" "+"The index of neighbour is invalid !  It must be from 1 to n - the number of cities." );
                    }
                    int w = Integer.parseInt(tokenizer.nextToken()); // reading the value of cost
                    if(w>200000 || w<=0){  // Validation for the cost value
                        throw new NullPointerException("Test:"+(test+1)+" city:"+(i+1)+" "+"The cost is invalid ! It must be from 1 to 200000." );
                    }
                    v--;  // modification number to array index
                    adj[i].add(v);  
                    weight[i].add(w);
                }
            }

            tokenizer = new StringTokenizer(cin.readLine());
            int r = Integer.parseInt(tokenizer.nextToken()); //reading the number of path
            if(r>100 || r<=0){  // Validation for the number of paths
                    throw new NullPointerException("Test:"+(test+1)+" "+"The number of paths is invalid ! It must be from 1 to 100." );
                }
            
            di.setAdj(adj);        
            di.setWeight(weight);
            for ( int i = 0; i<r; i++ ){
                tokenizer = new StringTokenizer(cin.readLine());
                String source = tokenizer.nextToken(); //reading the source city
                String destination = tokenizer.nextToken(); //reading the destination city
                if(source.equals(destination) || invaliddestsource(source,name ) ||  invaliddestsource(destination,name )){  // Validation for the path 
                    throw new NullPointerException("Test:"+(test+1)+"  path: "+(i+1)+" The path is invalid! The source and destination can not be equal. They must be in the cities list." );
                }   
                

                int sindex = 0;
                int dindex = 0;        
                for ( int iter = 0; iter<name.length; iter++ ){   // transformation names to indexes 
                    if (name[iter].equals(source)){
                       sindex = iter;
                    }
                    if (name[iter].equals(destination)){
                       dindex = iter;
                    }

                }
                di.dejkstra(sindex, dindex);   // start Dijkstra algorithm for the path
            }
            
        cin.readLine();
        System.out.println();
        }
        cin.close();
    }
    
    boolean namescan (String name){               // validation of city name for each symbol
      
        char[] array =  name.toCharArray();
        for (int i = 0; i< array.length; i++){
            char symbol = array[i];
            if ( Character.isLetter(symbol) ){   }else{  return true; }
        }
        
        return false;
        
    }
    boolean invaliddestsource(String city, String[] name){ // validation : list of cities must contain the source and destiation
        for( int i = 0; i<name.length ; i++){
           
            if( name[i].equals(city)){

                return false;
            }
        }
   
        return true;
    
    }
    
    public boolean invalidcreation(String city, String[] name , int i){ // validation of city name using the array of entered cities
        if (i == 0){return false;}                                      //The name should not be repeated 
        for( int k = 0; k< i ; k++){
            
            if( name[k].equals(city) ){   return true; }
            
        } 
        return false;
    }
    
}
