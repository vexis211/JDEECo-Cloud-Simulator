package cz.cuni.mff.d3s.jdeeco.cloudsimulator.client;

public abstract class Animal implements Movable {
	
	private Destination currentDestination;
	
	public Destination getCurrentDestination() {
		return currentDestination;
	}
	
	@Override
	public void move(Destination destination) {
		

		int[] asd = new int[] {1,2,3};
		
		this.currentDestination = destination;
	}
	
	public abstract void makeNoise();
}
