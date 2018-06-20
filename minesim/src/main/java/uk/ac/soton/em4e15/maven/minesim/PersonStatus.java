package uk.ac.soton.em4e15.maven.minesim;

public class PersonStatus {
	
	private double speed = 1.3;
	
	PersonStatus() {}
	
	PersonStatus(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
}
