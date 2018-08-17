package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.jena.graph.Triple;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class LayoutAtom implements AtomObject {
	
	private Integer id;
	private Integer superId;
	private Position pos;
	private MineState state;
	private LayoutAtomStatus status;
	private SortedSet<Integer> neighbours;
	private double radius;
	
	private boolean allowVehicles;
	
	// create a new LayoutAtom
	LayoutAtom(Integer superId, Position pos, MineState state, LayoutAtomStatus status, double radius, boolean allowVehicles) {
		id = state.getNextId();
		this.superId = superId;
		this.pos = pos;
		this.state = state;
		
		status.setAtomId(id);
		this.status = new LayoutAtomStatus(status);
		
		neighbours = new TreeSet<Integer>();
		this.radius = radius;
		this.allowVehicles = allowVehicles;
		state.addNew(this);
	}
	
	// find the neighbours automatically
	public void initialiseLinks() {
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
		
		status = new LayoutAtomStatus(atom.getStatus());
		
		neighbours = atom.getNeighbours();
		radius = atom.getRadius();
		allowVehicles = atom.areVehiclesAllowed();
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
	
	public SortedSet<Integer> getNeighbours() {
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
	
	public boolean areVehiclesAllowed() {
		return allowVehicles;
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		LayoutAtom atom = new LayoutAtom(this, next);
		
		// find the neighbouring statuses and the nearby fires/gas leaks/temperature increases
		SortedSet<LayoutAtomStatus> statuses = new TreeSet<LayoutAtomStatus>(Comparator.comparing(LayoutAtomStatus::getAtomId));
		for(Integer atomId: neighbours)
			statuses.add(state.getObject(LayoutAtom.class, atomId).getStatus());
		SortedSet<EventObject> events = state.getObjectsInRadius(EventObject.class, pos, radius);
		
		// update the status
		atom.getStatus().update(statuses, events, rand);
		
		// to do: add ventilation, et cetera
	}
	
	public Path shortestPathTo(Set<Integer> targets, Set<Integer> block) {
		return shortestPathToDijkstra(targets, block);
	}
	
	//Dijkstra version of the shortest path
	public Path shortestPathToDijkstra(Set<Integer> targets, Set<Integer> block) {
		Map<Integer, Double> tentativeDistances = new HashMap<Integer, Double>();
		Map<Integer,Integer> shortestTree = new HashMap<Integer,Integer>();
		tentativeDistances.put(this.id, new Double(0));
		Set<Integer> visitedAtoms = new HashSet<Integer>();
		boolean moreAtomsToVisit = true;
		Set<Integer> visitableAtoms = new HashSet<Integer>();
		visitableAtoms.add(this.id);
		while (moreAtomsToVisit) {
			// find atom with the minimum distance
			Integer currentAtom = null;
			Double currentDistance = null;
			for(Integer atomId: visitableAtoms) {
				if(!block.contains(atomId) && !visitedAtoms.contains(atomId)) {
					if(currentDistance == null || tentativeDistances.get(atomId).compareTo(currentDistance) < 0) {
						currentAtom = atomId;
						currentDistance = tentativeDistances.get(atomId);
					}
				}
			}
			if(currentAtom == null) moreAtomsToVisit = false;
			else {
				LayoutAtom currentAtomObject = state.getObject(LayoutAtom.class, currentAtom);
				// consider all the unvisited and unblocked neighbours and update their distances
				for(Integer atomId: currentAtomObject.neighbours)
					if(!block.contains(atomId) && !visitedAtoms.contains(atomId)) {
						visitableAtoms.add(atomId);
						Double distance = new Double(tentativeDistances.get(currentAtomObject.id).doubleValue() + currentAtomObject.pos.distanceTo(state.getObject(LayoutAtom.class, atomId).pos));
						if( (!tentativeDistances.containsKey(atomId)) || tentativeDistances.get(atomId).compareTo(distance) > 0) {
							tentativeDistances.put(atomId, distance);
							shortestTree.put(atomId, currentAtomObject.id);
						}
					}	
				visitedAtoms.add(currentAtom);
				
			}
		}
		//find the closest target atom, if any:
		Integer closest = null;
		Double distance = null;
		for(Integer atomId: targets) {
			if(tentativeDistances.containsKey(atomId) && 
					(distance == null || tentativeDistances.get(atomId).compareTo(distance) < 0)) {
				closest = atomId;
				distance = tentativeDistances.get(atomId);
			}
		}
		Path path = new Path();
		if (closest != null) {
			Integer atomId = closest;
			while(atomId != id) {
				path.prependAtomId(atomId);
				atomId = shortestTree.get(atomId);
			}
			path.prependAtomId(this.id);
		}
		return path;
	}
	
	
	public Path shortestPathToNaive(Set<Integer> targets, Set<Integer> block) {
		
		if(targets.contains(id))
			return new Path(new LinkedList<Integer>(Arrays.asList(id)), 0.0);
		
		Path subpath = new Path();
		block.add(id);
		
		for(Integer atomId: neighbours)
			if(!block.contains(atomId)) {
				Path candidate = state.getObject(LayoutAtom.class, atomId).shortestPathToNaive(targets, block);
				if(candidate.betterThan(subpath)) {
					subpath = candidate;
					break; // ONLY FOR DEBUG PURPOSES: remove to have true shortest paths.
				}
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

	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Triple> getSensorInfoRDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Triple> getSensorSchemaRDF() {
		// TODO Auto-generated method stub
		return null;
	}
}
