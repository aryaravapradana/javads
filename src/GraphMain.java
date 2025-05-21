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

        Graph<MyVertex> WG = new Graph<MyVertex>(true); //directed

        WG.addEdge(v1, v2, 4);
        WG.addEdge(v1, v3, 6);
        WG.addEdge(v2, v3, 1);
        WG.addEdge(v2, v4, 2);
        WG.addEdge(v4, v5, 2);
        WG.addEdge(v5, v3, 1);

        System.out.println("\nDirected graph:");
        WG.printGraph();

        // Shortest path 
        // Mengambil contoh A atau v1
        System.out.println("\njalan dari A:");
        System.out.println();   
        Map<MyVertex, MyVertex> prev = new HashMap<>();
        Map<MyVertex, Integer> distances = WG.ShortestPath(v1, prev); // (Changeable according to what user want)

        for (MyVertex vertex : distances.keySet()) {
            int dist = distances.get(vertex);
            if (dist == Integer.MAX_VALUE) {
                System.out.println("jarak dari A ke " + vertex + " = null (tidak ada path yang dapat digunakan)"); // (Changeable according to what user want)
            } else {
                System.out.println("jarak dari A ke " + vertex + " = " + dist); // (Changeable according to what user want)
                WG.UsedPathing(vertex, prev);
                System.out.println();
            }
        }

}
}
