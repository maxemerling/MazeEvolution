import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Dot {
	private Vector r, v, a; //r is pos, v is velocoty, a is acceleration
	private DotPanel myPanel;
	public Brain brain;
	private Square goal;
	private Color myColor;
	private ArrayList<Square> walls;
	
	private boolean dead, reachedGoal;
	
	public static final double SIDE = 4;
	private static final double MAX_VELOCITY = 5;
	public static final int BRAIN_SIZE = 1000;
	private static final double GOAL_SCORE = 1;
	
	static int maxStep = BRAIN_SIZE;
	
	//initialize to middle of screen
	public Dot(DotPanel hostPanel) {
		myPanel = hostPanel;
		goal = myPanel.getGoal();
		myColor = Color.CYAN;
		
		walls = myPanel.getWalls();
		
		dead = false;
		reachedGoal = false;
		
		r = myPanel.start.getCenterPos().clone();
		v = new Vector(0, 0);
		a = new Vector(0, 0);
		
		brain = new Brain(BRAIN_SIZE);
	}
	
	public void show(Graphics2D g2) {
		g2.setColor(myColor);
		g2.fill(getSquare());
	}
	
	public Square getSquare() {
		return new Square(r, SIDE);
	}
	
	private void move() {
		if (brain.hasNext()) {
			a = brain.getNext();
		} else {
			dead = true;
			//kill the dot when it runs out of directions to turn
		}
		
		v.add(a);
		v.limit(MAX_VELOCITY);
		r.add(v);
	}
	
	/** If dot isn't dead, then move */
	public void update() {
		if (!dead && !reachedGoal) {
			move();
			if (hasHitWall() || isOutOfBounds()) {
				dead = true;
			} else if (hasReachedGoal()) {
				reachedGoal = true;
				if (brain.getMoves() > maxStep) {
					dead = true;
				}
			}
		}
	}
	
	public boolean hasReachedGoal() {
		return goal.intersects(getSquare());
	}
	
	private boolean hasHitWall() {
		for (Square
				s : walls) {
			if (s.intersects(getSquare())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isOutOfBounds() {
		return (!getSquare().intersects(myPanel.getBounds()));
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public double getFitness() {
		if (hasReachedGoal()) {
			return 10 * Math.pow(
					((double) BRAIN_SIZE) / brain.getMoves(),
					2) + 10;
		} else {
			return 1d / Vector.getDistance(r, goal.getCenterPos());
		}
	}
	
	public Vector getPos() {
		return r;
	}
	
	@Override
	public Dot clone() {
		Dot clone = new Dot(myPanel);
		clone.goal = this.goal;
		clone.brain = this.brain.clone();
		return clone;
	}
	
	public void mutate() {
		brain.mutate();
	}
	
	public void reset() {
		brain.reset();
	}
}
