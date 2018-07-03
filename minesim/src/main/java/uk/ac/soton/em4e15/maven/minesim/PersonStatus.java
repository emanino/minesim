package uk.ac.soton.em4e15.maven.minesim;

public class PersonStatus {
	
	private double distance = 1.3;
	private double health = 1.0; // temporary
	
	PersonStatus() {}
	
	PersonStatus(double distance, double health) {
		this.distance = distance;
		this.health = health;
	}
	
	public double getDistance() {
		return distance * health; // people get slower when unhealthy
	}
	
	public double getHealth() {
		return health;
	}
	
	public void update(LayoutAtomStatus status) {
		this.CO2Damage(status.getCO2());
		this.TempDamage(status.getTemp());
	}
	
	public void recoverHealth() {
		health += 0.05;
		if(health > 1.0)
			health = 1.0;
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
