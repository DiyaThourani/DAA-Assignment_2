import java.util.*;

public class EdmondsKarp {

    static int V = 7;
    static int[][] cap = new int[V][V];
    static int[][] original = new int[V][V];
    static String[] name = {"A","B","C","D","E","F","G"};
    static void addEdge(int u, int v, int c) {
        cap[u][v] = c;
        original[u][v] = c;
    }
    static boolean bfs(int source, int sink, int[] parent) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        Arrays.fill(parent, -1);
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) {
                if (!visited[v] && cap[u][v] > 0) {
                    visited[v] = true;
                    parent[v] = u;
                    if (v == sink) return true;
                    queue.add(v);
                }
            }
        }
        return false;
    }

    static int maxFlow(int source, int sink) {
        int[] parent = new int[V];
        int totalFlow = 0;

        while (bfs(source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            int v = sink;
            while (v != source) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, cap[u][v]);
                v = parent[v];
            }

            printPath(parent, source, sink, pathFlow);

            v = sink;
            while (v != source) {
                int u = parent[v];
                cap[u][v] -= pathFlow;
                cap[v][u] += pathFlow;
                v = parent[v];
            }

            totalFlow += pathFlow;
        }
        return totalFlow;
    }

    static void minCut(int source) {

        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < V; v++) {
                if (!visited[v] && cap[u][v] > 0) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }

        System.out.println("\nMin-Cut Edges:");
        int cutCapacity = 0;
        for (int u = 0; u < V; u++) {
            for (int v = 0; v < V; v++) {
                if (visited[u] && !visited[v] && original[u][v] > 0) {
                    System.out.println("  " + name[u] + " -> " + name[v]
                            + "  (capacity = " + original[u][v] + ")");
                    cutCapacity += original[u][v];
                }
            }
        }
        System.out.println("Min-Cut Capacity = " + cutCapacity);
    }

    static void printPath(int[] parent, int source, int sink, int flow) {
        List<Integer> path = new ArrayList<>();
        int v = sink;
        while (v != source) { path.add(v); v = parent[v]; }
        path.add(source);
        Collections.reverse(path);

        System.out.print("  Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(name[path.get(i)]);
            if (i < path.size() - 1) System.out.print(" -> ");
        }
        System.out.println("  | Flow: " + flow);
    }

    public static void main(String[] args) {

        addEdge(0, 3, 3);
        addEdge(0, 1, 3);
        addEdge(2, 0, 3);
        addEdge(2, 3, 1);
        addEdge(2, 4, 2);
        addEdge(1, 2, 4);
        addEdge(3, 5, 6);
        addEdge(3, 4, 2);
        addEdge(4, 1, 1);
        addEdge(4, 6, 1);
        addEdge(5, 6, 9);

        System.out.println("----Edmonds-Karp Algorithm-----");
        System.out.println("\n Edges in graph:");
        System.out.println("  A->D:3, A->B:3, C->A:3, C->D:1");
        System.out.println("  C->E:2, B->C:4, D->F:6, D->E:2");
        System.out.println("  E->B:1, E->G:1, F->G:9");

        System.out.println("\nAugmenting Paths found by BFS:");
        int flow = maxFlow(0, 6); // A=0, G=6

        System.out.println("\nMaximum Flow = " + flow);
        minCut(0);
    }
}