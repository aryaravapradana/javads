// Simpan sebagai GraphTopOrder.java (di folder src utama, package default)
// Jika Graph.java berada di package com.datastruct, tambahkan `import com.datastruct.Graph;`

import com.datastruct.Graph;
import java.util.List;

public class graphtoporder {
    public static void main(String[] args) {
        // Buat graph berarah (DAG)
        Graph<String> g = new Graph<>(true);

        // Tambahkan edge sesuai contoh soal
        g.addEdge("b", "c", 1);
        g.addEdge("b", "d", 2);
        g.addEdge("a", "b", 4);
        g.addEdge("a", "c", 6);
        g.addEdge("d", "e", 2);
        g.addEdge("e", "c", 1);

        // Cetak representasi graph (opsional)
        System.out.println("Directed Graph:");
        g.printGraph();
        System.out.println();

        // Lakukan topological sort
        try {
            List<String> topo = g.topologicalSort();
            System.out.println("Topological Ordering: " + topo);
        } catch (IllegalStateException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
