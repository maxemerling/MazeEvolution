
public class Square extends java.awt.geom.Rectangle2D.Double {
	private Vector centerPos;
	private double side;
	
	public Square(Vector centerPos, double side) {
		super(centerPos.getX() - side/2, centerPos.getY() - side/2, side, side);
		this.centerPos = centerPos;
		this.side = side;
	}
	
	/** (x, y) is pos of top left corner */
	public Square(double x, double y, double side) {
		this (new Vector(x + side / 2, y + side / 2), side);
	}
	
	/**
	 * Stretches topLeft by the side amount
	 */
	public Square(Point topLeft, double side) {
		this(topLeft.x * side, topLeft.y * side, side);
	}
	
	public Vector getCenterPos() {
		return centerPos;
	}
	
	public double getSide() {
		return side;
	}
}
