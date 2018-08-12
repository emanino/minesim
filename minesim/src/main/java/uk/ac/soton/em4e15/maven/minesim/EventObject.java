package uk.ac.soton.em4e15.maven.minesim;

public interface EventObject extends AtomObject {
	
	// negative values mean the event lasts forever
	public int remainingTimesteps();
}
