
package task2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author S.Gaidai
 */
public class Dijkstra {
    
    private boolean[]  used; // array for the viewed cities
    private int[] dist;
    private int INF = 200000; // the max cost of transportation ( max weight)   
    private int cities;
    private ArrayList<Integer> adj[] ,weight[]; 
    private int start , destination;    
    
    public void dejkstraAlgorithm() {
        cities = adj.length; // get the number of cities in test
        // initialization for arrays
        initializeArrays();
        dist[start] = 0; //start value of cost for source point
       
        for (int city = 0; city < cities; ++city) {
            int v = -1;
            int distValue = INF;
            //finding of not used point
            for (int j = 0; j < cities; ++j) {
                if (used[j] || distValue < dist[j]) {
                    continue;
                }
                v = j;
                distValue = dist[j];
            }
            //processing for all adjacencies of city
            identMinimalCost(v);
            //mark the city as viewed point
            used[v] = true;            
        }
        if (dist[destination]>200000){
            System.out.println(" The minimum cost for this path is too high. MAX value is 200000 ");
            // validation for minimum cost
        }else{
            System.out.println(dist[destination]);
            // print the minimal cost for path
        }        
    }   
    private void initializeArrays(){
        used = new boolean[cities]; 
        Arrays.fill(used, false);             
        dist = new int[cities];
        Arrays.fill(dist, INF);   
    }
    
    private void identMinimalCost(int v){
        
        for (int i = 0; i < adj[v].size(); ++i) {
            int u = adj[v].get(i);
            int weightU = weight[v].get(i);

            //identification the minimum cost (weight) for point
            if (dist[v] + weightU < dist[u]) {
                dist[u] = dist[v] + weightU;                
            }
        }
    }
  
    public ArrayList<Integer>[] getAdj() {
        return adj;
    }

    public void setAdj(ArrayList<Integer>[] adj) {
        this.adj = adj;
    }

    public ArrayList<Integer>[] getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<Integer>[] weight) {
        this.weight = weight;
    }
    
    public void setStart(int start) {
        this.start = start;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
