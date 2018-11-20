package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.SortedSet;
import uk.ac.soton.em4e15.maven.minesim.LayoutAtomStatusVariable.LayoutAtomStatusLevel;

public class LayoutAtomStatus {
	
	private LayoutAtomStatusVariable temp;
	private LayoutAtomStatusVariable co2;
	private Integer atomId;
	
	LayoutAtomStatus(Properties prop, Integer atomId) {
		
		double timeStep = Double.parseDouble(prop.getProperty("timeStep"));
		
		double tempStart = Double.parseDouble(prop.getProperty("tempStart"));
		double tempMin = Double.parseDouble(prop.getProperty("tempMin"));
		double tempDanger = Double.parseDouble(prop.getProperty("tempDanger"));
		double tempHigh = Double.parseDouble(prop.getProperty("tempHigh"));
		double tempExtreme = Double.parseDouble(prop.getProperty("tempExtreme"));
		double tempFluc = Double.parseDouble(prop.getProperty("tempFluc")) * timeStep; // depends on the length of the time step
		double tempInc = Double.parseDouble(prop.getProperty("tempInc")) * timeStep; // depends on the length of the time step
		double tempCenterW = Double.parseDouble(prop.getProperty("tempCenterW"));
		double tempNeighW = Double.parseDouble(prop.getProperty("tempNeighW"));

		double co2Start = Double.parseDouble(prop.getProperty("co2Start"));
		double co2Min = Double.parseDouble(prop.getProperty("co2Min"));
		double co2Danger = Double.parseDouble(prop.getProperty("co2Danger"));
		double co2High = Double.parseDouble(prop.getProperty("co2High"));
		double co2Extreme = Double.parseDouble(prop.getProperty("co2Extreme"));
		double co2Fluc = Double.parseDouble(prop.getProperty("co2Fluc")) * timeStep; // depends on the length of the time step
		double co2Inc = Double.parseDouble(prop.getProperty("co2Inc")) * timeStep; // depends on the length of the time step
		double co2CenterW = Double.parseDouble(prop.getProperty("co2CenterW"));
		double co2NeighW = Double.parseDouble(prop.getProperty("co2NeighW"));
		
		temp = new LayoutAtomStatusVariable(tempStart, tempMin, tempDanger, tempHigh, tempExtreme, tempFluc, tempInc, tempCenterW, tempNeighW);
		co2 = new LayoutAtomStatusVariable(co2Start, co2Min, co2Danger, co2High, co2Extreme, co2Fluc, co2Inc, co2CenterW, co2NeighW);
		this.atomId = atomId;
	}
	
	LayoutAtomStatus(LayoutAtomStatus status) {
		try {
			temp = (LayoutAtomStatusVariable) status.getVariableTemp().clone();
			co2 = (LayoutAtomStatusVariable) status.getVariableCO2().clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalArgumentException("For some reason I cannot clone any LayoutAtomStatusVariable");
		}
		atomId = status.getAtomId();
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
	
	public double getCO() {
		return co2.getValue();
	}
	
	public Integer getAtomId() {
		return atomId;
	}

	public void setAtomId(Integer atomId) {
		this.atomId = atomId;
	}
	
	public void update(SortedSet<LayoutAtomStatus> neighbours, SortedSet<EventObject> nearbyEvents, Random rand) {
		if(neighbours.contains(this))
			throw new IllegalArgumentException("You cannot update a LayoutAtomStatus with itself as a neighbour");
		
		// extract the neighbours' temperature and co2
		ArrayList<Double> neighbourTemp = new ArrayList<Double>();
		ArrayList<Double> neighbourCO2 = new ArrayList<Double>();
		for(LayoutAtomStatus status: neighbours) {
			neighbourTemp.add(status.getTemp());
			neighbourCO2.add(status.getCO());
		}
		
		// standard update
		temp.update(neighbourTemp, rand);
		co2.update(neighbourCO2, rand);
		
		// A fire/gas leak/temperature increase is happening
		for(EventObject e: nearbyEvents)
			if(e instanceof Fire) {
				temp.forceValueUpTowardsLevel(LayoutAtomStatusLevel.EXTREME); // ignore the strength of the fire for now
				co2.forceValueUpTowardsLevel(LayoutAtomStatusLevel.EXTREME); // ignore the strength of the fire for now
			} else if(e instanceof GasLeak) {
				if(((GasLeak) e).getStatus().isBigLeak())
					co2.forceValueUpTowardsLevel(LayoutAtomStatusLevel.EXTREME); // big gas leak
				else
					co2.forceValueUpTowardsLevel(LayoutAtomStatusLevel.HIGH); // small gas leak
			} else if(e instanceof TemperatureIncrease) {
				if(((TemperatureIncrease) e).getStatus().isBigIncrease())
					temp.forceValueUpTowardsLevel(LayoutAtomStatusLevel.EXTREME); // big temperature increase
				else
					temp.forceValueUpTowardsLevel(LayoutAtomStatusLevel.HIGH); // small temperature increase
			}
	}
}
