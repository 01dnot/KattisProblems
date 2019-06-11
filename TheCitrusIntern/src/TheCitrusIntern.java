import java.util.ArrayList;
import java.util.HashSet;

public class TheCitrusIntern {

    public static long[] in;
    public static long[] outUp;
    public static long[] outDown;
    public static ArrayList<HashSet<Integer>> graph;
    public static long[] w;

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        final int MEMBERS = io.getInt();
        w = new long[MEMBERS];
        in = new long[MEMBERS];
        outDown = new long[MEMBERS];
        outUp = new long[MEMBERS];
        boolean[] isTop = new boolean[MEMBERS];

        graph = new ArrayList<>(MEMBERS);
        for (int i = 0; i < MEMBERS; i++) {
            graph.add(new HashSet<>());
            w[i] = io.getInt();
            int subs = io.getInt();
            for (int j = 0; j < subs; j++) {
                int neib = io.getInt();
                isTop[neib] = true;
                graph.get(i).add(neib);
            }
        }
        int top = 0;
        for (int i = 0; i < isTop.length; i++) {
            if (!isTop[i]) top = i;
        }

        fillTable(top);
        System.out.println(Math.min(in[top], outDown[top]));

    }

    public static void fillTable(int v) {

        in[v] = w[v];
        outDown[v] = Integer.MAX_VALUE;
        outUp[v] = 0;
        if (graph.get(v).isEmpty()) return;

        long sum = 0;
        long delta = Integer.MAX_VALUE;
        for (int child : graph.get(v)) {
            fillTable(child);
            in[v] += outUp[child];
            outUp[v] += Math.min(in[child], outDown[child]);

            sum += Math.min(in[child], outDown[child]);
            delta = Math.min(delta, Math.max(in[child] - outDown[child], 0));
        }
        outDown[v] = sum + delta;
    }

}
