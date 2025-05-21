package com.datastruct;
/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */
import java.util.*;

class Edge<T> { 
	private T neighbor; //connected vertex
	private int weight; //weight
	
	//Constructor, Time O(1) Space O(1)
	public Edge(T v, int w) {
		this.neighbor = v; 
		this.weight = w;
	}

	public void setNeighbor(T neighbor) {
		this.neighbor = neighbor;
	}
	public T getNeighbor() {
		return neighbor;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}

	//Time O(1) Space O(1)
	@Override
	public String toString() {
		return "(" + neighbor + "," + weight + ")";
	}
}

public class Graph<T> {
	//Map<T, LinkedList<Edge<T>>> adj;
	private Map<T, MyLinearList<Edge<T>>> adj;
	boolean directed;

	//Constructor, Time O(1) Space O(1)
	public Graph (boolean type) {
		adj = new HashMap<>();
		directed = type;
	}

	//Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		adj.putIfAbsent(a, new MyLinearList<>()); //add node
		adj.putIfAbsent(b, new MyLinearList<>()); //add node
		Edge<T> edge1 = new Edge<>(b, w);
		adj.get(a).pushQ(edge1);//add(edge1); //add edge
		if (!directed) { //undirected
			Edge<T> edge2 = new Edge<>(a, w);
			adj.get(b).pushQ(edge2);
		}			
	}
	//Print graph as hashmap, Time O(V+E), Space O(1)
	public void printGraph() {
		for (T key: adj.keySet()) {
			//System.out.println(key.toString() + " : " + adj.get(key).toString());
            System.out.print(key.toString() + " : ");
			MyLinearList<Edge<T>> thelist = adj.get(key);
			Node<Edge<T>> curr = thelist.head;
			while(curr != null) {
				System.out.print(curr.getData());
				curr = curr.getNext();
			}
			System.out.println();
		}
	}

	//DFS
	// pake format yang lumayan mirip sesuai materi https://www.lavivienpost.net/weighted-graph-as-adjacency-list/
	public void DFS(T src) {
		Set<T> visited = new HashSet<>();
		System.out.print("DFS: ");
		dfsHelper(src, visited);
		System.out.println();
	}

	private void dfsHelper(T node, Set<T> visited) {
		if (node == null || visited.contains(node)) 
		return;
		visited.add(node);
		System.out.print(node + " ");
		MyLinearList<Edge<T>> neighbors = adj.get(node);
		if (neighbors == null) 
		return;
		Node<Edge<T>> current = neighbors.head;
		while (current != null) {
			T neighbor = current.getData().getNeighbor();
			if (!visited.contains(neighbor)) {
				dfsHelper(neighbor, visited);
			}
			current = current.getNext();
		}
	}

	//BFS
	// pake format yang lumayan mirip sesuai materi https://www.lavivienpost.net/weighted-graph-as-adjacency-list/
	public void BFS(T src) {
		Set<T> visited = new HashSet<>();
		MyLinearList<T> queue = new MyLinearList<>();

		System.out.print("BFS: ");
		visited.add(src);
		queue.pushQ(src);

		while (!queue.isEmpty()) {
			T currentNode = queue.remove();
			System.out.print(currentNode + " ");

			MyLinearList<Edge<T>> neighbors = adj.get(currentNode);
			if (neighbors == null) continue;

			Node<Edge<T>> current = neighbors.head;
			while (current != null) {
				T neighbor = current.getData().getNeighbor();
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.pushQ(neighbor);
				}
				current = current.getNext();
			}
		}
		System.out.println();
	}
	// deleteEdge (Opsional)
	//Remove edge sesuai dari materi yang diberikan hanya diubah menjadi ke versi linearlist
	private Edge<T> findEdgeByNodes(T a, T b) {
    	if (!adj.containsKey(a) || !adj.containsKey(b)) 
        	return null;
    	MyLinearList<Edge<T>> ne = adj.get(a); 
    	Node<Edge<T>> curr = ne.head;
		while (curr != null) {  
			if (curr.getData().getNeighbor().equals(b)) {  
				return curr.getData();  
			}
			curr = curr.getNext();  
		}
		return null;  
	}

	public boolean removeEdge(T a, T b) {
		if (!adj.containsKey(a) || !adj.containsKey(b)) 
			return false;

		MyLinearList<Edge<T>> listA = adj.get(a); 
		MyLinearList<Edge<T>> listB = adj.get(b); 

		if (listA == null || listB == null) 
			return false;

		Edge<T> edge1 = findEdgeByNodes(a, b);
		if (edge1 == null) 
			return false;
		listA.remove(edge1);
		
		if (!directed) {
			Edge<T> edge2 = findEdgeByNodes(b, a);
			if (edge2 != null) {
				listB.remove(edge2);  
			}
		}

		return true; 
	}
	

    public Map<T, Integer> ShortestPath(T start, Map<T, T> prev) {
        Map<T, Integer> jarak = new HashMap<>(); 		//Jarak
        MyLinearList<T> unvisited = new MyLinearList<>(); 	//Membedakan tujuan/Huruf yang sudah di visit atau tidak

        for (T vertex : adj.keySet()) {
            jarak.put(vertex, Integer.MAX_VALUE); 
            unvisited.pushQ(vertex);
        }
        jarak.put(start, 0);

        while (!unvisited.isEmpty()) {
            T minVertex = null;
            int jarakMin = Integer.MAX_VALUE;
            Node<T> current = unvisited.head;

            while (current != null) {
                T vertex = current.getData();
                int jarakLama = jarak.get(vertex);
                if (jarakLama < jarakMin) {
                    jarakMin = jarakLama;
                    minVertex = vertex;
                }
                current = current.getNext();
            }

            if (minVertex == null) break;
            unvisited.remove(minVertex);

            MyLinearList<Edge<T>> tetangga = adj.get(minVertex);
			// Pencarian tetangga dari node
            if (tetangga != null) { 
                Node<Edge<T>> nodeTetangga = tetangga.head;
                while (nodeTetangga != null) { // loop akan terus berulang ketika node masih memiliki tetangga
                    Edge<T> edge = nodeTetangga.getData();
                    T neighbor = edge.getNeighbor();
                    int jarakBaru = jarak.get(minVertex) + edge.getWeight();
					// jika jarak terbaru lebih kecil dibanding jarak yang lama maka akan di switch
                    if (jarakBaru < jarak.get(neighbor)) {
                        jarak.put(neighbor, jarakBaru);
                        prev.put(neighbor, minVertex);
                    }
                    nodeTetangga = nodeTetangga.getNext(); 
                }
            }
        }

        return jarak;
    }

	// Menampilkan pathing node 
    public void UsedPathing(T end, Map<T, T> prev) {
        List<T> path = new ArrayList<>();
        for (T at = end; at != null; at = prev.get(at)) {
            path.add(0, at);
        }

        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" --> ");
            }
        }
        System.out.println();
    }
}


	

