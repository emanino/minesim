package uk.ac.soton.em4e15.maven.minesim;

import java.util.Random;
import java.util.Set;
import org.apache.jena.graph.Triple;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class Fire implements EventObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	private FireStatus status;
	private MineStatistics stats;
	private boolean extinguished = false;
	
	// create a new Fire
	Fire(Position pos, MineState state, FireStatus status, MineStatistics stats) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.status = status;
		this.stats = stats;
		state.addNew(this);
		stats.recordFireStart(this);
	}
	
	// copy the Fire into the next state
	Fire (Fire fire, MineState next) {
		id = fire.getId();
		pos = fire.getPosition();
		state = next;
		status = fire.getStatus();
		stats = fire.getStatistics();
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
	
	public MineStatistics getStatistics() {
		return stats;
	}
	
	public void markAsExtinguished() { // USE THIS WHEN A FIRE GETS EXTINGUISHED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		extinguished = true;
		stats.recordFireEnds(this);
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		if(!extinguished) {
			Fire fire = new Fire(this, next);
			fire.getStatus().increaseStrength(); // gradually increase the strength of the fire
			// create another fire in a certain radius with a certain probability
		}
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

	@Override
	public Set<Triple> getSensorSchemaRDF() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remainingTimesteps() {
		return -1;
	}
}
