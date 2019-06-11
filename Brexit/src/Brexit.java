import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Brexit {

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int C = io.getInt(), P = io.getInt(), X = io.getInt(), L = io.getInt();
        int[] startDegree = new int[C];
        HashMap<Integer, HashSet<Integer>> graph = new HashMap<>(C);

        for (int i = 0; i < C; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < P; i++) {
            int countryA = io.getInt(), countryB = io.getInt();
            graph.get(countryA - 1).add(countryB - 1);
            graph.get(countryB - 1).add(countryA - 1);
        }
        for (int i = 0; i < graph.size(); i++) {
            startDegree[i] = graph.get(i).size();
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(L - 1);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            if (current == X - 1) {
                System.out.println("leave");
                return;
            }

            for (int w : graph.get(current)) {
                graph.get(w).remove(current);
                if (graph.get(w).size() <= startDegree[w] / 2) {
                    queue.add(w);
                }
            }
            graph.get(current).clear();
        }
        System.out.println("stay");
        io.close();
    }
}