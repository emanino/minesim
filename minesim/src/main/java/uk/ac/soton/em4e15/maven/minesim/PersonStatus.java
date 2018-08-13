package uk.ac.soton.em4e15.maven.minesim;

import java.util.Properties;

public class PersonStatus {
	
	private double timeStep;
	private double personSpeed;
	private double RestTimeThreshold;
	private double currRestBar;
	private boolean instantHealth;
	
	private double health = 1.0;
	private double co2DamageRate;
	private double tempDamageRate;
	
	PersonStatus(Properties prop) {
		timeStep = Double.parseDouble(prop.getProperty("timeStep"));
		personSpeed = Double.parseDouble(prop.getProperty("personSpeed"));
		RestTimeThreshold = Double.parseDouble(prop.getProperty("personRestTime"));
		currRestBar = RestTimeThreshold;
		instantHealth = Integer.parseInt(prop.getProperty("personRest")) != 1;
		
		co2DamageRate = Double.parseDouble(prop.getProperty("co2DamageRate"));
		tempDamageRate = Double.parseDouble(prop.getProperty("tempDamageRate"));
	}
	
	public double getDistance() {
		return personSpeed * timeStep * health; // people get slower when unhealthy
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getRestBar() {
		return currRestBar;
	}
	
	public void setRestBar(double currRestBar) {
		this.currRestBar = currRestBar;
	}
	
	public boolean isRested() {
		return currRestBar >= RestTimeThreshold;
	}
	
	public void restAndRecover() {
		if(instantHealth){
			health = 1.0;
			currRestBar = RestTimeThreshold;
			
		} else {				
			health += 0.05;
			if(health > 1.0)
				health = 1.0;
			currRestBar += timeStep;
			if(currRestBar > RestTimeThreshold)
				currRestBar = RestTimeThreshold;
		}
	}
	
	public void workAndTire(LayoutAtomStatus status) {
		
		// health damage
		this.CO2Damage(status.getVariableCO2());
		this.TempDamage(status.getVariableTemp());
		
		// fatigue
		currRestBar -= timeStep;
	}
	
	private void CO2Damage(LayoutAtomStatusVariable co2) {
		if(co2.isAboveDangerThreshold())
			health -= co2DamageRate * co2.getValue(); // CO2 poisoning
	}
	
	private void TempDamage(LayoutAtomStatusVariable temp) {
		if(temp.isAboveDangerThreshold())
			health -= tempDamageRate * temp.getValue(); // heat stroke
	}
}
