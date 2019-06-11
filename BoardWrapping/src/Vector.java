public class Vector implements Comparable<Vector> {
	private double x;
	private double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public boolean isLeftOf(Vector other) {
		return other.dot(this.rotateLeft()) <= 0;
	}

	public Vector rotateLeft() {
		return new Vector(-y, x);
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

	public Vector invert() {
		return new Vector(this.x, -this.y);
	}
	public double dot(Vector other) {
		return this.x * other.x + this.y * other.y;
	}

	public double distToOrigo() {
		return Math.sqrt(this.dot(this));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}

	public Vector pointAt(double d, Vector nextPoint) {
		Vector AB = nextPoint.minus(this);
		return (AB.times(d)).plus(this);
	}

	@Override
	public int compareTo(Vector o) {
		if (this.x < o.x) {
			return -1;
		}
		if (this.x > o.x) {
			return 1;
		}
		if (this.y < o.y) {
			return 1;
		}
		if (this.y > o.y) {
			return -1;
		}
		return 0;
	}

}
