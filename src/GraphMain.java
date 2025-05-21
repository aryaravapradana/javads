import java.util.HashMap;
import java.util.Map;

import com.datastruct.*;

class MyVertex {
    String nodeName;
    MyVertex(String name) {
        this.nodeName = name;
    }

    @Override
    public String toString() {
        return nodeName;
    }
}

public class GraphMain {
    public static void main(String[] args) {
        MyVertex v1 = new MyVertex("A");
        MyVertex v2 = new MyVertex("B");
        MyVertex v3 = new MyVertex("C");
        MyVertex v4 = new MyVertex("D");
        MyVertex v5 = new MyVertex("E");

        Graph<MyVertex> WG = new Graph<MyVertex>(true); // directed

        WG.addEdge(v1, v2, 4);
        WG.addEdge(v1, v3, 6);
        WG.addEdge(v2, v3, 1);
        WG.addEdge(v2, v4, 2);
        WG.addEdge(v4, v5, 2);
        WG.addEdge(v5, v3, 1);

        System.out.println("\nDirected graph:");
        WG.printGraph();

        // Shortest path dari A
        System.out.println("\nJalan dari A:");
        Map<MyVertex, MyVertex> prev = new HashMap<>();
        Map<MyVertex, Integer> distances = WG.ShortestPath(v1, prev);

        for (MyVertex vertex : distances.keySet()) {
            int dist = distances.get(vertex);
            if (dist == Integer.MAX_VALUE) {
                System.out.println("Jarak dari A ke " + vertex + " = null (tidak ada path)");
            } else {
                System.out.println("Jarak dari A ke " + vertex + " = " + dist);
                WG.UsedPathing(vertex, prev);
                System.out.println();
            }
        }

        // Test Algoritma Prim (ubah directed = false)
        System.out.println("\nGraph untuk MST (undirected):");
        Graph<MyVertex> MSTGraph = new Graph<MyVertex>(false);

        MSTGraph.addEdge(v1, v2, 4);
        MSTGraph.addEdge(v1, v3, 6);
        MSTGraph.addEdge(v2, v3, 1);
        MSTGraph.addEdge(v2, v4, 2);
        MSTGraph.addEdge(v4, v5, 2);
        MSTGraph.addEdge(v5, v3, 1);

        System.out.println("\nPrim's MST (mulai dari A):");
        MSTGraph.primMST(v1);

        System.out.println("\nKruskal's MST:");
        MSTGraph.kruskalMST();
    }
}
