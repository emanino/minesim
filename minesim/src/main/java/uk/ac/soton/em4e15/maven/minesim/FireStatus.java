package uk.ac.soton.em4e15.maven.minesim;

import java.util.Properties;

public class FireStatus {
	
	private double strength = 0.2;
	private double change = 0.1;
	
	FireStatus(Properties prop) {
		this.strength = Double.parseDouble(prop.getProperty("fireStrength"));
		this.change = Double.parseDouble(prop.getProperty("fireStrengthIncrease"));
	}
	
	public double getStrength() {
		return strength;
	}
	
	public void setStrength(double strength) {
		this.strength = strength;
	}
	
	public double increaseStrength() {
		strength += change;
		if(strength > 1.0) strength = 1.0;
		return strength;
	}
}
