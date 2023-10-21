import java.util.Scanner;
import java.util.ArrayList;
class SimulatorOne{
   Graph g = new Graph();
   String shortestTaxi = "";
   int nodes = 0;
   int shops = 0;
   int numClients = 0;
   String[] noHelp; 
   //facilitates and manages input accordingly.
   public void getInput(){
      Scanner input = new Scanner(System.in);
      nodes = Integer.parseInt(input.nextLine());
      noHelp = new String[nodes];
      for(int i = 0; i < nodes; i++){
         String[] line = input.nextLine().split(" ");
         String source = line[0];
         if(line.length == 1){
            noHelp[i] = source;
         }
         else{
            for(int j = 2; j < line.length; j += 2){
               g.addEdge(source, line[j-1], Double.parseDouble(line[j]));
            }
         }
      }
      shops = Integer.parseInt(input.nextLine());
      String[] line = input.nextLine().split(" ");
      for(int i = 0; i < line.length; i++){
         g.getVertex(line[i]).isShop = true;
      }
      numClients  = Integer.parseInt(input.nextLine());
      String[] clients = input.nextLine().split(" ");
      taxiClient(clients);
      }
      
       void taxiClient(String[] client){
         boolean helped = false;
         String output = "";
         double[] costsA = new double[100000];
         double[] costsB = new double[100000];
         
         client: for(int i = 0; i < client.length; i++){
            double minA = 1000000;
            double minB = 1000000;
            System.out.println("client " + client[i]);
            if(unhelped(client[i])){
               System.out.println("cannot be helped");
               continue;
            }
            for(int j = 0; j < nodes; j++){
             Vertex temp  = g.getVertex(String.valueOf(j));
               if(temp.isShop){
                  g.dijkstra(temp.name);
                  double cost = g.getVertex(client[i]).dist;
                  if(cost == Graph.INFINITY)
                     continue;
                  else{
                     helped = true;
                     costsA[j] = cost;
                     if(cost < minA)
                        minA = cost;
                  }
               }
            }
            if(helped){
               for(int k = 0; k < nodes; k++){
                  Vertex temp  = g.getVertex(String.valueOf(k));
                  if(temp.isShop){
                     if(costsA[k] == minA){
                          System.out.println("taxi " + k);
                          double multiple = g.dijkstra(temp.name);
                          if(multiple == minA)
                              System.out.println("multiple solutions cost " + (int)minA);
                          else
                              g.printPath(client[i]);
                          /*Vertex p = g.getVertex(client[i]);
                          output +=  p.name;
                          while(p.prev != null){
                             output = p.prev.name + " " + output;
                             p = p.prev;
                          }
                          output += "\n";*/
                          
                     }
                   }
                }
                // find nearest shop/multiple shop paths here.
                g.dijkstra(client[i]);
                boolean hasPath = false;
                for(int m = 0; m < nodes; m++){
                   Vertex v  = g.getVertex(String.valueOf(m));
                   if(v.isShop){
                     double sCost = v.dist;
                     if(sCost == Graph.INFINITY){
                        continue;
                     }
                     else{
                        hasPath = true;
                        costsB[m] = sCost;
                        if(sCost < minB)
                           minB = sCost;
                        
                     }
                   }
                }
                if(hasPath){
                  for(int n = 0; n < nodes; n++){
                     Vertex temp  = g.getVertex(String.valueOf(n));
                     if(temp.isShop){
                        if(costsB[n] == minB){
                             double multiple = g.dijkstra(client[i]);
                             System.out.println("shop " + n);
                             if(multiple == minB)
                                 System.out.println("multiple solutions cost " + (int)minB);
                             else
                                 g.printPath(temp.name);
                             /*Vertex p = temp;
                             output +=  temp.name;
                             while(p.prev != null){
                                output = p.prev.name + " " + output;
                                p = p.prev;
                             }
                             output += "\n";*/
                             
                        }
                   }
                  }
                }
             }
             else{
               System.out.println("cannot be helped");
             }
             
            
         }
      } 
      
      public boolean unhelped(String client){
         for(int i = 0; i < noHelp.length; i++){
            if(noHelp[i] == null)
               continue;
            else if(noHelp[i].equals(client))
               return true;
         }
         return false;
      }
      
      
   
   
   public static void main(String[] args){
      SimulatorOne sO = new SimulatorOne();
      sO.getInput();
   }

}