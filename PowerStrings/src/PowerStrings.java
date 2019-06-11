public class PowerStrings {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		String current;
		while (!(current = io.getWord()).equals(".")) {
			int lastPre = kmpPre(current);
			int minRepLength = current.length() - lastPre;
			io.println(current.length() / minRepLength);
		}
		io.close();
	}

	public static int kmpPre(String s) {
		int stringLength = s.length();
		int[] val = new int[stringLength + 1];
		int i = 0, j = -1;
		val[0] = -1;
		while (i < stringLength) {
			while (j >= 0 && s.charAt(i) != s.charAt(j)) j = val[j];
			i++;
			j++;
			val[i] = j;
		}
		return val[stringLength];
	}
}
