import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class ConvexHull {

	private static ArrayList<Vector> pointList;
	private static ArrayList<Vector> polygon;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);

		int points = io.getInt();
		while(points  != 0) {
			if(points == 1) {
				System.out.println(1);
				System.out.println(io.getInt() + " " + io.getInt());
				points = io.getInt();
				continue;
			}
				
			pointList = new ArrayList<>();
			polygon = new ArrayList<>();
			for(int i = 0; i < points; i++) {
				pointList.add(new Vector(io.getInt(), io.getInt()));
			}
		
		Collections.sort(pointList);
		grahamScanAndrewUpper();
		grahamScanAndrewUnder();

		List<Vector> deduped = polygon.stream().distinct().collect(Collectors.toList());
		System.out.println(deduped.size());

		for(int i = deduped.size()-1; i >= 0; i--) {
			int x = (int) deduped.get(i).getX();
			int y = (int) deduped.get(i).getY();
			System.out.println(x + " " + y);
		}
		points = io.getInt();
		}
		io.close();
	}

	private static void grahamScanAndrewUnder() { // Inverted version of grahamScanAndrewUpper
		Deque<Vector> stack = new ArrayDeque<>();
		stack.push(pointList.get(0).invert());
		stack.push(pointList.get(1).invert());
		for (int i = 2; i < pointList.size(); i++) {
			stack.push(pointList.get(i).invert());
			while (formsAleftTurn(stack) && stack.size() >= 3) {
			}
		}
		for (Vector e : stack) {
			polygon.add(e.invert());
		}
	}

	public static void grahamScanAndrewUpper() {
		Deque<Vector> stack = new ArrayDeque<>();
		stack.push(pointList.get(0));
		stack.push(pointList.get(1));
		for (int i = 2; i < pointList.size(); i++) {
			stack.push(pointList.get(i));
			while (formsAleftTurn(stack) && stack.size() >= 3) {
			}
		}
		while (!stack.isEmpty()) {
			polygon.add(stack.getLast());
			stack.removeLast();
		}
	}

	private static boolean formsAleftTurn(Deque<Vector> stack) { // IF BC is left of AB then drop b.
		Vector c = stack.pop();
		Vector b = stack.pop();
		Vector a = stack.pop();
		if (c.minus(b).isLeftOf(b.minus(a))) {
			stack.push(a);
			stack.push(c);
			return true;
		}
		stack.push(a);
		stack.push(b);
		stack.push(c);
		return false;
	}
}
