package uk.ac.soton.em4e15.maven.minesim;

public class TemperatureIncreaseStatus {

	boolean isBigIncrease;
	
	TemperatureIncreaseStatus(boolean isBigIncrease) {
		this.isBigIncrease = isBigIncrease;
	}
	
	public boolean isBigIncrease() {
		return isBigIncrease;
	}
}
