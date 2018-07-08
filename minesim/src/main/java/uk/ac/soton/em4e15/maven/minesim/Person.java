package uk.ac.soton.em4e15.maven.minesim;
//
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
			state.getObject(MovingObject.class, obId).setPosition(pos);
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
	public void update(MineObjectScheduler scheduler, Random rand, MineState next) {
		new Person(this, next);
		// so far simple people have nothing to do in our beloved mine
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
