package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LayoutAtom implements AtomObject {
	
	private Integer id;
	private Integer superId;
	private Position pos;
	private MineState state;
	private LayoutAtomStatus status;
	private Set<Integer> neighbours;
	
	// create a new LayoutAtom
	LayoutAtom(Integer superId, Position pos, MineState state, LayoutAtomStatus status, double radius) {
		id = state.getNextId();
		this.superId = superId;
		this.pos = pos;
		this.state = state;
		this.status = status;
		neighbours = new HashSet<Integer>();
		state.addNew(this);
		
		// find the neighbours automatically
		for(AtomObject obj: state.getObjectsInRadius(pos, radius))
			if(obj instanceof LayoutAtom && obj.getId() != id)
				this.linkWith(obj.getId());
	}
	
	// copy the LayoutAtom into the next state
	LayoutAtom(LayoutAtom atom, MineState next) {
		id = atom.getId();
		superId = atom.getSuperId();
		pos = atom.getPosition();
		state = next;
		status = atom.getStatus();
		neighbours = atom.getNeighbours();
		state.addOld(this);
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	public Integer getSuperId() {
		return superId;
	}

	@Override
	public Position getPosition() {
		return pos;
	}
	
	public LayoutAtomStatus getStatus() {
		return status;
	}
	
	public Set<Integer> getNeighbours() {
		return neighbours;
	}
	
	public void addNeighbour(Integer atomId) {
		if(atomId == id)
			throw new IllegalArgumentException("A LayoutAtom cannot have itself as a neighbour");
		if(!(state.getObject(atomId) instanceof LayoutAtom))
			throw new IllegalArgumentException("The neighbours of a LayoutAtom must be LayoutAtoms");
		neighbours.add(atomId);
	}
	
	public void linkWith(Integer atomId) {
		this.addNeighbour(atomId);
		((LayoutAtom) state.getObject(atomId)).addNeighbour(id);
	}

	@Override
	public void update(Set<Action> actions, Random rand, MineState next) {
		LayoutAtom atom = new LayoutAtom(this, next);
		// update atom...
		// - status (given occupants)
	}
	
	public List<Integer> shortestPathTo(Integer target, Set<Integer> block) {
		
		if(target == id)
			return new ArrayList<Integer>(Arrays.asList(id));
		
		List<Integer> subpath = null;
		int length = Integer.MAX_VALUE;
		block.add(id);
		
		for(Integer atomId: neighbours)
			if(!block.contains(atomId)) {
				List<Integer> candidate = ((LayoutAtom) state.getObject(atomId)).shortestPathTo(target, block);
				if(candidate != null && candidate.size() < length) {
					length = candidate.size();
					subpath = candidate;
				}
			}
		
		if(subpath != null)
			subpath.add(id); // the final path will be in reverse order
		block.remove(id);
		
		return subpath;
	}
}
