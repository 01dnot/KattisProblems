
public class HidingChickens {

	private static double[][] disChickToHiding;
	private static double[] disFromToChickens;
	private static double[] memoizingSet;
	private static int CHICKENS;
	private static int ALL_CHICKENS_VISITED_SET;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		double foxX = io.getDouble();
		double foxY = io.getDouble();
		CHICKENS = io.getInt();
		double[][] hidingSpots = new double[CHICKENS][2];
		ALL_CHICKENS_VISITED_SET = (1 << CHICKENS) - 1;
		memoizingSet = new double[ALL_CHICKENS_VISITED_SET];

		
		for (int i = 0; i < CHICKENS; i++) {
			hidingSpots[i][0] = io.getDouble();
			hidingSpots[i][1] = io.getDouble();
		}
		
		disFromToChickens = new double[CHICKENS];
		for (int i = 0; i < CHICKENS; i++) {
			disFromToChickens[i] = Math.sqrt(Math.pow(hidingSpots[i][0] - foxX, 2) + Math.pow(hidingSpots[i][1] - foxY, 2));
		}
		
		disChickToHiding = new double[CHICKENS][CHICKENS];
		for (int i = 0; i < CHICKENS; i++) {
			for (int j = 0; j < CHICKENS; j++) {
				if (i == j)
					continue;
				double distance = Math.sqrt(Math.pow(hidingSpots[i][0] - hidingSpots[j][0], 2)
						+ Math.pow(hidingSpots[i][1] - hidingSpots[j][1], 2));
				disChickToHiding[i][j] = distance;
				disChickToHiding[j][i] = distance;
			}
		}
		
		io.println(hideChickens(0)); // HIDING ALL CHICKENS, STARTING WITH EMPTY SET
		io.close();
	}

	public static double hideChickens(int currentVisitedSet) {
		if (memoizingSet[currentVisitedSet] != 0) { // BEEN HERE, DONE THAT
			return memoizingSet[currentVisitedSet];
		}

		double lowestCostForSet = Double.MAX_VALUE;

		for (int fstChick = 0; fstChick < CHICKENS; fstChick++) { // TRY GOING TO ALL UNVISTED CHICKENS
			if ((currentVisitedSet & (1 << fstChick)) == 0) { // IF NOT VISITED CHICKEN YET
				int newSet = currentVisitedSet | (1 << fstChick);
				if (newSet == ALL_CHICKENS_VISITED_SET) { // ONLY THIS CHICKEN LEFT!
					lowestCostForSet = Math.min(lowestCostForSet, disFromToChickens[fstChick]);
					break;
				} else { // VISIT HIDINGSPOT AND GO BACK TO HIDE THE REST!
					double cost = disFromToChickens[fstChick] + disFromToChickens[fstChick] + hideChickens(newSet);
					lowestCostForSet = Math.min(lowestCostForSet, cost);
				}
				for (int sndChick = 0; sndChick < CHICKENS; sndChick++) {
					if ((newSet & (1 << sndChick)) == 0) { // IF NOT VISITED
						int newnewSet = newSet | (1 << sndChick); // INCLUDE IN SET
						if (newnewSet == ALL_CHICKENS_VISITED_SET) { // ALL CHICKENS COLLECTED
							lowestCostForSet = Math.min(lowestCostForSet,
									disFromToChickens[fstChick] + disChickToHiding[fstChick][sndChick]);
						} else { // FULL ROUND
							double cost = disFromToChickens[fstChick] + disChickToHiding[fstChick][sndChick]
									+ disFromToChickens[sndChick] + hideChickens(newnewSet);
							lowestCostForSet = Math.min(lowestCostForSet, cost);
						}
					}
				}
			}
		}
		memoizingSet[currentVisitedSet] = lowestCostForSet;
		return lowestCostForSet;
	}

}