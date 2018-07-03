package uk.ac.soton.em4e15.maven.minesim;

public class FireExtinguishingSkill {
	
	private double radius = 1.0;
	private double power = 0.3;
	
	FireExtinguishingSkill() {}
	
	FireExtinguishingSkill(double radius, double power) {
		this.radius = radius;
		this.power = power;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getPower() {
		return power;
	}
}
