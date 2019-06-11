import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class BoardWrapping {

	private static ArrayList<Vector> pointList;
	private static ArrayList<Vector> polygon;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		final int TESTCASES = io.getInt();
		for (int t = 0; t < TESTCASES; t++) {
			int moulds = io.getInt();
			double mouldArea = 0;
			pointList = new ArrayList<>();
			polygon = new ArrayList<>();
			for (int i = 0; i < moulds; i++) {
				double x = io.getDouble();
				double y = io.getDouble();
				double w = io.getDouble();
				double h = io.getDouble();
				double v = Math.toRadians(io.getDouble());

				addRectangleCorners(x,y,w,h,v);
				mouldArea += h * w;
			}
			Collections.sort(pointList); // Sort points first by X value then Y value
			grahamScanAndrewUpper();
			grahamScanAndrewUnder();
			System.out.printf("%.1f", (mouldArea / getPolygonArea()) * 100); // Prints ratio in percent
			System.out.println(" %");
		}
		io.close();

	}

	private static void addRectangleCorners(double x, double y, double w, double h, double v) {
		double upNewX = x + (h / 2 * Math.sin(v));
		double upNewY = y + (h / 2 * Math.cos(v));
		double downNewX = x - (h / 2 * Math.sin(v));
		double downNewY = y - (h / 2 * Math.cos(v));

		// Add all 4 points of the rectangle to the pointList
		double leftUpperCornerX = upNewX + (w / 2 * Math.sin(v + (Math.PI / 2)));
		double leftUpperCornerY = upNewY + (w / 2 * Math.cos(v + (Math.PI / 2)));
		pointList.add(new Vector(leftUpperCornerX, leftUpperCornerY));

		double rightUpperCornerX = upNewX - (w / 2 * Math.sin(v + (Math.PI / 2)));
		double rightUpperCornerY = upNewY - (w / 2 * Math.cos(v + (Math.PI / 2)));
		pointList.add(new Vector(rightUpperCornerX, rightUpperCornerY));

		double leftLowCornerX = downNewX + (w / 2 * Math.sin(v + (Math.PI / 2)));
		double leftLowCornerY = downNewY + (w / 2 * Math.cos(v + (Math.PI / 2)));
		pointList.add(new Vector(leftLowCornerX, leftLowCornerY));

		double rightLowCornerX = downNewX - (w / 2 * Math.sin(v + (Math.PI / 2)));
		double rightLowCornerY = downNewY - (w / 2 * Math.cos(v + (Math.PI / 2)));
		pointList.add(new Vector(rightLowCornerX, rightLowCornerY));
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

	public static double getPolygonArea() { // Calculate area of polygon by determinant calculation MAT121
		double area = 0;
		for (int curr = 0; curr < polygon.size(); curr++) {
			int nxt = (curr + 1) % polygon.size();
			area += (polygon.get(curr).getX() * polygon.get(nxt).getY())
					- (polygon.get(curr).getY() * polygon.get(nxt).getX());
		}
		return Math.abs(area * 0.5);
	}
}
