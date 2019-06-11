public class MaximizingYourWinnings {

	private static int rooms;
	private static int[][] roomsScoreFromTo;
	private static int[][] maxTable;
	private static int[][] minTable;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		rooms = io.getInt();
		while (rooms != 0) {
			roomsScoreFromTo = new int[rooms][rooms];
			for (int to = 0; to < rooms; to++) {
				for (int from = 0; from < rooms; from++) {
					roomsScoreFromTo[from][to] = io.getInt();
				}

			}
			int turns = io.getInt();

			maxTable = new int[rooms][turns];
			minTable = new int[rooms][turns];
			for (int i = 0; i < rooms; i++) {
				for (int j = 0; j < turns; j++) {
					maxTable[i][j] = -1;
					minTable[i][j] = -1;
				}
			}

			io.println(maxPath(0, turns) + " " + minPath(0, turns));
			rooms = io.getInt();
		}
		io.close();
	}

	public static int maxPath(int current, int turn) {
		if (turn == 0)
			return 0;

		if (maxTable[current][turn - 1] != -1)
			return maxTable[current][turn - 1];

		int result = 0;
		for (int y = 0; y < rooms; y++) {
			result = Math.max(roomsScoreFromTo[y][current] + maxPath(y, turn - 1), result);
		}
		maxTable[current][turn - 1] = result;
		return result;
	}

	public static int minPath(int current, int turn) {
		if (turn == 0)
			return 0;

		if (minTable[current][turn - 1] != -1)
			return minTable[current][turn - 1];

		int result = Integer.MAX_VALUE;
		for (int y = 0; y < rooms; y++) {
			result = Math.min(roomsScoreFromTo[y][current] + minPath(y, turn - 1), result);
		}
		minTable[current][turn - 1] = result;
		return result;
	}
}
