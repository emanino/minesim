package uk.ac.soton.em4e15.maven.minesim;

import java.util.List;
import java.util.Random;

public class LayoutAtomStatusVariable implements Cloneable {
	
	public enum LayoutAtomStatusLevel {
		MIN, DANGER, HIGH, EXTREME;
	}
	
	private double value;
	private double valueMin;
	private double valueDanger;
	private double valueHigh;
	private double valueExtreme;
	private double fluctuation;
	private double increase;
	private double centerWeight;
	private double neighbourWeight;
	
	LayoutAtomStatusVariable(double valueStart, double valueMin, double valueDanger, double valueHigh,
			double valueExtreme, double fluctuation, double increase, double centerWeight, double neighbourWeight) {
		value = valueStart;
		this.valueMin = valueMin;
		this.valueDanger = valueDanger;
		this.valueHigh = valueHigh;
		this.valueExtreme = valueExtreme;
		this.fluctuation = fluctuation;
		this.increase = increase;
		this.centerWeight = centerWeight;
		this.neighbourWeight = neighbourWeight;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public double getValue() {
		return value;
	}
	
	public double getValueLevel(LayoutAtomStatusLevel level) {
		switch(level) {
		case MIN:
			return valueMin;
		case DANGER:
			return valueDanger;
		case HIGH:
			return valueHigh;
		case EXTREME:
			return valueExtreme;
		}
		return 0.0;
	}
	
	public boolean isAboveLevel(LayoutAtomStatusLevel level) {
		return value > getValueLevel(level);
	}
	
	public boolean isBelowLevel(LayoutAtomStatusLevel level) {
		return value < getValueLevel(level);
	}
	
	public void update(List<Double> neighbourValues, Random rand) {
		
		// Rademacher fluctuation (trimmed not to exceed the valueMin-valueDanger range)
		double change;
		if(rand.nextBoolean()) { // step in the negative direction
			change = valueMin - value;
			if(-fluctuation > change)
				change = -fluctuation;
		} else {				 // step in the positive direction
			change = valueDanger - value;
			if(fluctuation < change)
				change = fluctuation;
		}
		value += change;
		
		// linear interpolation with the neighbours
		value *= centerWeight;
		for(Double v: neighbourValues)
			value += v * neighbourWeight;
		value /= (centerWeight + neighbourValues.size() * neighbourWeight);
	}
	
	public void forceValueUpTowardsLevel(LayoutAtomStatusLevel level) {
		double target = getValueLevel(level);
		if(value >= target)
			return;
		double distanceToTarget = (target - value) / (target - valueMin);
		value += increase * distanceToTarget; // slow down as we come closer to the desired level
	}
}
