
package task2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author S.Gaidai
 */
public class Dijkstra {
    
    boolean[]  used; // array for the viewed cities
    int[] dist;
    int INF = 200000; // the max cost of transportation ( max weight)
   
   
    private ArrayList<Integer> adj[]; 
    private ArrayList<Integer> weight[]; 
    
    
    public void dejkstra(int s, int d) {
        int n = adj.length; // get the number of cities in test
        // initialization for arrays
        used = new boolean[n]; 
        Arrays.fill(used, false);
             
        dist = new int[n];
        Arrays.fill(dist, INF);
        
        dist[s] = 0; //start value of cost for source point
        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;
            //finding of not used point
            for (int i = 0; i < n; ++i) {
                if (used[i]) {
                    continue;
                }
                if (distV < dist[i]) {
                    continue;
                }
                v = i;
                distV = dist[i];
            }
            //processing for all adjacencies of city
            for (int i = 0; i < adj[v].size(); ++i) {
                int u = adj[v].get(i);
                int weightU = weight[v].get(i);
                
                //identification the minimum cost (weight) for point

                if (dist[v] + weightU < dist[u]) {
                    dist[u] = dist[v] + weightU;
                
                }
            }
            //mark the city as viewed point
            used[v] = true;
            
        }
        if (dist[d]>200000){System.out.println(" The minimum cost for this path is too high. MAX value is 200000 "); } // validation for minimum cost
            else{System.out.println(dist[d]);}                   // print minimum cost for path
    }
    
  
    public Dijkstra( ArrayList<Integer>[] adj, ArrayList<Integer>[] weight) {
        
        this.adj = adj;
        this.weight = weight;
    }

    public Dijkstra() {
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

}
