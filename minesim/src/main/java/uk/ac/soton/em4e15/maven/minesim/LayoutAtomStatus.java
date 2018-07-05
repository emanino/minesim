package uk.ac.soton.em4e15.maven.minesim;

import java.util.Set;

public class LayoutAtomStatus {
	
	private double temp = 20.0;
	private double co2 = 0.01;
	
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
		
		// large values saturate to max
		if(temp >= 200.0) temp = 200.0;
		if(co2 >= 1.0) co2 = 1.0;
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
	
	public void recover() {
		temp -= 0.5;
		co2 -= 0.005;
		
		// small values saturate to default
		if(temp <= 20.0) temp = 20.0;
		if(co2 <= 0.01) co2 = 0.01;
	}
}
