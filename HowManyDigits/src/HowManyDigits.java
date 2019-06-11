public class HowManyDigits {

	public static void main(String[] args) {
		double[] numbers = new double[1_000_001];

		for (int i = 1; i < 1_000_001; i++) {
			numbers[i] = Math.log10(i) + numbers[i - 1];
		}
		
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int num = io.getInt();
			if (num < 4) {
				io.println(1);
			} else {
				io.println((int) Math.ceil(numbers[num]));
			}
		}
		io.close();
	}
}
