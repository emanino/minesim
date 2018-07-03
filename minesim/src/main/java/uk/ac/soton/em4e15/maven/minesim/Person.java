package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Person implements MovingObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	private PersonStatus status;
	private Set<Integer> carried;
	
	// create a new Person
	Person(Position pos, MineState state, PersonStatus status) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.status = status;
		carried = new HashSet<Integer>();
		state.addNew(this);
	}
	
	// copy the Person into the next state
	Person(Person person, MineState next) {
		id = person.getId();
		pos = person.getPosition();
		state = next;
		status = person.getStatus();
		carried = person.getCarried();
		state.addOld(this);
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public Position getPosition() {
		return pos;
	}
	
	@Override
	public void setPosition(Position pos) {
		this.pos = pos;
		
		// move all the carried objects to the new position
		for(Integer obId: carried)
			((MovingObject) state.getObject(obId)).setPosition(pos);
	}
	
	public MineState getState() {
		return state;
	}
	
	public PersonStatus getStatus() {
		return status;
	}
	
	public Set<Integer> getCarried() {
		return carried;
	}
	
	public void addCarried(MovingObject obj) {
		if(obj == this)
			throw new IllegalArgumentException("A Person cannot carry themselves");
		carried.add(obj.getId());
	}
	
	public void removeCarried(Integer movId) {
		carried.remove(movId);
	}

	@Override
	public void update(Set<Action> actions, Random rand, MineState next) {
		Person person = new Person(this, next);
		
		// find the subset of Actions that pertain this Person
		Set<Action> myActions = new HashSet<Action>();
		for(Action act: actions)
			if(act.getRecipientIds().contains(id))
				myActions.add(act);
		
		// find the current LayoutAtom
		LayoutAtom currAtom = state.getClosestLayoutAtom(pos);
		
		// TAKE DAMAGE:
		// high temperatures
		// high CO2
		person.getStatus().update(currAtom.getStatus());
		
		// MOVE AROUND:
		// extract the atoms we need to evacuate (if any)
		// if we need to evacuate the atom we are on, do so
		// else try to reach the target atom (if any)
		// while avoiding the atoms to be evacuated
		
		// find all the LayoutAtoms to be evacuated
		Set<Integer> evacuate = new HashSet<Integer>();
		for(Action act: myActions)
			if(act instanceof EvacuateAction)
				evacuate.addAll(((EvacuateAction) act).getAtomIds());
		
		// run away (if need be)!
		Path path = null;
		if(evacuate.contains(currAtom.getId())) {
			path = currAtom.shortestPathOut(evacuate);
			
		// find the target(s)
		} else {
			Set<Integer> targets = new HashSet<Integer>();
			for(Action act: myActions)
				if(act instanceof MoveAction)
					targets.add(((MoveAction) act).getTargetId());
			
			// shortest path to the target(s)
			if(targets.size() > 0)
				path = currAtom.shortestPathTo(targets, evacuate);
		}
		
		// make a move
		if(path != null) {
			Position newPos = this.moveAlongPath(path, currAtom);
			person.setPosition(newPos);
		}
		
		// DO OTHER STUFF
	}
	
	protected Position moveAlongPath(Path path, LayoutAtom currAtom) {
		
		if(path.getAtomIds().get(0) != currAtom.getId())
			throw new IllegalArgumentException("The current LayoutAtom must be the first LayoutAtom in this Path");
		
		Position newPos = new Position(pos);
		double distance = status.getDistance();
		
		Iterator<Integer> atomIds = path.getAtomIds().iterator();
		atomIds.next(); // skip the first atom
		
		while(atomIds.hasNext()) {
			Position step = ((LayoutAtom) state.getObject(atomIds.next())).getPosition().minus(newPos);
			double length = step.length();
			
			// jump to the next LayoutAtom
			if(length < distance) {
				newPos = newPos.plus(step);
				distance -= length;
			} else {
				newPos = newPos.plus(step.times(distance / length)); // fall short if we run out of distance
				break;
			}
		}
		
		return newPos;
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"person\",\"name\":\"P"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
	}
}
