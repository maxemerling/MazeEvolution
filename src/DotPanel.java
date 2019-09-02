import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DotPanel extends JPanel {
	private Population pop;
	public Square start, goal;
	
	private Maze2 maze;
	private ArrayList<Square> walls;
	
	private Dot bestDot = null; //to show at end of program
	private ArrayList<Square> bestDotPath = new ArrayList<Square>(); 
	
	public DotPanel(int panelSize, int mazeSize, int numDots) {
		this.setSize(new Dimension(panelSize, panelSize));
		this.setBackground(Color.WHITE);
		
		Maze2 maze = new Maze2(mazeSize);
		walls = new ArrayList<Square>();
		
		int cellSize = panelSize / mazeSize;
		
		for (int i = 0; i < mazeSize; i++) {
			for (int j = 0; j < mazeSize; j++) {
				if (!maze.board[i][j]) {
					walls.add(new Square(i * cellSize, j * cellSize, cellSize));
				}
			}
		}
		
		final double MIN_DIST = 0.75 * panelSize;
		
		//get start and end cells
		do {
			start = new Square(maze.path.get((int) (Math.random() * maze.path.size())), cellSize);
			goal = new Square(maze.path.get((int) (Math.random() * maze.path.size())), cellSize);
		} while (Vector.getDistance(start.getCenterPos(), goal.getCenterPos()) 
				< MIN_DIST
				);
		
		
		pop = new Population(numDots, this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.BLACK);
		for (Square r : walls) {
			g2.fill(r);
		}
		
		g2.setColor(Color.CYAN);
		g2.fill(start);
		
		g2.setColor(Color.GREEN);
		g2.fill(goal);
		
		g2.setColor(Color.RED);
		if (bestDot == null) {
			pop.showAll(g2);
		} else {
			bestDotPath.add(bestDot.getSquare());
			for (Square s : bestDotPath) {
				g2.fill(s);
			}
		}
	}
	
	public Population getPopulation() {
		return pop;
	}
	
	public Square getGoal() {
		return goal;
	}
	
	public void setBestDot(Dot dot) {
		bestDot = dot;
	}
	
	public ArrayList<Square> getWalls() {
		return walls;
	}
}
