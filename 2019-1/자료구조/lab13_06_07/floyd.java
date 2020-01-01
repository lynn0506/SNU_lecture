//2014-19498 jung eunjoo
import java.util.Scanner;

public class floyd {
    static Scanner inputscan;
    static int V;
    static final int INF = 999;
    static int[][] graph;
    public static void main(String[] args)
    {
        System.out.println("Enter the number of vertices");
        inputscan = new Scanner(System.in);
        V = Integer.parseInt(inputscan.nextLine());
        System.out.println("Enter the Wighted Matrix for the graph");

        int[] input = new int[100];
        int index = 0;
        while(inputscan.hasNextInt()) {
            input[index++] = inputscan.nextInt();
        }
        graph = new int[V][V];
        index = 0;
        for(int i = 0; i<V; i++) {
            for(int j = 0; j<V; j++){
                graph[i][j] = input[index++];
            }
        }

        floyd a = new floyd();
        a.floydWarshall();
    }

    void floydWarshall ()
    {
        int d[][] = new int[V][V];
        int i, j, k;

        for(i = 0; i<V; i++) {
            for (j = 0; j < V; j++) {
                if(i != j && graph[i][j] == 0)
                    d[i][j] = INF;
                else
                    d[i][j] = graph[i][j];
            }
        }

        for(k = 0; k<V; k++)
        {
            for(i = 0; i <V; i++)
            {

                for(j = 0; j<V; j++)
                {
                    if(d[i][k] + d[k][j] < d[i][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }
        printSolution(d);
    }

    void printSolution(int dist[][])
    {
        System.out.print("\t");
        for(int i = 0; i<V; i++)
            System.out.print(i+1 + "\t");

        System.out.println();
        for(int i = 0; i<V; i++)
        {
            System.out.print(i+1 + "\t");
            for(int j = 0; j<V; j++)  {
                    System.out.print(dist[i][j] + "\t");
                }
            System.out.println();
        }

    }
}
