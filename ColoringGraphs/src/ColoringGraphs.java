import java.util.Scanner;

public class ColoringGraphs {
	private static boolean[][] adjMatrix;
	private static int[] nodeColor;
	private static int V;

	public static void main(String[] args) {
		Scanner io = new Scanner(System.in);
		V = io.nextInt();
		io.nextLine();
		nodeColor = new int[V];
		adjMatrix = new boolean[V][V];
		boolean completeGraph = true;
		for (int i = 0; i < V; i++) {
			String[] numbers = io.nextLine().split(" ");
			if (numbers.length < V - 1)
				completeGraph = false;
			for (String s : numbers) {
				int num = Integer.parseInt(s);
				adjMatrix[i][num] = true;
				adjMatrix[num][i] = true;
			}
		}
		if (completeGraph) {
			System.out.println(V);
			io.close();
			return;
		}
		int i = 2;
		while (!colorGraph(0, i)) {
			i++;
		}
		System.out.println(i);
		io.close();
	}

	private static boolean colorGraph(int node, int maxColors) {
		if (node >= V) {
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
		for (int neib = 0; neib < V; neib++) {
			if (adjMatrix[node][neib] && nodeColor[neib] == color) {
				return false;
			}
		}
		return true;
	}

}
