package uk.ac.soton.em4e15.maven.minesim;

public class FireStatus {
	
	private double strength = 0.2;
	private double increment = 0.1;
	
	FireStatus() {}
	
	FireStatus(double strength, double increment) {
		this.strength = strength;
		this.increment = increment;
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	public double increaseStrength() {
		strength += increment;
		if(strength > 1.0) strength = 1.0;
		return strength;
	}
}
