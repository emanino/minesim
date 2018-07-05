package uk.ac.soton.em4e15.maven.minesim;

import java.util.List;

public class Path {
	
	private List<Integer> atoms = null;
	private double cost = Double.MAX_VALUE; // max possible cost
	
	Path() {}
	
	Path(List<Integer> atoms, double cost) {
		this.atoms = atoms;
		this.cost = cost;
	}
	
	public List<Integer> getAtomIds() {
		return atoms;
	}
	
	public void appendAtomId(Integer atomId) {
		if(atoms != null)
			atoms.add(atomId);
	}
	
	public void prependAtomId(Integer atomId) {
		if(atoms != null)
			atoms.add(0, atomId);
	}
	
	public double getCost() {
		return cost;
	}
	
	public void increaseCost(double value) {
		if(cost < Double.MAX_VALUE - value)
			cost += value;
	}
	
	public boolean betterThan(Path path) {
		return (atoms != null && cost < path.getCost())? true: false; // prefer valid paths with smaller cost
	}
	
	public int getLength() {
		return atoms.size();
	}
}
