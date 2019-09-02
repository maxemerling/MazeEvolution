import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Program {
	
	public static final int NUM_DOTS = 1000;
	public static final int PANEL_SIZE = 700;
	public static final int MAZE_SIZE = 30;
	
	private Population p;
	private DotPanel panel;
	private JFrame frame;
	
	public Program() {
		setup();
		
	}
	
	private void setup() {
		panel = new DotPanel(PANEL_SIZE, MAZE_SIZE, NUM_DOTS);
		panel.setBackground(Color.WHITE);
		p = panel.getPopulation();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(panel.getSize().width + 15, panel.getSize().height+35);
		frame.getContentPane().add(panel);
		frame.setBackground(Color.WHITE);
		frame.validate();
		frame.setVisible(true);
	}
	
	private void run() throws InterruptedException {
		while(p.gen < 50) {
			while (!p.allDone()) {
				p.updateAll();
				panel.repaint();
				if (p.gen > 45) {
					Thread.sleep(3);
				}
			}
			p.naturalSelection();
		}
		
		//show best dot
		Dot dot = p.getBestDot();
		dot.reset();
		panel.setBestDot(dot);
		while(!(dot.isDead() && dot.hasReachedGoal())) {
			dot.update();
			panel.repaint();
			Thread.sleep(5);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Program main = new Program();
		main.run();
	}
}
