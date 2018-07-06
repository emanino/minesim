package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class LayoutAtom implements AtomObject {
	
	private Integer id;
	private Integer superId;
	private Position pos;
	private MineState state;
	private LayoutAtomStatus status;
	private Set<Integer> neighbours;
	private double radius;
	
	// create a new LayoutAtom
	LayoutAtom(Integer superId, Position pos, MineState state, LayoutAtomStatus status, double radius) {
		id = state.getNextId();
		this.superId = superId;
		this.pos = pos;
		this.state = state;
		this.status = status;
		neighbours = new HashSet<Integer>();
		this.radius = radius;
		state.addNew(this);
		
		// find the neighbours automatically
		for(LayoutAtom atom: state.getObjectsInRadius(LayoutAtom.class, pos, radius))
			if(atom.getId() != id)
				this.linkWith(atom);
	}
	
	// copy the LayoutAtom into the next state
	LayoutAtom(LayoutAtom atom, MineState next) {
		id = atom.getId();
		superId = atom.getSuperId();
		pos = atom.getPosition();
		state = next;
		status = atom.getStatus();
		neighbours = atom.getNeighbours();
		radius = atom.getRadius();
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
	
	public void addNeighbour(LayoutAtom atom) {
		if(atom == this)
			throw new IllegalArgumentException("A LayoutAtom cannot have itself as a neighbour");
		neighbours.add(atom.getId());
	}
	
	public void removeNeighbour(Integer atomId) {
		neighbours.remove(atomId);
	}
	
	public void linkWith(LayoutAtom atom) {
		this.addNeighbour(atom);
		atom.addNeighbour(this);
	}
	
	public double getRadius() {
		return radius;
	}

	@Override
	public void update(Set<MicroAction> actions, Random rand, MineState next) {
		LayoutAtom atom = new LayoutAtom(this, next);
		
		// compute the damage caused by Fires
		for(Fire fire: state.getObjectsInRadius(Fire.class, pos, radius))
			atom.getStatus().update(fire);
		
		// compute the effect of neighbouring LayoutAtoms
		Set<LayoutAtomStatus> statuses = new HashSet<LayoutAtomStatus>();
		for(Integer atomId: neighbours)
			statuses.add(state.getObject(LayoutAtom.class, atomId).getStatus());
		atom.getStatus().update(statuses);
		
		// the status slowly recovers on its own
		atom.getStatus().recover();
	}
	
	public Path shortestPathTo(Set<Integer> targets, Set<Integer> block) {
		
		if(targets.contains(id))
			return new Path(new LinkedList<Integer>(Arrays.asList(id)), 0.0);
		
		Path subpath = new Path();
		block.add(id);
		
		for(Integer atomId: neighbours)
			if(!block.contains(atomId)) {
				Path candidate = state.getObject(LayoutAtom.class, atomId).shortestPathTo(targets, block);
				if(candidate.betterThan(subpath))
					subpath = candidate;
			}
		
		subpath.prependAtomId(id);
		subpath.increaseCost(1.0);
		block.remove(id);
		
		return subpath;
	}
	
	public Path shortestPathOut(Set<Integer> evacuate) {
		
		Set<Integer> targets = new HashSet<Integer>();
		for(LayoutAtom atom: state.getObjects(LayoutAtom.class))
			if(!evacuate.contains(atom.getId()))
				targets.add(atom.getId());
		
		return this.shortestPathTo(targets, new HashSet<Integer>());
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"atom\",\"name\":\"A"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
	}
}
