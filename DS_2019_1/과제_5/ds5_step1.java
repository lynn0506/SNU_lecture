//2014-19498 Jung, eunjoo
import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class ds5_step1 {
    static PrintStream writer;
    static Random rand = new Random();
    static int Vertice;

    public static void main(String[] args) throws Exception {
        Vertice = Integer.parseInt(args[0]);
        UGraph graph = new UGraph();
        String Output = args[1];

        for(int i = 1; i< Vertice+1; i++)
            graph.addVertex(i);

        while(BFTraversal(graph, 1).size() < Vertice) {
            int V = rand.nextInt(Vertice) +1;
            int D = V;
            rand = new Random();
            int w = rand.nextInt(Vertice) +1;
            rand = new Random();
            while(D == V || graph.getAdjVertices(V).containsKey(D))
                D = rand.nextInt(Vertice) +1;

            graph.addEdge(V, D, w);
        }
        System.out.println("A random graph having " + Vertice + " vertices is generated.");
        System.out.println("Number of edge is " + graph.edgeCount());
        System.out.println("Output saved as graph.txt.");
        writer = new PrintStream(new File(Output));
        graph.printGraph();
    }

    public static Set<Integer> BFTraversal(UGraph graph, int root) {
        Queue<LinkedHashMap<Integer, Integer>> queue = new ArrayDeque<>();
        Set<Integer> set = new LinkedHashSet<>();
        set.add(root);
        queue.add(graph.getAdjVertices(root));

        while(!queue.isEmpty()) {
            LinkedHashMap<Integer, Integer> t = queue.poll();
            List<Integer> keyset = new ArrayList<>(t.keySet());

            for (int i = 0; i < t.size(); i++) {
                if (!set.contains(keyset.get(i))) {
                    queue.add(graph.getAdjVertices(keyset.get(i)));
                    set.add(keyset.get(i));
                }
            }
        }
        return set;
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

    interface Graph {
        LinkedHashMap<Integer, Integer> getAdjVertices(int v);
        void addVertex(int v);
        void addEdge(int v1, int v2, int w);
        void printGraph();
        int edgeCount();
    }

    static class UGraph implements Graph {
        Map<Integer, LinkedHashMap<Integer, Integer>> adjacencyList;
        int vertex;

        public UGraph() {
            adjacencyList = new LinkedHashMap<>();
            vertex = 0;
        }

        public int edgeCount() {
            int s = 0;
            for(int i = 1; i < Vertice+1; i++)
                s += getAdjVertices(i).size();
            return s;
        }

        public LinkedHashMap<Integer, Integer> getAdjVertices(int v) {
            return adjacencyList.get(v);
        }

        public void addVertex(int v) {
            if(!adjacencyList.containsKey(v)) {
                adjacencyList.put(v, new LinkedHashMap<>());
                vertex++;
            }
        }

        public void addEdge(int v1, int v2, int w) {
            LinkedHashMap<Integer, Integer> tmp = adjacencyList.get(v1);
            Set<Integer> keyset = tmp.keySet();
            if(!keyset.contains(v2))
                adjacencyList.get(v1).put(v2, w);

            tmp = adjacencyList.get(v2);
            keyset = tmp.keySet();
            if(!keyset.contains(v1))
                adjacencyList.get(v2).put(v1, w);
        }

        public void printGraph() {
            writer.println(Vertice);
            writer.println(edgeCount()/2);
            for(int i = 1; i<vertex+1; i++)
            {
                LinkedHashMap<Integer, Integer> tmp = sortByKey(adjacencyList.get(i));
                List<Integer> keyset = new ArrayList<>(tmp.keySet());
                List<Integer> valset = new ArrayList<>(tmp.values());
                for(int j = 0; j < tmp.size(); j++) {
                    if(keyset.get(j) > i)
                        writer.println(i + " " + keyset.get(j) + " " + valset.get(j));
                }
            }
        }
    }

    public void remove(int val)
    {
        Node x = find(val);

    }


    public Node find(int val)
    {
        splay(find(root, val));
        return root;
    }

    public Node find(Node root, int val)
    {
        if(root == null)
            return null;

        else if(root.val == val)
            return root;
        else if(root.val > val) {
            if(root.left) == null
                splay(root);
            return find(root.left, val);
        }
    }












}