package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Mine {
	
	private long layoutSeed;
	private long updateSeed;
	private Random layoutRand;
	private Random updateRand;
	private MineState state;
	
	Mine(long layoutSeed, long updateSeed) {
		this.layoutSeed = layoutSeed;
		this.updateSeed = updateSeed;
		layoutRand = new Random(layoutSeed);
		updateRand = new Random(updateSeed);
		state = new MineState(0);
		
		createLayout();
		fillLayout();
	}

	// constructor from file
	// save minimal info on file
	// save on JSON for visualisation
	
	public long getLayoutSeed() {
		return layoutSeed;
	}
	
	public long getUpdateSeed() {
		return updateSeed;
	}
	
	public MineState getState() {
		return state;
	}
	
	public void update(Set<Action> actions) {
		MineState next = new MineState(state.getNextId()); // make sure the old ids are kept the same
		for(MineObject obj: state.getObjects())
			obj.update(actions, updateRand, next); // update all the elements
		state = next; // overwrite the old state with the new one
	}
	
	// this is just a draft
	private void createLayout() {
		
		// all of these parameters should go into a configuration file
		double granularity = 1.0;
		double radius = 1.1;
		int nAtoms = 10;
		int nLevels = 2;
		
		Position head = new Position(0.0, 0.0, 0.0); // convention: the exit is always the origin
		Position tail = new Position(10.0, 0.0, 0.0); // convention: the first tunnel always follows the positive x axis
		new Tunnel(state, head, tail, nAtoms, new LayoutAtomStatus(), radius);
		
		createBranch(tail, 0, nLevels, 3, nAtoms - 3, granularity, radius);
	}
	
	// this is just a draft
	private void fillLayout() {
		
		// all of these parameters should go into a configuration file
		int nPeople = 4;
		
		// find all the LayoutAtoms
		List<LayoutAtom> atoms = new ArrayList<LayoutAtom>();
		for(MineObject obj: state.getObjects())
			if(obj instanceof LayoutAtom)
				atoms.add((LayoutAtom) obj);
		
		// create the people in random locations
		for(int p = 0; p < nPeople; ++p) {
			int a = layoutRand.nextInt(atoms.size());
			new Person(atoms.get(a).getPosition(), state, new PersonStatus());
		}
	}
	
	// this is just a draft
	private void createBranch(Position head, int lastDir, int nLevels, int maxBranching, int nAtoms, double granularity, double radius) {
		
		// some mild argument checking
		if(lastDir < 0 || lastDir > 3)
			throw new IllegalArgumentException("There are only 4 directions for now");
		if(maxBranching < 1 || maxBranching > 3)
			throw new IllegalArgumentException("The branching factor is limited between 1 and 3 for now");
		if(nAtoms < 1)
			throw new IllegalArgumentException("Tunnels must contain at least one LayoutAtom");
		
		// end the recursion
		if(nLevels <= 0) return;
		
		// extract the branching factor
		int nBranch = layoutRand.nextInt(maxBranching - 1) + 1;
		
		// list all the possible directions
		List<Position> allDir = Arrays.asList(new Position(1, 0, 0),
										      new Position(-1, 0, 0),
										      new Position(0, 1, 0),
										      new Position(0, -1, 0));
		
		// extract the directions
		List<Integer> subDir = new ArrayList<Integer>();
		for(int b = 0; b < nBranch; ++b) {
			int d = layoutRand.nextInt(4);
			if(d != (lastDir ^ 1)) subDir.add(d);
			else --b;
		}
		
		// create the branches
		for(int b = 0; b < nBranch; ++b) {
			Position tail = head.plus(allDir.get(subDir.get(b)).times(granularity * (double) nAtoms));
			new Tunnel(state, head, tail, nAtoms, new LayoutAtomStatus(), radius);
			createBranch(tail, subDir.get(b), nLevels - 1, maxBranching, nAtoms - nLevels, granularity, radius);
		}
	}
}
