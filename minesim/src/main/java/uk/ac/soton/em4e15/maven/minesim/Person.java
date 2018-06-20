package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Person implements MovingObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	private PersonStatus status;
	private Set<Integer> carried;
	
	// this attribute is temporary (needed to do a random walk)
	private LayoutAtom target = null;
	
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
	
	public PersonStatus getStatus() {
		return status;
	}
	
	public Set<Integer> getCarried() {
		return carried;
	}
	
	public void addCarried(Integer movId) {
		if(movId == id)
			throw new IllegalArgumentException("A Person cannot carry themselves");
		if(!(state.getObject(movId) instanceof MovingObject))
			throw new IllegalArgumentException("A Person can only carry MovingObjects");
		carried.add(movId);
	}
	
	public void removeCarried(Integer movId) {
		carried.remove(movId);
	}

	@Override
	public void update(Set<Action> actions, Random rand, MineState next) {
		Person person = new Person(this, next);
		// update person...
		// - moves around, does stuff (just a random walk for now)
		
		// find the closest LayoutAtom
		LayoutAtom currAtom = state.getClosestLayoutAtom(pos);
		
		// target is reached, choose the next one
		if(target == null || target.getId() == currAtom.getId()) {
			
			// find all the LayoutAtoms
			List<LayoutAtom> atoms = new ArrayList<LayoutAtom>();
			for(MineObject obj: state.getObjects())
				if(obj instanceof LayoutAtom)
					atoms.add((LayoutAtom) obj);
			
			// choose one at random
			int a = rand.nextInt(atoms.size());
			target = atoms.get(a);
		}
		
		// find the path to the target
		List<Integer> path = currAtom.shortestPathTo(target.getId(), new HashSet<Integer>());
		
		// move towards the target
		double distanceLeft = status.getSpeed();
		Position newPos = new Position(pos.getXYZ());
		for(int i = path.size() - 2; i >= 0; --i) {
			Position nextAtom = ((LayoutAtom) state.getObject(path.get(i))).getPosition();
			Position difference = nextAtom.minus(newPos);
			double length = difference.length();
			if(length < distanceLeft) {
				newPos = newPos.plus(difference);
				distanceLeft -= length;
			} else {
				newPos = newPos.plus(difference.times(distanceLeft / length));
				break;
			}
		}
		
		// overwrite the Position in the new Person
		person.setPosition(newPos);
	}
}
