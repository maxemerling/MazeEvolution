import java.awt.Graphics2D;

public class Population {
	private Dot[] dots;
	private DotPanel ourPanel;
	public int gen; //current generation number
	
	public Population(int numDots, DotPanel aPanel) {
		gen = 1;
		dots = new Dot[numDots];
		ourPanel = aPanel;
		
		for(int i = 0; i < numDots; i++) {
			dots[i] = new Dot(ourPanel);
		}
	}
	
	public void showAll(Graphics2D g2) {
		for (Dot dot : dots) {
			dot.show(g2);
		}
	}
	
	public void updateAll() {
		for (Dot dot : dots) {
			dot.update();
		}
	}
	
	public boolean allDone() {
		for (Dot dot : dots) {
			if (!dot.isDead() && !dot.hasReachedGoal()) { return false; }
		}
		return true;
	}
	
	public void naturalSelection() {
		Dot[] newDots = new Dot[dots.length];
		
		//make the first dot the best dot from the previous generation
		//this way the population doesn't get worse
		newDots[0] = getBestDot().clone();
		
		for (int i = 1; i < newDots.length; i++) {
			//select parent based on fitness
			Dot parent = selectParent();
			
			//get their offspring
			(newDots[i] = parent.clone())
					//and mutate it
					.mutate();
			
		}
		dots = newDots.clone();
		gen++;
	}
	
	
	private Dot selectParent() {
		//set up "number line" to choose dots
		//weight each dot based on its fitness
		double fitSum = 0;
		int bestIdx = getBestIndex();
		for (int i = 0; i < dots.length; i++) {
			if (i == bestIdx) {
				fitSum += dots[i].getFitness() * 5;
			} else {
				fitSum += dots[i].getFitness();
			}
		}
		
		//choose random location along number line
		double rand = Math.random() * fitSum;
		
		//get dot at that location
		double runningSum = 0;
		for (int i = 0; i < dots.length; i++) {
			if (i == bestIdx) {
				runningSum += dots[i].getFitness() * 5;
			} else {
				runningSum += dots[i].getFitness();
			}
			if (runningSum >= rand) {
				return dots[i];
			}
		}
	
		//will never get to this point
		System.out.println("oops");
		return null;
	}
	
	public int getBestIndex() {
		int bestIdx = 0;
		for (int i = 1; i < dots.length; i++) {
			if (dots[i].getFitness() > dots[bestIdx].getFitness()) {
				bestIdx = i;
			}
		}
		
		return bestIdx;
	}
	
	public Dot getBestDot() {
		return dots[getBestIndex()];
	}
}
