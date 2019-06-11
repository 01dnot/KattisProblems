public class MapColoring {

	private static boolean[][] adjMatrix;
	private static int[] nodeColor;
	private static int countries;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int testCases = io.getInt();
		testCases: for (int test = 0; test < testCases; test++) {
			countries = io.getInt();
			int boarders = io.getInt();
			if (boarders == 0) { //If not connected at all, only 1 color needed
				io.println(1);
				continue;
			}

			adjMatrix = new boolean[countries][countries];
			nodeColor = new int[countries];
			for (int i = 0; i < boarders; i++) {
				int from = io.getInt();
				int to = io.getInt();
				adjMatrix[from][to] = true;
				adjMatrix[to][from] = true;
			}

			if (countries == 2) { //If 2 contries, 2 colors needed
				io.println(2);
				continue;
			}

			for (int i = 2; i < 5; i++) {
				if (colorGraph(0, i)) {
					io.println(i);
					continue testCases;
				}
			}
			io.println("many");
		}
		io.close();
	}

	private static boolean colorGraph(int node, int maxColors) {
		if (node >= countries) {
			return true;
		}
		for (int color = 1; color <= maxColors; color++) {
			if (canColorNode(node, color)) {
				nodeColor[node] = color;
				if (colorGraph(node + 1, maxColors)) {
					return true;
				}
				nodeColor[node] = 0;
			}
		}
		return false;
	}

	private static boolean canColorNode(int node, int color) {
		for (int neib = 0; neib < countries; neib++) {
			if (adjMatrix[node][neib] && nodeColor[neib] == color) {
				return false;
			}
		}
		return true;
	}

}