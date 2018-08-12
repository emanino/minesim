package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.SortedSet;
import uk.ac.soton.em4e15.maven.minesim.LayoutAtomStatusVariable.LayoutAtomStatusVariableLevel;

public class LayoutAtomStatus {
	
	private LayoutAtomStatusVariable temp;
	private LayoutAtomStatusVariable co2;
	private Integer parentId;
	
	LayoutAtomStatus(LayoutAtomStatus status, Integer sensorId) {
		temp = status.getVariableTemp();
		co2 = status.getVariableCO2();
		this.setSensorId(sensorId);
	}
	
	LayoutAtomStatus(Properties prop) {
		this(prop, -1);
	}
	
	LayoutAtomStatus(Properties prop, Integer sensorId) {
		
		double tempStartValue = Double.parseDouble(prop.getProperty("tempStartValue"));
		double tempNormalMax = Double.parseDouble(prop.getProperty("tempNormalMax"));
		double tempNormalMin = Double.parseDouble(prop.getProperty("tempNormalMin"));
		double tempFlucRange = Double.parseDouble(prop.getProperty("tempFlucRange"));
		double tempFlucForce = Double.parseDouble(prop.getProperty("tempFlucForce"));
		double tempDanger = Double.parseDouble(prop.getProperty("tempDanger"));
		
		double co2StartValue = Double.parseDouble(prop.getProperty("co2StartValue"));
		double co2NormalMax = Double.parseDouble(prop.getProperty("co2NormalMax"));
		double co2NormalMin = Double.parseDouble(prop.getProperty("co2NormalMin"));
		double co2FlucRange = Double.parseDouble(prop.getProperty("co2FlucRange"));
		double co2FlucForce = Double.parseDouble(prop.getProperty("co2FlucForce"));
		double co2Danger = Double.parseDouble(prop.getProperty("co2Danger"));
		
		temp = new LayoutAtomStatusVariable(tempStartValue, tempNormalMax, tempNormalMin, tempFlucRange, tempFlucForce, tempDanger);
		co2 = new LayoutAtomStatusVariable(co2StartValue, co2NormalMax, co2NormalMin, co2FlucRange, co2FlucForce, co2Danger);
		
		this.setSensorId(sensorId);
	}
	
	public LayoutAtomStatusVariable getVariableTemp() {
		return temp;
	}
	
	public LayoutAtomStatusVariable getVariableCO2() {
		return co2;
	}
	
	public double getTemp() {
		return temp.getValue();
	}
	
	public double getCO2() {
		return co2.getValue();
	}
	
	// use this when everything is fine
	public void normalUpdate(SortedSet<LayoutAtomStatus> neighbours, Random rand) {
		if(neighbours.contains(this))
			throw new IllegalArgumentException("You cannot update a LayoutAtomStatus with itself as a neighbour");
		
		ArrayList<Double> neighbourTemp = new ArrayList<Double>();
		ArrayList<Double> neighbourCO2 = new ArrayList<Double>();
		for(LayoutAtomStatus status: neighbours) {
			neighbourTemp.add(status.getTemp());
			neighbourCO2.add(status.getCO2());
		}
		
		temp.update(neighbourTemp, rand);
		co2.update(neighbourCO2, rand);
		
		temp.forceValueInRange(LayoutAtomStatusVariableLevel.MIN, LayoutAtomStatusVariableLevel.MAX);
		co2.forceValueInRange(LayoutAtomStatusVariableLevel.MIN, LayoutAtomStatusVariableLevel.MAX);
	}
	
	// use this when fires start, gases leak, or similar tragedies happen 
	public void eventUpdate(SortedSet<EventObject> nearbyEvents, Random rand) {
		if(nearbyEvents.isEmpty())
			throw new IllegalArgumentException("No nearby events. You should have used normalUpdate()");
		
		// normal update
		ArrayList<Double> none = new ArrayList<Double>();
		temp.update(none, rand);
		co2.update(none, rand);
		
		// check fires
		for(EventObject e: nearbyEvents)
			if(e instanceof Fire) {
				FireStatus status = ((Fire) e).getStatus(); // stronger fires have a larger PROBABILITY of increasing the values
				if(temp.isValueBelow(LayoutAtomStatusVariableLevel.MAX) && rand.nextDouble() < status.getStrength())
					temp.forceValueUp();
				if(temp.isValueAbove(LayoutAtomStatusVariableLevel.EXTREME))
					temp.forceValueDown();
				if(co2.isValueBelow(LayoutAtomStatusVariableLevel.MAX) && rand.nextDouble() < status.getStrength())
					co2.forceValueUp();
				if(co2.isValueAbove(LayoutAtomStatusVariableLevel.EXTREME))
					co2.forceValueDown();
			}
		
		// check gas leaks
		for(EventObject e: nearbyEvents)
			if(e instanceof GasLeak)
				
				// BIG gas leak
				if(((GasLeak) e).getStatus().isBigLeak()) {
					if(co2.isValueBelow(LayoutAtomStatusVariableLevel.MAX))
						co2.forceValueUp();
					if(co2.isValueAbove(LayoutAtomStatusVariableLevel.EXTREME))
						co2.forceValueDown();
				
				// small gas leak
				} else {
					if(co2.isValueBelow(LayoutAtomStatusVariableLevel.DANGER))
						co2.forceValueUp();
					if(co2.isValueAbove(LayoutAtomStatusVariableLevel.MAX))
						co2.forceValueDown();
				}
		
		// check temperature increases
		for(EventObject e: nearbyEvents)
			if(e instanceof TemperatureIncrease)
				
				// BIG temperature increase
				if(((TemperatureIncrease) e).getStatus().isBigIncrease()) {
					if(temp.isValueBelow(LayoutAtomStatusVariableLevel.MAX))
						temp.forceValueUp();
					if(temp.isValueAbove(LayoutAtomStatusVariableLevel.EXTREME))
						temp.forceValueDown();
				
				// small temperature increase
				} else {
					if(temp.isValueBelow(LayoutAtomStatusVariableLevel.DANGER))
						temp.forceValueUp();
					if(temp.isValueAbove(LayoutAtomStatusVariableLevel.MAX))
						temp.forceValueDown();
				}
	}

	public Integer getSensorId() {
		return parentId;
	}

	public void setSensorId(Integer sensorId) {
		this.parentId = sensorId;
	}
}
