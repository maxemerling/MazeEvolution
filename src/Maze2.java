import java.util.ArrayList;

class Point {
	//package-private fields
	int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public static Point add(Point a, Point b) {
		return new Point(a.x + b.x, a.y + b.y);
	}
	
	@Override
	public Point clone() {
		return new Point(x, y);
	}
}

public class Maze2 {
	public boolean[][] board;
	private int currDir;
	private int side;
	public boolean justCCW, justCW; //for if it just turned one of those directions
	public ArrayList<Point> path;
	
	private static final int EAST = 0, NORTH = 1, WEST = 2, SOUTH = 3;
	
	private static final double PATH_LENGTH_FACTOR = 35;
	
	public Maze2(int side) {
		this.side = side;
		
		justCCW = false;
		justCW = false;
		
		board = new boolean[side][side];
		
		path = new ArrayList<Point>();
		
		//choose starting point (on top row, not on corner)
		//if side=8, can choose between y=0, 1<=x<=6, or (0<=x<=5)++
		path.add(new Point((int) (Math.random() * (side - 3)) + 1, 1));
		
		currDir = SOUTH;
		
		while (path.size() < side * PATH_LENGTH_FACTOR) {
			path.add(getNext(path.get(path.size() - 1)));
		}
		
		int yMaxIdx = side-1;
		for (int i = side-1; i >= 0; i--) {
			if (path.get(i).y > path.get(yMaxIdx).y) {
				yMaxIdx = i;
			}
		}
		
		//fill board
		for (Point p : path) {
			board[p.x][p.y] = true;
		}
	}
	
	private boolean inBounds(Point p) {
		return (
				p.x > 0 &&
				p.y > 0 &&
				p.x < side - 1 &&
				p.y < side - 1
				);
	}
	
	private Point getNext(Point p) {
		Point newPoint;
		int newDir;
		do {
			double rand = Math.random();
			
			if (!justCCW && rand < 0.25) { //counterclockwise turn
				newDir = (currDir + 1) % 4;
				justCCW = true;
				justCW = false;
			} else if (!justCW && rand < 0.5) { //clockwise turn
				newDir = (currDir - 1 + 4) % 4;
				justCW = true;
				justCCW = false;
			} else {
				newDir = currDir;
				justCW = false;
				justCCW = false;
			}
			
			Point shift;
			
			switch (newDir) {
			case EAST : shift = new Point(1, 0); break;
			case NORTH : shift = new Point(0, -1); break;
			case WEST : shift = new Point(-1, 0); break;
			case SOUTH : shift = new Point(0, 1); break;
			default : shift = null; //should never get to this point
			}
			
			newPoint = Point.add(p, shift);
		} while (!inBounds(newPoint));
		
		currDir = newDir;
		return newPoint;
	}
	
	/*
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Maze2 maze = new Maze2(50);
		JPanel[][] panelArr = new JPanel[maze.side][maze.side];
		for (int i = 0; i < maze.side; i++) {
			for (int j = 0; j < maze.side; j++) {
				panelArr[i][j] = new JPanel();
				if (maze.board[j][i]) {
					panelArr[i][j].setBackground(Color.WHITE);
				} else {
					panelArr[i][j].setBackground(Color.BLACK);
				}
			}
		}
		
		JPanel panel = new JPanel();
		panel.setSize(800, 800);
		panel.setLayout(new GridLayout(maze.side, maze.side));
		for (int i = 0; i < maze.side; i++) {
			for (int j = 0; j < maze.side; j++) {
				panel.add(panelArr[j][i]);
			}
		}
		
		frame.setContentPane(panel);
		frame.setSize(panel.getSize());
		frame.setResizable(false);
		frame.validate();
		frame.setVisible(true);
	}*/
}
