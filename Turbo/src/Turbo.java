public class Turbo {
	private static int offset;
	private static int[] relativePosition;
	private static int[] numbersToPos;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		final int NUMBERS = io.getInt();
		numbersToPos = new int[NUMBERS];

		int numberArrSize = 2;
		offset = 2;
		while (2 * NUMBERS + 3 > numberArrSize)
			numberArrSize *= 2;
		while (NUMBERS + 2 > offset)
			offset *= 2;
		offset += 1;

		relativePosition = new int[numberArrSize];

		for (int i = 0; i < NUMBERS; i++) {
			numbersToPos[io.getInt() - 1] = i;
		}

		int left = 0;
		int right = NUMBERS - 1;
		for (int round = 1; round <= NUMBERS; round++) {

			if (round % 2 == 0) {

				int orgPos = numbersToPos[right];
				int relativePlacement = value(orgPos);
				int pos = orgPos + relativePlacement;
				int shift = right - pos;
				io.println(Math.abs(shift));
				setRange(orgPos + 1, NUMBERS - 1, -1);
				right--;
			} else {
				int orgPos = numbersToPos[left];
				int relativePlacement = value(orgPos);
				int pos = orgPos + relativePlacement;
				int shift = left - pos;
				io.println(Math.abs(shift));
				setRange(0, orgPos - 1, 1);
				left++;
			}
		}
		io.close();
	}

	public static int value(int index) {
		int sum = 0;
		int actualPos = offset + index;
		sum += relativePosition[actualPos];
		while (actualPos > 1) {
			actualPos /= 2;
			sum += relativePosition[actualPos];
		}
		return sum;
	}

	public static void setRange(int start, int stop, int val) {
		start += offset - 1;
		stop += offset + 1;
		while (true) {
			boolean leftManGoesRight = start % 2 == 0;
			boolean rightManGoesLeft = stop % 2 == 1;
			start = start / 2;
			stop = stop / 2;
			if (start == stop)
				break;
			if (leftManGoesRight) {
				relativePosition[start * 2 + 1] += val;
			}
			if (rightManGoesLeft) {
				relativePosition[stop * 2] += val;
			}
		}
	}
}
