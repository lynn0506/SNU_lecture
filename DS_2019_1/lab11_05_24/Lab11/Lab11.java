//2014-19498 jung eunjoo

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@SuppressWarnings("Duplicates")
public class Lab11 {

    public static Set<Integer> BFTraversal(Graph graph, int root) {
        Set<Integer> bfs = new LinkedHashSet<>();
        LinkedHashSet<Integer> visit = new LinkedHashSet<>();
        Queue<List<Integer>> queue = new LinkedList<>();

        queue.add(graph.getAdjVertices(root));
        visit.add(root);
        bfs.add(root);

        while(!queue.isEmpty()){
            List<Integer> n = queue.poll();

            if(n != null) {
                for (int i = 0; i < n.size(); i++) {
                    int m = n.get(i);
                    if (!visit.contains(m)) {
                        visit.add(m);
                        queue.add(graph.getAdjVertices(m));
                    }
                }
            }

        }
        bfs = visit;
        return bfs;
    }

    public static Set<Integer> DFTraversal (Graph graph, int root) {

        Set<Integer> dfs;
        LinkedHashSet<Integer> visit = new LinkedHashSet<>();
        Stack<List<Integer>> stack = new Stack<>();
        DFS(graph, root, visit, stack);
        dfs = visit;
        return dfs;
    }

    public static void DFS(Graph graph, int v, LinkedHashSet<Integer> visit, Stack<List<Integer>> stack)
    {
        visit.add(v);
        stack.add(graph.getAdjVertices(v));

        while(!stack.isEmpty()) {
            List<Integer> n = stack.pop();

            if(n != null) {
                for (int i = 0; i < n.size(); i++) {
                    int m = n.get(i);
                    if (!visit.contains(m)) {
                        DFS(graph, i, visit, stack);
                        visit.add(m);
                        stack.add(graph.getAdjVertices(m));
                    }
                }
            }
        }

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

        int N = Integer.parseInt(scanner.nextLine());

        for (int j = 0; j < N; j++) {
            String s = scanner.nextLine();
            String[] lines = s.split("\t");
            int v = Integer.parseInt(lines[0]);
            graph.addVertex(v);

            for (int k = 1; k < lines.length; k++)
                graph.addEdge(v, Integer.parseInt(lines[k]));
        }

        // Add code to construct the corresponding Graph from input2
        System.out.println("BFS");
        System.out.println(BFTraversal(graph, 1));
        System.out.println();


        file = new File("input2.txt");
        scanner = new Scanner(file);
        graph = new DGraph();
        N = Integer.parseInt(scanner.nextLine());

        for (int j = 0; j < N; j++) {
            String s = scanner.nextLine();
            String[] lines = s.split(" ");
            int v = Integer.parseInt(lines[0]);
            graph.addVertex(v);

            for (int k = 1; k < lines.length; k++)
                graph.addEdge(v, Integer.parseInt(lines[k]));
        }

        // Add code to construct the corresponding Graph from input2
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


class UGraph implements Graph {

    Map<Integer, List<Integer>> adjacencyList;

    public UGraph() {
        this.adjacencyList = new HashMap<>();
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
        if(!adjacencyList.containsKey(v)) {
            List<Integer> list = new ArrayList<>();
            this.adjacencyList.put(v, list);
        }
    }

    @Override
    public void addEdge(int v1, int v2) {
        List<Integer> list = getAdjVertices(v1);
        list.add(list.size(), v2);
        adjacencyList.replace(v1, list);

        addVertex(v2);

        List<Integer> list2 = getAdjVertices(v2);
        list2.add(list2.size(), v1);
        adjacencyList.replace(v2, list2);

    }


    @Override
    public void printGraph() {
        Set<Integer> keyset = getVertices();
        Object[] arr = keyset.toArray();
        for(int i = 0; i<arr.length; i++) {
            List<Integer> edges = getAdjVertices((int)(arr[i]));
            Object[] arr2 = edges.toArray();
            String line = "";
            line += "Vertex " + arr[i] + " - [";

            for(int j = 0; j<arr2.length; j++)
                line += arr2[j] + ", ";

            if(arr2.length == 0)
                System.out.println(line + "]");
            else {
                line = line.substring(0, line.length() - 2) + "]";
                System.out.println(line);
            }
        }
    }

    //implement a constructor and methods from Graph interface


}

class DGraph implements Graph   {

    Map<Integer, List<Integer>> adjacencyList;

    public DGraph() {
        this.adjacencyList = new HashMap<>();
    }
    @Override
    public List<Integer> getAdjVertices(int v) {
        return this.adjacencyList.get(v);
    }

    @Override
    public Set<Integer> getVertices() {
        return this.adjacencyList.keySet();
    }

    @Override
    public Map<Integer, List<Integer>> getAdjacencyList() {
        return this.adjacencyList;
    }

    @Override
    public void addVertex(int v) {
        List<Integer> list = new ArrayList<>();
        this.adjacencyList.put(v, list);
    }

    @Override
    public void addEdge(int v1, int v2) {
        List<Integer> list = getAdjVertices(v1);
        list.add(v2);
        this.adjacencyList.put(v1, list);
    }

    @Override
    public void printGraph() {
        Set<Integer> keyset = getVertices();
        Object[] arr = keyset.toArray();

        for(int i = 0; i<arr.length; i++) {
            List<Integer> edges = getAdjVertices((int)(arr[i]));
            Object[] arr2 = edges.toArray();
            String line = "";

            line += "Vertex " + arr[i] + " - [";

            for(int j = 0; j<arr2.length; j++)
                line += arr2[j] + ", ";

            if(arr2.length == 0)
                System.out.println(line + "]");
            else {
                line = line.substring(0, line.length() - 2) + "]";
                System.out.println(line);
            }
        }
    }

    //implement a constructor and methods from Graph interface

}

