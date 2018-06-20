package uk.ac.soton.em4e15.maven.minesim;

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
}
