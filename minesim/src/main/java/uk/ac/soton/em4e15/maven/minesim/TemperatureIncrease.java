package uk.ac.soton.em4e15.maven.minesim;

import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.jena.graph.Triple;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class TemperatureIncrease implements EventObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	private TemperatureIncreaseStatus status;
	private int countdown;
	
	// create a new GasLeak
	TemperatureIncrease(Position pos, MineState state, TemperatureIncreaseStatus status, Properties prop) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.status = status;
		countdown = (int) (Double.parseDouble(prop.getProperty("tempIncreaseDuration")) / Double.parseDouble(prop.getProperty("timeStep")));
		state.addNew(this);
	}
	
	// copy the GasLeak into the next state
	TemperatureIncrease(TemperatureIncrease increase, MineState next) {
		id = increase.getId();
		pos = increase.getPosition();
		state = next;
		status = increase.getStatus();
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

	public TemperatureIncreaseStatus getStatus() {
		return status;
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		if(countdown > 0) {
			new TemperatureIncrease(this, next);
			// to do: something more sensible than small/big increases
		}
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
	public String toJsonGui() {
		return "{\"type\":\"tempincrease\",\"name\":\"F"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
	}

	@Override
	public int remainingTimesteps() {
		return countdown;
	}

}
