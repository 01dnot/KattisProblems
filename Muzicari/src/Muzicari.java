import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Muzicari {

	public static class Tuple implements Comparable<Tuple> {
		int index;
		int time;

		public Tuple(int index, int time) {
			this.index = index;
			this.time = time;
		}

		@Override
		public String toString() {
			return "(" + index + ", " + time + ")";
		}
		
		public int compareTo(Tuple other) {
			return Integer.compare(time, other.time);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + index;
			result = prime * result + time;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tuple other = (Tuple) obj;
			if (index != other.index)
				return false;
			if (time != other.time)
				return false;
			return true;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
		
		
	}

	static ArrayList<Tuple> pauses;
	static int[][] maxSize;
	static int T, N;
	static List<Tuple> list;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		T = io.getInt();
		N = io.getInt();
		pauses = new ArrayList<>();
		list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			pauses.add(new Tuple(i,io.getInt()));
		}
		
		Collections.sort(pauses);
		maxSize = new int[T + 1][N];

		for (int t = 0; t <= T; t++) {
			for (int n = 0; n < N; n++) {
				maxSize[t][n] = Math.max(take(t, n), drop(t, n));
			}
		}
		findPauses(T, N - 1);
		Collections.sort(list, Comparator.comparingInt(Tuple::getIndex));
		List<Tuple> list2 = pauses.stream()
				.filter(x -> !list.contains(x))
				.sorted(Comparator.comparingInt(Tuple::getIndex))
				.collect(Collectors.toList());
		
		
		List<Tuple> tupleList1 = findStartTimes(list);
		List<Tuple> tupleList2 = findStartTimes(list2);


		int p1 = 0, p2 = 0;
		while(p1 < tupleList1.size() && p2 < tupleList2.size()) {
			if(tupleList1.get(p1).index > tupleList2.get(p2).index) {
				System.out.print(tupleList2.get(p2++).time + " ");
			} else {
				System.out.print(tupleList1.get(p1++).time + " ");
			}
		}
		while(p1 < tupleList1.size()) {
			System.out.print(tupleList1.get(p1++).time + " ");
		}
		while(p2 < tupleList2.size()) {
			System.out.print(tupleList2.get(p2++).time + " ");
		}


	}

	private static int drop(int t, int n) {
		return n == 0 ? 0 : maxSize[t][n - 1];
	}

	private static int take(int t, int n) {
		if (pauses.get(n).time <= t) {
			return n == 0 ? pauses.get(n).time : pauses.get(n).time + maxSize[t - pauses.get(n).time][n - 1];
		} else {
			return drop(t, n);
		}
	}

	private static void findPauses(int t, int n) {
		if (t <= 0) {
			return;
		} else if (n == 0 && maxSize[t][n] != 0) {
			list.add(pauses.get(n));
		} else if (maxSize[t][n] == maxSize[t][n - 1]) {
			findPauses(t, n - 1);
		} else {
			list.add(pauses.get(n));
			findPauses(t - pauses.get(n).time, n - 1);
		}
	}

	public static List<Tuple> findStartTimes(List<Tuple> list) {
		int totalTime = 0;
		List<Tuple> tupleList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			tupleList.add(new Tuple(list.get(i).index, totalTime));
			totalTime += list.get(i).time;
		}
		return tupleList;
	}

}
