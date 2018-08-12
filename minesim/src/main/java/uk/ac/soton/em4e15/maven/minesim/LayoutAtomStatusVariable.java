package uk.ac.soton.em4e15.maven.minesim;

import java.util.List;
import java.util.Random;

public class LayoutAtomStatusVariable {
	
	public enum LayoutAtomStatusVariableLevel {
		MIN, DANGER, MAX, EXTREME;
	}
	
	private double value;
	private double change = 0.0;
	private double normalMax;
	private double normalMin;
	private double flucRange;
	private double flucForce;
	private double dangerThresh;
	
	LayoutAtomStatusVariable(double startValue, double normalMax, double normalMin, double flucRange, double flucForce, double dangerThresh) {
		value = startValue;
		this.normalMax = normalMax;
		this.normalMin = normalMin;
		this.flucRange = flucRange;
		this.flucForce = flucForce;
		this.dangerThresh = dangerThresh;
	}
	
	public double getValue() {
		return value;
	}
	
	public boolean isAboveDangerThreshold() {
		return (value > dangerThresh);
	}
	
	public void update(List<Double> neighbourValues, Random rand) {
		
		// random fluctuation in the normal range
		double fluc = (2.0 * rand.nextDouble() - 1.0) * flucRange;
		change += fluc;
		value += change;
		
		// linear interpolation:
		// current atom has weight 4
		// surrounding atoms have weight 1
		value *= 4.0;
		for(Double v: neighbourValues)
			value += v;
		value /= (4.0 + (double) neighbourValues.size());
	}
	
	public void forceValueUp() {
		value += flucForce;
	}
	
	public void forceValueDown() {
		value -= flucForce;
	}
	
	public boolean isValueBelow(LayoutAtomStatusVariableLevel level) {
		switch(level) {
		case MIN:
			if(value < normalMin) return true;
			break;
		case DANGER:
			if(value < dangerThresh) return true;
			break;
		case MAX:
			if(value < normalMax) return true;
			break;
		case EXTREME:
			if(value < 2.0 * normalMax) return true;
			break;
		}
		return false;
	}
	
	public boolean isValueAbove(LayoutAtomStatusVariableLevel level) {
		switch(level) {
		case MIN:
			if(value > normalMin) return true;
			break;
		case DANGER:
			if(value > dangerThresh) return true;
			break;
		case MAX:
			if(value > normalMax) return true;
			break;
		case EXTREME:
			if(value > 2.0 * normalMax) return true;
			break;
		}
		return false;
	}
	
	public void forceValueInRange(LayoutAtomStatusVariableLevel levelMin, LayoutAtomStatusVariableLevel levelMax) {
		
		// check minimum
		switch(levelMin) {
		case MIN:
			if(value < normalMin) value = normalMin;
			break;
		case DANGER:
			if(value < dangerThresh) value = dangerThresh;
			break;
		case MAX:
			if(value < normalMax) value = normalMax;
			break;
		case EXTREME:
			if(value < 2.0 * normalMax) value = 2.0 * normalMax;
			break;
		}
		
		// check maximum
		switch(levelMax) {
		case MIN:
			if(value > normalMin) value = normalMin;
			break;
		case DANGER:
			if(value > dangerThresh) value = dangerThresh;
			break;
		case MAX:
			if(value > normalMax) value = normalMax;
			break;
		case EXTREME:
			if(value > 2.0 * normalMax) value = 2.0 * normalMax;
			break;
		}
	}
}
