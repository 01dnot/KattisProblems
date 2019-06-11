import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

public class FenceOrthogonality {
	private static ArrayList<Vector> polygon;
	private static ArrayList<Vector> pointList;
	private static ArrayList<Integer> currentPointForVector;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in, System.out);
		while (io.hasMoreTokens()) {
			int points = io.getInt();
			pointList = new ArrayList<>(points);
			polygon = new ArrayList<>();
			currentPointForVector = new ArrayList<>();
			for (int i = 0; i < points; i++) {
				pointList.add(new Vector(io.getDouble(), io.getDouble()));
			}

			// Create convex hull
			Collections.sort(pointList);
			grahamScanAndrewUpper();
			grahamScanAndrewUnder();

			io.println(findSmallestPerimeterRectangle());
		}
		io.close();
	}

	private static double findSmallestPerimeterRectangle() {
		Vector leftUp = polygon.get(0);
		Vector rightDown = polygon.get(0);
		Vector topRight = polygon.get(0);
		Vector bottomLeft = polygon.get(0);
		currentPointForVector.add(0);
		currentPointForVector.add(0);
		currentPointForVector.add(0);
		currentPointForVector.add(0);

		for (int i = 0; i < polygon.size(); i++) { // Vector : UP, DOWN, LEFT, RIGHT
			Vector e = polygon.get(i);
			if (e.getX() < leftUp.getX()) {
				leftUp = e;
				currentPointForVector.set(0, i);
			}
			if (e.getX() > rightDown.getX()) {
				rightDown = e;
				currentPointForVector.set(1, i);
			}
			if (e.getY() > topRight.getY()) {
				topRight = e;
				currentPointForVector.set(3, i);
			}
			if (e.getY() < bottomLeft.getY()) {
				bottomLeft = e;
				currentPointForVector.set(2, i);
			}
		}

		Vector upVec = new Vector(0, 1);
		Vector downVec = new Vector(0, -1);
		Vector leftVec = new Vector(-1, 0);
		Vector rightVec = new Vector(1, 0);

		int start = currentPointForVector.get(0);
		double minArea = Double.MAX_VALUE;

		for (int i = 0; i < polygon.size(); i++) {

			int current = (start + i) % polygon.size();
			int next = (current + 1) % polygon.size();

			// Find angle all shall move
			double rotateAngle = findAngleABC(upVec, polygon.get(current), polygon.get(next));

			currentPointForVector.set(0, current);
			moveVector(upVec, rotateAngle, current, 0);
			moveVector(downVec, rotateAngle, currentPointForVector.get(1), 1);
			moveVector(leftVec, rotateAngle, currentPointForVector.get(2), 2);
			moveVector(rightVec, rotateAngle, currentPointForVector.get(3), 3);

			// // FINDING WIDTH
			double base = polygon.get(current).minus(polygon.get(next)).distToOrigo();
			double lengthA = polygon.get(currentPointForVector.get(1)).minus(polygon.get(current)).distToOrigo();
			double lengthB = polygon.get(currentPointForVector.get(1)).minus(polygon.get(next)).distToOrigo();

			double s = (base + lengthA + lengthB) / 2;
			double area = Math.sqrt(s * (s - base) * (s - lengthA) * (s - lengthB));
			double width = 2 * area / base;

			double height = base;
			// FINDING HEIGHT 1
			Vector a = polygon.get(next).minus(polygon.get(current));
			Vector b = polygon.get(currentPointForVector.get(3)).minus(polygon.get(next));
			double aDotB = a.dot(b);
			double aDotA = (a.dot(a));
			Vector newVec = a.times((aDotB / aDotA));
			double length = newVec.distToOrigo();
			height += length;

			// FINDING HEIGHT 2
			b = polygon.get(currentPointForVector.get(2)).minus(polygon.get(current));
			aDotB = a.dot(b);
			newVec = a.times((aDotB / aDotA));
			length = newVec.distToOrigo();
			height += length;

			minArea = Math.min(minArea, 2 * height + 2 * width);
		}
		return minArea;
	}

	public static void moveVector(Vector currentVec, double degToMove, int currPoint, int vecNumber) {
		while (degToMove > 0.000007) { // While still some degrees to move
			int nextPoint = (currPoint + 1) % polygon.size();
			double possibleMoveDegree = findAngleABC(currentVec, polygon.get(currPoint), polygon.get(nextPoint));
			if (possibleMoveDegree == 0) { // If nothing to move here, go on to next point
				currPoint = (currPoint + 1) % polygon.size();
				currentPointForVector.set(vecNumber, currPoint);
				continue;
			}

			if (possibleMoveDegree < degToMove + 0.000007 || possibleMoveDegree == degToMove) { // If parallell or need
																								// more

				double radDeg = (Math.PI * 2) - possibleMoveDegree;
				double newX = Math.cos(radDeg) * currentVec.getX() - Math.sin(radDeg) * currentVec.getY();
				double newY = Math.sin(radDeg) * currentVec.getX() + Math.cos(radDeg) * currentVec.getY();
				currentVec.setX(newX);
				currentVec.setY(newY);
				degToMove -= possibleMoveDegree;
				currPoint = (currPoint + 1) % polygon.size();
				currentPointForVector.set(vecNumber, currPoint);
			} else { // Same point, only rotate vector
				double radDeg = (Math.PI * 2) - degToMove;
				double newX = Math.cos(radDeg) * currentVec.getX() - Math.sin(radDeg) * currentVec.getY();
				double newY = Math.sin(radDeg) * currentVec.getX() + Math.cos(radDeg) * currentVec.getY();
				currentVec.setX(newX);
				currentVec.setY(newY);
				return;
			}
		}
	}

	private static double findAngleABC(Vector a, Vector b, Vector c) { // Find angle between A and BC.
		Vector bc = c.minus(b);
		double lengthAB = a.distToOrigo();
		double lengthBC = bc.distToOrigo();
		double dotProduct = a.dot(bc);

		return Math.acos(dotProduct / (lengthAB * lengthBC));
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

		polygon.remove(polygon.size() - 1);
		for (Vector e : stack) {
			polygon.add(e.invert());
		}
		polygon.remove(polygon.size() - 1);
	}

	public static void grahamScanAndrewUpper() { // Algorithm to find convex hull of points, O(nlog(n))
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
