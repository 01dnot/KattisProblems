public class Vector implements Comparable<Vector> {
	private double x;
	private double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	public Vector times(double c) {
		return new Vector(c * this.x, c * this.y);
	}

	public Vector minus(Vector other) {
		return new Vector(this.x - other.x, this.y - other.y);
	}
	
	public Vector plus(Vector other) {
		return new Vector(this.x + other.x, this.y + other.y);
	}

	public double dot(Vector other) {
		return this.x * other.x + this.y * other.y;
	}
	
	public double distToOrigo() {
		return Math.sqrt(this.dot(this));
	}

	public Vector rotateLeft() {
		return new Vector(-y, x);
	}

	public boolean isRightOf(Vector other) {
		return other.dot(this.rotateLeft()) > 0;
	}
	public boolean isLeftOf(Vector other) {
		return other.dot(this.rotateLeft()) < 0;
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int compareTo(Vector o) {
		return Double.compare(x*x + y*y, o.x*o.x+o.y+o.y);
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	public Vector pointAt(double d, Vector nextPoint) {
		Vector AB = nextPoint.minus(this);
		return (AB.times(d)).plus(this);
	}

}
