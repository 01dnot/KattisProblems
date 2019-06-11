import java.util.Comparator;
import java.util.PriorityQueue;

public class IslandHopping {

    private static class Edge {
        private int aIsland;
        private int bIsland;
        private double weight;

        public Edge(int aIsland, int bIsland, double weight) {
            this.aIsland = aIsland;
            this.bIsland = bIsland;
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }
    }

    private static class UF {
        private int[] id;
        private int components;

        public UF(int size) {
            id = new int[size];
            components = size;
            for (int i = 0; i < size; i++) {
                id[i] = i;
            }
        }

        public boolean connected(int a, int b) {
            return find(a) == find(b);
        }

        public void union(int a, int b) {
            int aKey = find(a);
            int bKey = find(b);
            if (aKey == bKey)
                return;
            id[aKey] = bKey;
            components--;
        }

        public int find(int a) {
            while (a != id[a]) {
                a = id[a];
            }
            return a;
        }

    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        final int CASES = io.getInt();
        for (int c = 0; c < CASES; c++) {
            int islands = io.getInt();
            UF uf = new UF(islands);
            PriorityQueue<Edge> pq = new PriorityQueue<Edge>(Comparator.comparingDouble(Edge::getWeight));
            double[][] cord = new double[islands][2];
            for (int i = 0; i < islands; i++) {
                cord[i][0] = io.getDouble();
                cord[i][1] = io.getDouble();
            }
            for (int i = 0; i < islands; i++) {
                for (int j = i + 1; j < islands; j++) {
                    double distance = calcdistace(cord[i][0], cord[i][1], cord[j][0], cord[j][1]);
                    pq.add(new Edge(i, j, distance));
                }
            }
            double sumCost = 0;
            while (uf.components > 1) {
                Edge e = pq.remove();
                if (!uf.connected(e.aIsland, e.bIsland)) {
                    uf.union(e.aIsland, e.bIsland);
                    sumCost += e.weight;
                }
            }
            io.println(sumCost);

        }
        io.close();
    }

    private static double calcdistace(double islandAx, double islandAy, double islandBx, double islandBy) {
        return Math.sqrt(Math.pow((islandBx - islandAx), 2) + Math.pow((islandBy - islandAy), 2));
    }

}
