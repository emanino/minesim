package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Set;

public class MineStatisticsHealthTracker {
	
	private Set<Person> riskTracker;
	private Set<Person> injuryTracker;
	private double shiftRisk = 0.0;
	private double shiftInjury = 0.0;
	private double instantRisk = 0.0;
	private double instantInjury = 0.0;
	
	MineStatisticsHealthTracker() {
		riskTracker = new HashSet<Person>();
		injuryTracker = new HashSet<Person>();
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
		riskTracker.add(person);
		instantRisk += 1.0;
	}
	
	public void recordInjuryEvent(Person person) {
		injuryTracker.add(person);
		instantInjury += 1.0;
	}
	
	public void recordEndOfShift(Person person) {
		if(riskTracker.contains(person))
			shiftRisk += 1.0;
		if(injuryTracker.contains(person))
			shiftInjury += 1.0;
	}
}
