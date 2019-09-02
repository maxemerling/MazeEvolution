public class Brain {
	private Vector[] directions;
	private int step;
	private int size;
	
	public static final double MUTATION_RATE = 0.5;
	public static final double MAX_MUTATION = 180 * (Math.PI / 180);

	//public static final double HIGHER_MUTATION_RATE = .5;
	//public static final double HIGHER_MAX_MUTATION = 1 * (Math.PI / 180);
	
	
	public Brain(int size) {
		this.size = size;
		directions = new Vector[size];
		randomize();
		step = 0;
	}
	
	public void randomize() {
		for (int i = 0; i < directions.length; i++) {
			double randAngle = Math.random() * 2 * Math.PI;
			directions[i] = Vector.fromAngle(randAngle);
		}
	}
	
	public int size() {
		return directions.length;
	}
	
	public boolean hasNext() {
		return step < size();
	}
	
	public Vector getNext() {
		step++;
		return directions[step - 1];
	}
	
	public int getMoves() {
		return step;
	}
	
	@Override
	public Brain clone() {
		Brain clone = new Brain(directions.length);
		for (int i = 0; i < directions.length; i++) {
			clone.directions[i] = directions[i].clone();
		}
		return clone;
	}

	private static final int MUTABLE_STEPS = Dot.BRAIN_SIZE / 100;
	private static final double SAVED_MUTATION_RATE = MUTATION_RATE / 10;
	private static final double SAVED_MAX_MUTATION = 	MAX_MUTATION / 10;

	public void mutate() {
		/*
		//mutate the last *MUTABLE_STEPS* number of steps a lot, mutate the rest only a little

		int marker = (step > MUTABLE_STEPS) ? step - MUTABLE_STEPS : 0;
		
		//"saved" steps
		for (int i = 0; i < marker; i++) {
			double rand = Math.random();
			if (rand < SAVED_MUTATION_RATE) {
				double mutation = Math.random() * SAVED_MAX_MUTATION;
				if (rand < SAVED_MUTATION_RATE / 2) {
					mutation *= -1;
				}
				directions[i].shift(mutation);
			}
		}
		
		//last steps
		for (int i = marker; i < size; i++) {
			double rand = Math.random();
			if (rand < MUTATION_RATE) {
				double mutation = Math.random() * MAX_MUTATION;
				if (rand < MUTATION_RATE / 2) {
					mutation *= -1;
				}
				directions[i].shift(mutation);
			}
		}/*
		
		/*
		//STANDARD MUTATION ALGORITHM
		for (Vector acc : directions) {
			double rand = Math.random();
			if (rand < MUTATION_RATE) {
				double mutation = Math.random() * MAX_MUTATION;
				if (rand < MUTATION_RATE / 2) {
					mutation *= -1;
				}
				acc.shift(mutation);
			}
		}*/

		for (int i = 0; i < size; i++) {
			double rand = Math.random();
			if (Math.random() < i / (double) size * 0.5) {
				double mutation = Math.random() * Math.PI;
				if (rand < MUTATION_RATE / 2) {
					mutation *= -1;
				}
				directions[i].shift(mutation);
			}
		}

		/*
		for (Vector acc : directions) {
			double rand = Math.random();
			if (Math.random() < 0.9) {
				if (rand < MUTATION_RATE) {
					double mutation = Math.random() * MAX_MUTATION;
					if (rand < MUTATION_RATE / 2) {
						mutation *= -1;
					}
					acc.shift(mutation);
				}
			} else {
				if (rand < HIGHER_MUTATION_RATE) {
					double mutation = Math.random() * MAX_MUTATION;
					if (rand < HIGHER_MUTATION_RATE / 2) {
						mutation *= -1;
					}
					acc.shift(mutation);
				}
			}
		}
		*/
		
		/*
		DOESN'T WORK
		if (start > 0) {
			for (int i = 0; i < start; i++) {
				double rand = Math.random();
				if (rand < SAVED_MUTATION_RATE) {
					double mutation = Math.random() * SAVED_MAX_MUTATION;
					if (rand < SAVED_MUTATION_RATE / 2) {
						mutation *= -1;
					}
					directions[i].shift(mutation);
				}
			}
		}
		
		for (int i = start; i < size; i++) {
			double rand = Math.random();
			if (rand < MUTATION_RATE) {
				double mutation = Math.random() * MAX_MUTATION;
				if (rand < MUTATION_RATE / 2) {
					mutation *= 1;
				}
				directions[i].shift(mutation);
			}
		}
		
		
		for (int i = 0; i < size; i++) {
			double rand = Math.random();
			if (rand < maxMutation) {
				double mutation = Math.random() * maxMutation;
				if (rand < mutationRate / 2) {
					mutation *= -1;
				}
				directions[i].shift(mutation);
			}
		}*/
	}
	
	public void reset() {
		step = 0;
	}
}
