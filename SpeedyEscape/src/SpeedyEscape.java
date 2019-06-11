import java.util.HashMap;
import java.util.HashSet;

public class SpeedyEscape {

    private static double[] policeDistance;
    private static boolean[] policeVisited;
    private static double[] robberDistance;
    private static boolean[] robberVisited;
    private static boolean[] speedVisited;
    private static double[] speedDistance;

    public static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int nodes = io.getInt();
        int edges = io.getInt();
        int exits = io.getInt();
        policeDistance = new double[nodes];
        policeVisited = new boolean[nodes];
        robberDistance = new double[nodes];
        robberVisited = new boolean[nodes];
        speedVisited = new boolean[nodes];
        speedDistance = new double[nodes];
        HashMap<Integer, HashSet<Edge>> graph = new HashMap<>();
        for (int i = 0; i < nodes; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < edges; i++) {
            int from = io.getInt() - 1;
            int to = io.getInt() - 1;
            int weight = io.getInt();
            Edge e = new Edge(from, to, weight);
            graph.get(from).add(e);
            graph.get(to).add(e);
        }

        int[] exitsArr = new int[exits];
        for (int i = 0; i < exits; i++) {
            exitsArr[i] = io.getInt() - 1;
        }

        int robberPos = io.getInt() - 1;
        int policePos = io.getInt() - 1;

        if (robberPos == policePos) {
            System.out.println("IMPOSSIBLE");
            io.close();
            return;
        }

        policeDjikstra(policePos, graph);
        robberDjikstra(robberPos, policePos, graph);
        speedDjikstra(robberPos, policePos, graph);
        int count = 0;
        for (int exit : exitsArr) {
            if (robberDistance[exit] == Double.MAX_VALUE)
                count++;
        }
        if (count == exitsArr.length) {
            System.out.println("IMPOSSIBLE");
            io.close();
            return;
        }

        double lowestAvarageSpeed = Double.MAX_VALUE;
        for (int exit : exitsArr) {
            if (speedDistance[exit] < lowestAvarageSpeed) {
                lowestAvarageSpeed = speedDistance[exit];
            }
        }

        io.print(lowestAvarageSpeed);
        io.close();

    }

    public static boolean containsFalse(boolean[] arr) {
        for (boolean val : arr) {
            if (!val) {
                return true;
            }
        }
        return false;
    }

    public static int getSmallestUnvistedDist(double[] distance, boolean[] visited) {
        double smallest = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] <= smallest && !visited[i]) {
                smallest = distance[i];
                index = i;
            }

        }
        return index;
    }

    public static void policeDjikstra(int policePos, HashMap<Integer, HashSet<Edge>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            policeDistance[i] = Double.MAX_VALUE;
        }
        policeDistance[policePos] = 0;
        for (int j = 0; j < graph.size(); j++) {
            policeVisited[j] = false;
        }

        while (containsFalse(policeVisited)) {
            int v = getSmallestUnvistedDist(policeDistance, policeVisited);
            policeVisited[v] = true;

            for (Edge neib : graph.get(v)) {
                int other = neib.from == v ? neib.to : neib.from;
                if (!policeVisited[other] && policeDistance[other] > policeDistance[v] + neib.weight) {
                    policeDistance[other] = policeDistance[v] + neib.weight;
                }
            }
        }
    }

    public static void robberDjikstra(int robberPos, int policePos, HashMap<Integer, HashSet<Edge>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            robberDistance[i] = Double.MAX_VALUE;
        }

        robberDistance[robberPos] = 0;
        for (int j = 0; j < graph.size(); j++) {
            robberVisited[j] = false;
        }

        robberVisited[policePos] = true;
        while (containsFalse(robberVisited)) {

            int v = getSmallestUnvistedDist(robberDistance, robberVisited);
            robberVisited[v] = true;

            for (Edge neib : graph.get(v)) {
                int other = neib.from == v ? neib.to : neib.from;
                if (!robberVisited[other] && robberDistance[other] > robberDistance[v] + neib.weight) {
                    robberDistance[other] = robberDistance[v] + neib.weight;
                }
            }
        }
    }

    public static void speedDjikstra(int robberPos, int policePos, HashMap<Integer, HashSet<Edge>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            speedDistance[i] = Double.MAX_VALUE;
        }

        speedDistance[robberPos] = 0;
        for (int j = 0; j < graph.size(); j++) {
            speedVisited[j] = false;
        }

        speedVisited[policePos] = true;
        while (containsFalse(speedVisited)) {
            int v = getSmallestUnvistedDist(speedDistance, speedVisited);
            speedVisited[v] = true;

            for (Edge neib : graph.get(v)) {
                int other = neib.from == v ? neib.to : neib.from;
                double policeTimeToDest = policeDistance[other] / 160.0;
                double robberSpeed = robberDistance[other] / policeTimeToDest;
                if (!speedVisited[other] && speedDistance[other] > Math.max(speedDistance[v], robberSpeed)) {
                    speedDistance[other] = Math.max(speedDistance[v], robberSpeed);
                }
            }
        }
    }
}
