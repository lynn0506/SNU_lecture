import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Lab11 {
    public static Set<Integer> BFTraversal(Graph graph, int root) {
        Queue<List<Integer>> queue = new ArrayDeque<>();
        Set<Integer> set = new LinkedHashSet<>();
        set.add(root);
        queue.add(graph.getAdjVertices(root));

        while(!queue.isEmpty()) {
            List<Integer> t = queue.poll();
            for(int i = 0; i<t.size(); i++) {
                if(!set.contains(t.get(i))) {
                    queue.add(graph.getAdjVertices(t.get(i)));
                    set.add(t.get(i));
                }
            }
        }
        return set;
    }

    public static Set<Integer> DFTraversal (Graph graph, int root) {
        Set<Integer> set = new LinkedHashSet<>();
        set = dfs(graph, root, set);
        return set;
    }

    public static Set<Integer> dfs(Graph graph, int v, Set<Integer> map) {
        int n = graph.getAdjVertices(v).size();
        map.add(v);

        for(int i = 0; i<n; i++) {
            if(!map.contains(graph.getAdjVertices(v).get(i))) {
                dfs(graph, graph.getAdjVertices(v).get(i), map);
            }
        }
        return map;
    }

    public static void main(String[] args) throws FileNotFoundException {

        /**
         *  Test 1
         */
        Graph graph = new DGraph();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.printGraph();
        System.out.print("DFS traversal from 5 : ");
        System.out.println(DFTraversal(graph, 5));
        System.out.println();

        graph = new UGraph();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        graph.printGraph();
        System.out.print("BFS traversal from 2 : ");
        System.out.println(BFTraversal(graph, 2));
        System.out.println();

        /**
         *  Test 2
         */

        File file = new File("input.txt");
        Scanner scanner = new Scanner(file);
        graph = new UGraph();
        // Add code to construct the corresponding Graph from input2
        int v = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i<=v; i++) {
            graph.addVertex(i);
        }

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] inputs = line.split("\t");
            int t = Integer.parseInt(inputs[0]);
            for(int i = 1; i<inputs.length; i++)
            {
                graph.addEdge(t, Integer.parseInt(inputs[i]));
            }
        }
        System.out.println("BFS");
        System.out.println(BFTraversal(graph, 1));
        System.out.println();


        file = new File("input2.txt");
        scanner = new Scanner(file);
        graph = new DGraph();
        // Add code to construct the corresponding Graph from input2
        v =  Integer.parseInt(scanner.nextLine());
        for(int i = 0; i<=v; i++)
            graph.addVertex(i);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] inputs = line.split(" ");
            int t = Integer.parseInt(inputs[0]);
            for(int i = 1; i<inputs.length; i++)
                graph.addEdge(t, Integer.parseInt(inputs[i]));
        }

        System.out.println("DFS");
        System.out.println(DFTraversal(graph, 1));
        System.out.println();


    }
}


interface Graph {
    public List<Integer> getAdjVertices(int v);
    public Set<Integer> getVertices();
    public  Map<Integer, List<Integer>> getAdjacencyList();
    public void addVertex(int v);
    public void addEdge(int v1, int v2);
    public void printGraph();

}


class DGraph implements Graph {
    Map<Integer, List<Integer>> adjacencyList;
    int vertex;
    int size;

    public DGraph() {
        vertex = 0;
        size = 0;
        adjacencyList = new LinkedHashMap<>();
    }

    @Override
    public List<Integer> getAdjVertices(int v) {
        return adjacencyList.get(v);
    }

    @Override
    public Set<Integer> getVertices() {
        Set<Integer> set = adjacencyList.keySet();
        System.out.println(set.toString());
        return set;
    }

    @Override
    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public void addVertex(int v) {
        adjacencyList.put(v, new ArrayList<>());
        vertex++;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if(!adjacencyList.get(v1).contains(v2))
            adjacencyList.get(v1).add(v2);
    }

    @Override
    public void printGraph() {
        for(int i = 0; i < vertex; i++) {
            System.out.print("Vertex " + i + " - ");
            System.out.println(adjacencyList.get(i).toString());
        }
    }

    //implement a constructor and methods from Graph interface


}

class UGraph implements Graph   {
    Map<Integer, List<Integer>> adjacencyList;
    int vertex;

    public UGraph() {
        adjacencyList = new LinkedHashMap<>();
        vertex = 0;
    }

    @Override
    public List<Integer> getAdjVertices(int v) {
        return adjacencyList.get(v);
    }

    @Override
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    @Override
    public void addVertex(int v) {
        adjacencyList.put(v, new ArrayList<>());
        vertex++;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if(!adjacencyList.get(v1).contains(v2))
            adjacencyList.get(v1).add(v2);
        if(!adjacencyList.get(v2).contains(v1))
            adjacencyList.get(v2).add(v1);
    }

    @Override
    public void printGraph() {
        for(int i = 0; i<vertex; i++)
        {
            System.out.print("Vertex " + i + " - ");
            System.out.println(adjacencyList.get(i).toString());
        }

    }

    //implement a constructor and methods from Graph interface

}

