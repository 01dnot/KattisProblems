import java.util.ArrayList;
import java.util.HashMap;

public class BirthdayParty {

	private static int[] pre;
	private static int[] low;
	private static int counter;
	private static boolean hasBridge;

	public static void main(String[] args) {

		Kattio io = new Kattio(System.in, System.out);
		int a = io.getInt(), b = io.getInt();
		while (a != 0 && b != 0) {
			hasBridge = false;
			counter = 0;
			pre = new int[a];
			low = new int[a];

			for (int i = 0; i < a; i++) {
				pre[i] = -1;
				low[i] = -1;
			}

			HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>(a);
			for (int i = 0; i < a; i++) {
				graph.put(i, new ArrayList<>());
			}

			for (int i = 0; i < b; i++) {
				int nodeA = io.getInt(), nodeB = io.getInt();
				graph.get(nodeA).add(nodeB);
				graph.get(nodeB).add(nodeA);
			}
			
			for(int i = 0; i < a; i++) {
				if(pre[i] == -1) {
					dfs(graph, i, i);
				}
			}

			if (hasBridge) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}

			a = io.getInt();
			b = io.getInt();
		}
		io.close();
	}

	private static void dfs(HashMap<Integer, ArrayList<Integer>> graph, int former, int v) {
		pre[v] = counter++;
		low[v] = pre[v];
		for (int w : graph.get(v)) {
			if (pre[w] == -1) {
				dfs(graph, v, w);
				low[v] = Math.min(low[w], low[v]);
				if (low[w] == pre[w]) {
					hasBridge = true;
				}
			} else if (w != former) {
				low[v] = Math.min(low[w], low[v]);
			}
		}
	}

}
