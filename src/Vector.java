
public class Vector {
	private double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void add(Vector v) {
		x += v.getX();
		y += v.getY();
	}
	
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	public double getLength() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * If the vector is longer than this length, normalize it, then multiply it by input length.
	 * @param length
	 */
	public void limit(double length) {
		double myLength = this.getLength();
		if (myLength > length) {
			x = x / myLength * length;
			y = y / myLength * length;
		}
	}
	
	/**
	 * Returns a unit vector along the direction of the input angle
	 * @param angle must be given in radians
	 */
	public static Vector fromAngle(double angle) {
		return new Vector(Math.cos(angle), Math.sin(angle));
	}
	
	public static double getDistance(Vector a, Vector b) {
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void shift(double angle) {
		angle += getAngle();
		x = Math.cos(angle);
		y = Math.sin(angle);
	}
	
	@Override
	public Vector clone() {
		return new Vector(x, y);
		
	}
}
