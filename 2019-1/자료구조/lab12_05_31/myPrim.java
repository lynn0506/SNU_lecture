//eunjoo jung 2014 - 19498
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class myPrim {
    static Scanner scanner;
    static int V;
    static HashMap<Integer, HashSet<Pair>> node;

    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        scanner = new Scanner(new File(input));
        int index = 0;
        String[] S = new String[10];

        while (scanner.hasNextLine()) {
            S[index++] = scanner.nextLine();
        }

        V = S[0].length();
        int start = Integer.parseInt(S[V]);

        int[][] matrix = new int[V][V];
        for (int i = 0; i < V; i++) {
            String tmp = S[i];
            for (int j = 0; j < V; j++) {
                matrix[i][j] = Integer.parseInt(tmp.substring(j, j + 1));
            }
        }

        Prim prim = new Prim(matrix, start);
        prim.getTree();

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
            Pair pr = (Pair) o;

            if (weight > pr.weight)
                return 1;
            else
                return -1;

        }
    }

    public static class Prim {
        int size;
        int[] Q = new int[V + 1];
        int[] d = new int[V + 1];
        int[][] inputTree;
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int source;


        public Prim(int[][] matrix, int start) {
            size = V;
            node = new HashMap<>();
            for (int i = 1; i < V + 1; i++)
                Q[i] = 0;

            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (matrix[i][j] != 0) {
                        int x = i + 1;
                        int y = j + 1;
                        if (node.get(x) == null) {
                            node.put(x, new HashSet<>(V, 0.75F));
                            node.get(x).add(new Pair(y, matrix[i][j]));
                        } else {
                            node.get(x).add(new Pair(y, matrix[i][j]));
                        }
                    }
                }
            }

            inputTree = matrix;
            source = start;
            pq.add(new Pair(1, 0));
        }

        public void getTree() {
            Arrays.fill(d, Integer.MAX_VALUE);
            Arrays.fill(Q, 0);
            d[1] = 0;

            int[] t = new int[V + 1];
            Arrays.fill(t, 0);

            while (!pq.isEmpty()) {
                Pair pr = pq.remove();
                Q[pr.vertex] = 1;

                for (Pair p : node.get(pr.vertex)) {
                    if (d[p.vertex] > p.weight) {
                        d[p.vertex] = p.weight;
                        t[p.vertex] = pr.vertex;

                        if (Q[p.vertex] == 0)
                            pq.add(new Pair(p.vertex, d[p.vertex]));
                    }
                }
            }
            LinkedHashMap<Integer, Integer>[] primTree = new LinkedHashMap[V+1];
            Arrays.fill(primTree, new LinkedHashMap<>());

            System.out.println("Edge   weight");
            int i = 1;
            for(int j = 0; j<V+1; j++)
            {
                if(t[j] == 1) {
                    int y = j-1;
                    System.out.println(0 + " - " + y + "   " + d[j]);
                    i = y;
                }
            }
            int s = 1;
            while(s < V-1) {
               for(int j = 1; j < V+1; j++) {
                   if(t[j] == i+1) {
                       int y = j-1;
                       System.out.println(t[j]-1 + " - " + y + "   " + d[j]);
                       i = y;
                   }
               }
               s++;
            }
            s = 0;
            for(int m = 1; m < V+1; m++)
                s += d[m];

            System.out.println("Total weight : " + s);
        }
        public static LinkedHashMap<Integer, Integer> sortByKey(LinkedHashMap<Integer, Integer> map)
        {
            List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
            list.sort(Map.Entry.comparingByKey());

            Map<Integer, Integer> S = new LinkedHashMap<>();
            for(Map.Entry<Integer, Integer> entry : list)
                S.put(entry.getKey(), entry.getValue());

            return (LinkedHashMap<Integer, Integer>)S;
        }

    }}



