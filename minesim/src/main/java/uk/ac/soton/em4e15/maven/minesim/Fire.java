package uk.ac.soton.em4e15.maven.minesim;
import java.util.List;
//
import java.util.Random;
import java.util.Set;

import org.apache.jena.graph.Triple;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

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
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		Fire fire = new Fire(this, next);
		fire.getStatus().increaseStrength(); // gradually increase the strength of the fire
		// create another fire in a certain radius with a certain probability
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"fire\",\"name\":\"F"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
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
}
