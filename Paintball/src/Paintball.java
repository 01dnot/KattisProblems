public class Paintball {

	static int arrSize;
	static boolean[][] adjMatrix;
	static int[] path;
	static int[] matching;
	static boolean[] visited;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		int NODES = io.getInt();
		arrSize = NODES+1;
		adjMatrix = new boolean[arrSize][arrSize];
		matching = new int[arrSize];

		final int EDGES = io.getInt();
		for (int i = 0; i < EDGES; i++) {
			int a = io.getInt();
			int b = io.getInt();
			adjMatrix[a][b] = true;
			adjMatrix[b][a] = true;
		}

		int matches = 0;
		for (int u = 1; u < arrSize; u++) {
			visited = new boolean[arrSize];
			if (existsAugmentedPath(u)) {
				matches++;
			}
		}
		if (matches < NODES) {
			System.out.println("Impossible");
		} else {
			for (int i = 1; i < arrSize; i++) {
				io.println(matching[i]);
			}
		}
		io.close();
	}

	private static boolean existsAugmentedPath(int u) {
		for (int v = 1; v < arrSize; v++) {
			if (adjMatrix[u][v] && !visited[v]) {
				visited[v] = true;
				if (matching[v] == 0 || existsAugmentedPath(matching[v])) { //NO PAIRING OR CHANGE OLD PAIRING
					matching[v] = u;
					return true;
				}
			}
		}
		return false;
	}
}
