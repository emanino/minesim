package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Random;
import java.util.Set;

public class Fire implements AtomObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	private FireStatus status;
	
	// create a new Fire
	Fire(Position pos, MineState state, FireStatus status) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.status = status;
		state.addNew(this);
	}
	
	// copy the Fire into the next state
	Fire (Fire fire, MineState next) {
		id = fire.getId();
		pos = fire.getPosition();
		state = next;
		status = fire.getStatus();
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
	
	public FireStatus getStatus() {
		return status;
	}

	@Override
	public void update(Set<MicroAction> actions, Random rand, MineState next) {
		Fire fire = new Fire(this, next);
		fire.getStatus().increaseStrength(); // gradually increase the strength of the fire
		// create another fire in a certain radius with a certain probability
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"fire\",\"name\":\"F"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
	}
}
