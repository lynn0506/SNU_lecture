import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class myDij {
    static Scanner scanner;
    static int V;
    static HashMap<Integer, HashSet<Pair>> node;


    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        scanner = new Scanner(new File(input));
        int index = 0;
        String[] S = new String[10];

        while(scanner.hasNextLine()){
            S[index++] = scanner.nextLine();
        }

        V = S[0].length();
        int start = Integer.parseInt(S[V]);

        int[][] matrix = new int[V][V];
        for(int i = 0; i<V; i++) {
            String tmp = S[i];
            for(int j = 0; j<V; j++) {
                matrix[i][j] = Integer.parseInt(tmp.substring(j, j+1));
            }
        }

        System.out.println("The shorted Path to all nodes are");
        Dijkstra mydijk = new Dijkstra(matrix, start);
        mydijk.getTree();
    }

    public static class Pair implements Comparable {
        int vertex;
        int weight;

        public Pair(int v, int weight) {
            this.vertex = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Object o) {
            Pair pr = (Pair)o;

            if(weight > pr.weight)
                return 1;
            else
                return -1;

        }
    }

    public static class Dijkstra {
        int size;
        int[] Q = new int[V+1];
        int[] d = new int[V+1];
        int[][] inputTree;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int source;


        public Dijkstra(int[][] matrix, int start) {
            size = V;
            node = new HashMap<>();
            for(int i = 1; i < V+1; i++)
                Q[i] = 0;

            for(int i = 0; i<V; i++) {
                for(int j = 0; j<V; j++) {
                    if(matrix[i][j] != 0) {
                        int x = i+1;
                        int y = j+1;
                        if (node.get(x) == null) {
                            node.put(x, new HashSet<Pair>(V, 0.75F));
                            node.get(x).add(new Pair(y, matrix[i][j]));
                        } else {
                            node.get(x).add(new Pair(y, matrix[i][j]));
                            }
                        }
                    }
                }

            inputTree = matrix;
            source = start;
            pq.add(new Pair(source, 0));
        }

        public void getTree() {
            Arrays.fill(d, Integer.MAX_VALUE);
            Arrays.fill(Q, 0);
            d[source] = 0;

            while(!pq.isEmpty()) {
                Pair pr = pq.remove();
                Q[pr.vertex] = 1;

                for (Pair p : node.get(pr.vertex)) {
                    if (d[p.vertex] > d[pr.vertex] + p.weight) {
                        d[p.vertex] = d[pr.vertex] + p.weight;

                        if (Q[p.vertex] == 0)
                            pq.add(new Pair(p.vertex, d[p.vertex]));
                    }
                }
            }

            for(int i = 1 ; i<V+1; i++){
                if(Q[i] == 0)
                    d[i] = 0;

                System.out.println(source+ " to " + i + " is " + d[i]);
            }
        }
    }
}
