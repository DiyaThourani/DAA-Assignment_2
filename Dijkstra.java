public class Dijkstra {

    static final int INF = 999999;

    public static void main(String[] args) {

        int n = 9;
        int[][] graph = new int[n][n];
        graph[0][1] = 4; graph[1][0] = 4;
        graph[0][7] = 8; graph[7][0] = 8;
        graph[1][2] = 8; graph[2][1] = 8;
        graph[1][7] = 11; graph[7][1] = 11;
        graph[2][3] = 7; graph[3][2] = 7;
        graph[2][8] = 2; graph[8][2] = 2;
        graph[2][5] = 4; graph[5][2] = 4;
        graph[3][4] = 9; graph[4][3] = 9;
        graph[3][5] = 14; graph[5][3] = 14;
        graph[4][5] = 10; graph[5][4] = 10;
        graph[5][6] = 2; graph[6][5] = 2;
        graph[6][7] = 1; graph[7][6] = 1;
        graph[6][8] = 6; graph[8][6] = 6;
        graph[7][8] = 7; graph[8][7] = 7;

        int[] d = new int[n];
        int[] pred = new int[n];
        char[] color = new char[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            d[i] = INF;
            pred[i] = -1;
            color[i] = 'W';
        }
        d[0] = 0;

        int step = 0;
        System.out.println("Step " + step + ": Initialization");
        printStuff(d, pred, color, visited, n);
        for (int count = 0; count < n; count++) {

            int u = -1;
            int min = INF;
            for (int i = 0; i < n; i++) {
                if (!visited[i] && d[i] < min) {
                    min = d[i];
                    u = i;
                }
            }

            if (u == -1) {
                break;
            }

            visited[u] = true;
            color[u] = 'B';

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != 0 && !visited[v]) {
                    if (d[u] + graph[u][v] < d[v]) {
                        d[v] = d[u] + graph[u][v];
                        pred[v] = u;
                    }
                }
            }

            step++;
            System.out.println("Step " + step + ": picked node " + u + " (smallest dist), updated its neighbors");
            printStuff(d, pred, color, visited, n);
        }

        System.out.println("queue is empty now, done\n");

        System.out.println("final dist + pred:");
        for (int i = 0; i < n; i++) {
            System.out.println("node " + i + " -> dist=" + d[i] + " pred=" + pred[i]);
        }

        System.out.println();
        System.out.println("shortest path tree edges:");
        for (int i = 0; i < n; i++) {
            if (pred[i] != -1) {
                System.out.println(pred[i] + " -> " + i + " (w=" + graph[pred[i]][i] + ")");
            }
        }
    }
    static void printStuff(int[] d, int[] pred, char[] color, boolean[] visited, int n) {
        System.out.print("v: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        System.out.print("d[v]: ");
        for (int i = 0; i < n; i++) {
            if (d[i] == INF) System.out.print("inf\t");
            else System.out.print(d[i] + "\t");
        }
        System.out.println();

        System.out.print("pred[v]: ");
        for (int i = 0; i < n; i++) {
            if (pred[i] == -1) System.out.print("nil\t");
            else System.out.print(pred[i] + "\t");
        }
        System.out.println();

        System.out.print("color[v]: ");
        for (int i = 0; i < n; i++) {
            System.out.print(color[i] + "\t");
        }
        System.out.println();
        System.out.print("PQ v: ");
        for (int i = 0; i < n; i++) {
            if (!visited[i]) System.out.print(i + "\t");
        }

        System.out.print("PQ d[v]: ");
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (d[i] == INF) System.out.print("inf\t");
                else System.out.print(d[i] + "\t");
            }
        }
        System.out.println();
        System.out.println();
    }
}