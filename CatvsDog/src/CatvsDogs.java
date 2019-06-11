import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class CatvsDogs {
	static int[][] currentCAP;
	static int[] path;
	static int arrSize;
	static ArrayList<HashSet<Integer>> graph;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		final int TESTCASES = io.getInt();
		for (int test = 0; test < TESTCASES; test++) {
			int CATS = io.getInt();
			int DOGS = io.getInt();
			int VOTERS = io.getInt();
			String[][] votes = new String[VOTERS][2];
			HashMap<String, HashSet<Integer>> keepAni = new HashMap<>(VOTERS);
			HashMap<String, HashSet<Integer>> removeAni = new HashMap<>(VOTERS);
			arrSize = VOTERS + 2;
			currentCAP = new int[arrSize][arrSize];
			graph = new ArrayList<>(arrSize);

			for (int i = 0; i < arrSize; i++) {
				graph.add(new HashSet<>());
			}

			ArrayList<Integer> dogVoters = new ArrayList<>();
			ArrayList<Integer> catVoters = new ArrayList<>();

			for (int i = 0; i < VOTERS; i++) {
				String keeping = io.getWord();
				String removing = io.getWord();
				votes[i][0] = keeping;
				votes[i][1] = removing;
				if (keeping.charAt(0) == 'D') {
					dogVoters.add(i);
				} else {
					catVoters.add(i);
				}

				if (!keepAni.containsKey(keeping)) {
					keepAni.put(keeping, new HashSet<>());
				}
				if (!removeAni.containsKey(removing)) {
					removeAni.put(removing, new HashSet<>());
				}
				keepAni.get(keeping).add(i);
				removeAni.get(removing).add(i);
			}

			for (int voter : dogVoters) {
				currentCAP[graph.size() - 2][voter] = 1;
				String myIn = votes[voter][0];
				String myOut = votes[voter][1];
				if (removeAni.containsKey(myIn)) {
					removeAni.get(myIn).forEach(x -> {
						graph.get(voter).add(x);
						currentCAP[voter][x] = Integer.MAX_VALUE;
					});
				}
				if (keepAni.containsKey(myOut)) {
					keepAni.get(myOut).forEach(x -> {
						graph.get(voter).add(x);
						currentCAP[voter][x] = Integer.MAX_VALUE;
					});
				}
				graph.get(graph.size() - 2).add(voter);
			}
			for (int voter : catVoters) {
				currentCAP[voter][graph.size() - 1] = 1;

				String myIn = votes[voter][0];
				String myOut = votes[voter][1];
				if (removeAni.containsKey(myIn)) {
					graph.get(voter).addAll(removeAni.get(myIn));

				}
				if (keepAni.containsKey(myOut)) {
					graph.get(voter).addAll(keepAni.get(myOut));
				}

				graph.get(voter).add(graph.size() - 1);
			}

			io.println(VOTERS - fordFulkerson());
		}
		io.close();
	}

	private static boolean existsAugmentedPath(int from, int to) {
		boolean[] visited = new boolean[arrSize];
		path = new int[arrSize];

		Queue<Integer> queue = new LinkedList<>();
		queue.add(from);
		visited[from] = true;
		path[from] = -1;
		while (!queue.isEmpty()) {
			int current = queue.remove();
			for (int v : graph.get(current)) {
				if (currentCAP[current][v] > 0 && !visited[v]) {
					queue.add(v);
					path[v] = current;
					visited[v] = true;
				}
			}
		}
		return visited[to];
	}

	public static int fordFulkerson() {
		int source = currentCAP.length - 2;
		int dest = currentCAP.length - 1;

		int maxFlow = 0;
		while (existsAugmentedPath(source, dest)) {

			int minPathFlow = Integer.MAX_VALUE;
			for (int v = dest; v != source; v = path[v]) {
				int u = path[v];
				minPathFlow = Math.min(minPathFlow, currentCAP[u][v]);
			}

			for (int v = dest; v != source; v = path[v]) {
				int u = path[v];
				currentCAP[u][v] -= minPathFlow;
				currentCAP[v][u] += minPathFlow;
			}
			maxFlow += minPathFlow;
		}
		return maxFlow;
	}
}
