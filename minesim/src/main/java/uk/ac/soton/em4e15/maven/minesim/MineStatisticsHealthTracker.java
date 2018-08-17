package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Set;

public class MineStatisticsHealthTracker {
	
	private Set<Integer> riskTracker;
	private Set<Integer> injuryTracker;
	private double shiftRisk = 0.0;
	private double shiftInjury = 0.0;
	private double instantRisk = 0.0;
	private double instantInjury = 0.0;
	
	MineStatisticsHealthTracker() {
		riskTracker = new HashSet<Integer>();
		injuryTracker = new HashSet<Integer>();
	}
	
	public double getShiftRisk() {
		return shiftRisk;
	}
	
	public double getShiftInjury() {
		return shiftInjury;
	}
	
	public double getInstantRisk() {
		return instantRisk;
	}
	
	public double getInstantInjury() {
		return instantInjury;
	}
	
	public void recordRiskEvent(Person person) {
		riskTracker.add(person.getId());
		instantRisk += 1.0;
	}
	
	public void recordInjuryEvent(Person person) {
		injuryTracker.add(person.getId());
		instantInjury += 1.0;
	}
	
	public void recordEndOfShift(Person person) {
		if(riskTracker.contains(person.getId()))
			shiftRisk += 1.0;
		if(injuryTracker.contains(person.getId()))
			shiftInjury += 1.0;
	}
}
