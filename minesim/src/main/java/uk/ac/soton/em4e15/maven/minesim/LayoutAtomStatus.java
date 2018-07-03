package uk.ac.soton.em4e15.maven.minesim;

import java.util.Set;

public class LayoutAtomStatus {
	
	private double temp = 20.0;
	private double co2 = 1.0;
	
	LayoutAtomStatus() {}
	
	LayoutAtomStatus(LayoutAtomStatus status) {
		temp = status.getTemp();
		co2 = status.getCO2();
	}
	
	LayoutAtomStatus(double temp, double co2) {
		this.temp = temp;
		this.co2 = co2;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public double getCO2() {
		return co2;
	}
	
	public void update(Fire fire) {
		double strength = fire.getStatus().getStrength();
		temp += 2.0 * strength;
		co2 += 0.02 * strength;
	}
	
	public void update(Set<LayoutAtomStatus> neighbours) {
		if(neighbours.contains(this))
			throw new IllegalArgumentException("You cannot update a LayoutAtomStatus with itself as a neighbour");
		
		// linear interpolation:
		// current atom has weight 4
		// surrounding atoms have weight 1
		temp *= 4;
		co2 *= 4;
		for(LayoutAtomStatus status: neighbours) {
			temp += status.getTemp();
			co2 += status.getCO2();
		}
		double n = neighbours.size() + 4.0;
		temp /= n;
		co2 /= n;
	}
}
