//2014-19498 Jung, eunjoo
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class ds5_step3 {
    static List<LinkedHashMap<Integer, Integer>> list;
    static PrintStream writer;
    static Scanner scanner;
    static int V;
    static int E;
    static final int MAX = 1048578;

    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        String output = args[1];
        writer = new PrintStream(new File(output));
        scanner = new Scanner(new File(input));
        V = Integer.parseInt(scanner.nextLine());
        E = Integer.parseInt(scanner.nextLine());

        String[] s = new String[MAX];// input array len check
        int i = 0;

        while (scanner.hasNextLine())
            s[i++] = scanner.nextLine();

        String[] I = new String[i];
        System.arraycopy(s, 0, I, 0, i);
        makeList(I);
        dijkstra D = new dijkstra();
        D.mstDijkstra();

        System.out.println("Input graphn.txt successfully read.");
        System.out.println("Performing Dijkstra's algorithm... Done.");
        System.out.println("Output saved as dijkstra.txt.");

    }

    public static void makeList(String[] input) {
        list = new ArrayList<>();

        for(int i = 0; i<V+1; i++)
            list.add(new LinkedHashMap<>());

        for (int i = 0; i < input.length; i++) {
            scanner = new Scanner(input[i]);
            int v = scanner.nextInt();
            int key = scanner.nextInt();
            int val = scanner.nextInt();

            LinkedHashMap<Integer, Integer> tmp = list.get(v);
            tmp.put(key, val);
            list.remove(v);
            list.add(v, tmp);
        }
    }

    public static LinkedHashMap<Integer, Integer> sortByKey(LinkedHashMap<Integer, Integer> map) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByKey());

        Map<Integer, Integer> S = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list)
            S.put(entry.getKey(), entry.getValue());

        return (LinkedHashMap<Integer, Integer>) S;
    }

    public static class dijkstra {
        int size;
        int[] Q = new int[V + 1];
        int[] d = new int[V + 1];
        int[] tree = new int[V + 1];
        List<LinkedHashMap<Integer, Integer>> dijkstraTree = new ArrayList<>(V);

        public dijkstra() {
            size = V;
            for (int i = 1; i < V + 1; i++)
                Q[i] = i;

            for (int i = 1; i < V + 1; i++)
                tree[i] = 0;

            for (int i = 0; i < V + 1; i++)
                dijkstraTree.add(i, new LinkedHashMap<>());

        }

        public void mstDijkstra() {
            for (int i = 1; i < V + 1; i++)
                d[i] = Integer.MAX_VALUE;

            d[1] = 0;
            writer.println(V);
            while (size > 0) {
                int u = extractMin();

                LinkedHashMap<Integer, Integer> temp = sortByKey(list.get(u));
                List<Integer> keyList = new ArrayList<>(temp.keySet());
                List<Integer> valueList = new ArrayList<>(temp.values());

                for (int i = 0; i < list.get(u).size(); i++) {
                    if (Q[keyList.get(i)] != 0 && d[u] + valueList.get(i) < d[keyList.get(i)]) {
                        d[keyList.get(i)] = d[u] + valueList.get(i);
                        tree[keyList.get(i)] = u;
                    }
                }
                writer.println(u);
                writer.print("d");

                for(int i = 1; i<V+1; i++) 
                {
                    if(d[i] != Integer.MAX_VALUE) writer.print(" " + d[i]);
                    else writer.print(" -");
                } 
                writer.println();
                writer.print("p");

                for(int i = 1; i<V+1; i++) 
                {
                    if(tree[i] != 0) writer.print( " " + tree[i]);
                    else writer.print(" -");
                }
                writer.println();
            }   
            
            for(int i = 2; i< V+1; i++) 
            {
                LinkedHashMap<Integer, Integer> m = dijkstraTree.get(i);
                m.put(tree[i], d[i]);
                dijkstraTree.remove(i);
                dijkstraTree.add(i, m);
            }
        }

        public int extractMin() {
            int min = Integer.MAX_VALUE;
            int searchK = -1;

            for (int i = 1; i < V + 1; i++) {
                if (Q[i] != 0 && d[i] < min) {
                    min = d[i];
                    searchK = i;
                }
            }
            if(searchK != -1) Q[searchK] = 0;
            size--;
            return searchK;
        }
    }
}
