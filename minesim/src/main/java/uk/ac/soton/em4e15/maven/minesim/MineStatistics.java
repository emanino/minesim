package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineStatistics {
	
	private MineState state;
	private int timeStamp = 0;
	
	private double production = 0.0;
	private int nDeaths = 0;
	private MineStatisticsHealthTracker temp;
	private MineStatisticsHealthTracker co2; 
	private Map<Fire,Integer> activeFires;
	private List<Integer> sideFiresLength;
	private List<Integer> mainFiresLength; // includes secondary escape tunnel
	private List<Double> peopleHealth;
	
	MineStatistics(MineState state) {
		this.state = state;
		temp = new MineStatisticsHealthTracker();
		co2 = new MineStatisticsHealthTracker();
		activeFires = new HashMap<Fire,Integer>();
		sideFiresLength = new ArrayList<Integer>();
		mainFiresLength = new ArrayList<Integer>();
		peopleHealth = new ArrayList<Double>();
	}
	
	public void update(MineState next) {
		state = next;
		timeStamp++;
	}
	
	public double getProduction() {
		return production;
	}
	
	public int getNumberOfDeaths() {
		return nDeaths;
	}
	
	public MineStatisticsHealthTracker getTempRiskTracker() {
		return temp;
	}
	
	public MineStatisticsHealthTracker getCO2RiskTracker() {
		return co2;
	}
	
	public List<Integer> getSideFiresLength() {
		return sideFiresLength;
	}
	
	public List<Integer> getMainFiresLength() {
		return mainFiresLength;
	}
	
	public void recordProduction(MinerPerson miner, MiningSite site) {
		production += 1.0;
	}
	
	public void recordDeath(Person person) {
		nDeaths++;
	}
	
	public void recordTempRiskEvent(Person person, LayoutAtom atom) {
		temp.recordRiskEvent(person);
	}
	
	public void recordTempInjuryEvent(Person person, LayoutAtom atom) {
		temp.recordInjuryEvent(person);
	}
	
	public void recordCO2RiskEvent(Person person, LayoutAtom atom) {
		co2.recordRiskEvent(person);
	}
	
	public void recordCO2InjuryEvent(Person person, LayoutAtom atom) {
		co2.recordInjuryEvent(person);
	}
	
	public void recordFireStart(Fire fire) {
		activeFires.put(fire, timeStamp);
	}
	
	public void recordFireEnds(Fire fire) {
		if(!activeFires.containsKey(fire))
			throw new IllegalArgumentException("This fire did not start, or the event was not reported.");
		
		Integer start = activeFires.get(fire);
		int length = timeStamp - start;
		
		LayoutAtom atom = state.getClosestLayoutAtom(fire.getPosition());
		MineObject obj = state.getObject(atom.getSuperId());
		if(obj instanceof MainTunnel || obj instanceof EscapeTunnel)
			mainFiresLength.add(length);
		else if(obj instanceof Tunnel)
			sideFiresLength.add(length);
	}
	
	public void recordEndOfShift(Person person) {
		temp.recordEndOfShift(person);
		co2.recordEndOfShift(person);
		peopleHealth.add(person.getStatus().getHealth());
	}
}
