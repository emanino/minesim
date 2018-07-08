package uk.ac.soton.em4e15.maven.minesim;

import java.util.Properties;

public class PersonStatus {
	
	private double timeStep;
	private double personSpeed;
	private double RestTimeThreshold;
	private double currRestBar;
	
	private double health = 1.0; // temporary
	
	PersonStatus(Properties prop) {
		timeStep = Double.parseDouble(prop.getProperty("timeStep"));
		personSpeed = Double.parseDouble(prop.getProperty("personSpeed"));
		RestTimeThreshold = Double.parseDouble(prop.getProperty("personRestTime"));
		currRestBar = RestTimeThreshold;
	}
	
	public double getDistance() {
		return personSpeed * timeStep * health; // people get slower when unhealthy
	}
	
	public double getHealth() {
		return health;
	}
	
	public boolean isRested() {
		return currRestBar >= RestTimeThreshold;
	}
	
	public void restAndRecover() {
		health += 0.05;
		if(health > 1.0)
			health = 1.0;
		
		currRestBar += timeStep / 2; // cheeky hack: the rest bar recovers twice as slow as it gets depleted
	}
	
	public void workAndSuffer(LayoutAtomStatus status) {
		this.CO2Damage(status.getCO2());
		this.TempDamage(status.getTemp());
		
		currRestBar -= timeStep;
	}
	
	private void CO2Damage(double co2) {
		if(co2 > 0.02) // safe level
			health -= 0.1 * co2;
	}
	
	private void TempDamage(double temp) {
		if(temp < 8.0) {
			health -= 0.01 * (8.0 - temp); // freezing to death
		} else if(temp > 32.0) {
			health -= 0.01 * (temp - 32.0); // getting a heatstroke
		}
	}
}
