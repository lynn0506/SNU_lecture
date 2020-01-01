//2014-19498 jung, eunjoo
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class ds5_step2 {
    static List<LinkedHashMap<Integer, Integer>> list;
    static PrintStream writer;
    static Scanner inputScan;
    static int VERTEX;
    static int EDGE;
    static final int MAX = 1048578;

    public static void main(String[] args) throws FileNotFoundException {
        inputScan = new Scanner(new File(args[0]));
        writer = new PrintStream(new File(args[1]));
        VERTEX = Integer.parseInt(inputScan.nextLine());
        EDGE = Integer.parseInt(inputScan.nextLine());
        String[] S = new String[MAX]; // array length check
        int i = 0;

        while(inputScan.hasNextLine())
            S[i++] = inputScan.nextLine();

        String[] I = new String[i];
        System.arraycopy(S, 0, I, 0, i);
        makeList(I);

        Kruskal kruskal = new Kruskal();
        kruskal.print();
        Prim prim = new Prim();
        prim.print();

        System.out.println("Input " + args[0] + " successfully read.");
        System.out.println("Trying Kruskal's Algorithm... Done.");
        System.out.println("Total measured time is " + kruskal.Sec + "sec");
        System.out.println("Trying Prim's Algorithm... Done.");
        System.out.println("Total measured time is " + prim.Sec + "sec");
        System.out.println("Output saved as result.txt.");
    }

    public static void makeList(String[] input)
    {
        list = new ArrayList<>(VERTEX+1);
        for(int i = 0; i < VERTEX+1; i++)
            list.add(i, new LinkedHashMap<>());

        for(int i = 0; i < input.length; i++)
        {
            inputScan = new Scanner(input[i]);
            int v = inputScan.nextInt();
            int key = inputScan.nextInt();
            int value = inputScan.nextInt();

            LinkedHashMap<Integer, Integer> tmp = list.get(v);
            tmp.put(key, value);
            list.remove(v);
            list.add(v, tmp);

            LinkedHashMap<Integer, Integer> tmp2 = list.get(key);
            tmp2.put(v, value);
            list.remove(key);
            list.add(key, tmp2);
        }
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

    public static class Prim {
        private float Sec;
        int size;
        int[] Q = new int[VERTEX+1];
        int[] d = new int[VERTEX+1];
        int[] tree = new int[VERTEX+1];
        List<LinkedHashMap<Integer, Integer>> primTree = new ArrayList<>(VERTEX);

        public Prim() {
            size = VERTEX;
            for(int i = 1; i < VERTEX+1; i++)
                Q[i] = i;

            for(int i = 0; i < VERTEX+1; i++)
                primTree.add(i, new LinkedHashMap<>());
        }

        private void getTree() {
            for(int i = 1; i < VERTEX+1; i++)
                d[i] = Integer.MAX_VALUE; // graph'key

            d[1] = 0;
            while(size > 0) {
                int u = deleteMin();
                LinkedHashMap<Integer, Integer> temp = sortByKey(list.get(u));
                List<Integer> keyList = new ArrayList<>(temp.keySet());
                List<Integer> valueList = new ArrayList<>(temp.values());

                for (int i = 0; i < list.get(u).size(); i++) {
                    if (Q[keyList.get(i)] != 0 && valueList.get(i) < d[keyList.get(i)]) {
                        d[keyList.get(i)] = valueList.get(i);
                        tree[keyList.get(i)] = u;
                    }
                }
            }
            for(int i = 2; i< VERTEX+1; i++) {
                LinkedHashMap<Integer, Integer> m = primTree.get(i);
                m.put(tree[i], d[i]);
                primTree.remove(i);
                primTree.add(i, m);

                LinkedHashMap<Integer, Integer> n = primTree.get(tree[i]);
                n.put(i, d[i]);
                primTree.remove(tree[i]);
                primTree.add(tree[i], n);
            }
        }
        private int deleteMin() {
            int min = Integer.MAX_VALUE;
            int searchK = -1;

            for(int i = 1; i<VERTEX+1; i++){
                if(Q[i] != 0 && d[i] < min) {
                    min = d[i];
                    searchK = i;
                }
            }
            Q[searchK] = 0;
            size--;
            return searchK;
        }
        private int totalWeight() {
            int s = 0;
            for(int i = 1; i< VERTEX+1; i++) s += d[i];
            return s;
        }
        private int totalEdges() {
            int e = 0;
            for(int i = 1; i<VERTEX+1; i++) e += primTree.get(i).size();
            return e;
        }
        public void print() {
            writer.println("Prim");
            writer.println(getSec());
            writer.println(primTree.size()-1);
            writer.println(totalEdges()/2);
            writer.println(totalWeight());
            for (int i = 1; i < VERTEX + 1; i++)
            {
                LinkedHashMap<Integer, Integer> temp = sortByKey(primTree.get(i));
                for(Map.Entry entry : temp.entrySet()) {
                    if(i < (int)entry.getKey())
                        writer.println(i + " " + entry.getKey() + " " + entry.getValue());
                }
            }
        }
        private float getSec() {
            long startTime = System.currentTimeMillis();
            getTree();
            long endTime = System.currentTimeMillis();
            float sec = (endTime - startTime) / 1000F;
            this.Sec = sec;
            return sec;
        }
    }
    public static class Kruskal {
        private float Sec;
        private int weight;
        private List<LinkedHashMap<Integer, Integer>> Q = new ArrayList<>();
        private PriorityQueue<Edge> pq = new PriorityQueue<>();
        private List<LinkedHashMap<Integer, Integer>> KruskalTree = new ArrayList<>(VERTEX);

        public class Edge implements Comparable {
            Edge next;
            int start;
            int end;
            int weight;

            public Edge(int start, int end, int weight) {
                this.start = start;
                this.end = end;
                this.weight = weight;
                this.next = null;
            }
            @Override
            public int compareTo(Object o) {
                Edge pr = (Edge) o;
                if (weight > pr.weight) return 1;
                else return -1;
            }
        }
        public Kruskal() {
            for(int i = 0; i< VERTEX+1; i++) {
                KruskalTree.add(i, new LinkedHashMap<>());
                Q.add(i, new LinkedHashMap<>());
            }
        }
        private int find(int parent[], int i)
        {
            if(parent[i] == -1) return i;

            return find(parent, parent[i]);
        }

        private void getTree() {
            int[] parent = new int[VERTEX +1];
            Arrays.fill(parent, -1);

            int vertex = 1;
            for(int i = 1; i < VERTEX+1; i++) {
                LinkedHashMap<Integer, Integer> temp = list.get(i);
                for(Map.Entry entry : temp.entrySet()) {
                    if ((int) entry.getKey() > i) {
                        pq.add(new Edge(i, (int) entry.getKey(), (int)entry.getValue()));
                    }
                }
            }
            while(vertex < VERTEX && !pq.isEmpty()) {
                Edge curr = pq.poll();
                if(find(parent, curr.start) != find(parent, curr.end))
                {
                    union(curr, parent);
                    vertex++;
                }
            }
        }

        private void union(Edge curr, int[] parent) {
            int x = find(parent, curr.start);
            int y = find(parent, curr.end);
            parent[x] = y;

            for (Map.Entry entry : Q.get(curr.start).entrySet())
                Q.get(curr.end).put((int) entry.getKey(), (int) entry.getValue());

            Q.remove(curr.start);
            Q.add(curr.start, new LinkedHashMap<>());

            LinkedHashMap<Integer, Integer> q = KruskalTree.get(curr.start);
            q.put(curr.end, curr.weight);
            KruskalTree.remove(curr.start);
            KruskalTree.add(curr.start, q);

            q = KruskalTree.get(curr.end);
            q.put(curr.start, curr.weight);
            KruskalTree.remove(curr.end);
            KruskalTree.add(curr.end, q);
            weight += curr.weight;
        }

        private int totalEdge() {
            int e = 0;
            for(int i = 0; i< VERTEX+1; i++)
                e += KruskalTree.get(i).size();
            return e;
        }
        public void print() {
            writer.println("Kruskal");
            writer.println(getSec());
            writer.println(KruskalTree.size()-1);
            writer.println(totalEdge()/2);
            writer.println(weight);
            for (int i = 1; i < VERTEX + 1; i++)
            {
                LinkedHashMap<Integer, Integer> temp = sortByKey(KruskalTree.get(i));
                for(Map.Entry entry : temp.entrySet())
                {
                    if(i < (int)entry.getKey())
                    writer.println(i + " " + entry.getKey() + " " + entry.getValue());
                }
            }
        }
        private float getSec()
        {
            long startTime = System.currentTimeMillis();
            getTree();
            long endTime = System.currentTimeMillis();
            float sec = (endTime - startTime)/1000F;
            this.Sec = sec;
            return sec;
        }
    }
}
