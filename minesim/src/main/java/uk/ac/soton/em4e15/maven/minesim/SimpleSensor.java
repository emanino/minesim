package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Random;
import java.util.Set;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class SimpleSensor implements MovingObject {
	
	public enum SensorType {TEMP, CO2}

	private Integer id;
	private Position pos;
	private MineState state;
	private SensorType type;
	private double reading;
	
	// create a new SimpleSensor
	SimpleSensor(Position pos, MineState state, SensorType type) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.type = type;
		this.updateReading();
		state.addNew(this);
	}
	
	// copy the SimpleSensor into the next state
	SimpleSensor(SimpleSensor sensor, MineState next) {
		id = sensor.getId();
		pos = sensor.getPosition();
		state = next;
		type = sensor.getType();
		sensor.updateReading();
		reading = sensor.getReading();
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
	}
	
	public SensorType getType() {
		return type;
	}
	
	public double getReading() {
		return reading;
	}
	
	public void updateReading() {
		LayoutAtom atom = state.getClosestLayoutAtom(pos);
		switch(type) {
			case TEMP:
				reading = atom.getStatus().getTemp(); // perfect sensor for now
				break;
			case CO2:
				reading = atom.getStatus().getCO2(); // perfect sensor for now
				break;
		}
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		SimpleSensor sensor = new SimpleSensor(this, next);
		//sensor.updateReading();
	}
	
	@Override
	public String toJsonGui() {
		// add field with Sensor Type
		String name = null;
		switch(type) {
		case TEMP:
			name = "Temperature";
			break;
		case CO2:
			name = "Carbon Monoxide";
			break;
		}
		return "{\"type\":\"sensor\",\"name\":\""+ id + "\",\"c\":" + pos.toJsonGui() + ",\"reading\":" + getReading() + "}, "
				+ "{ \"type\": \"infoPredicate\", \"predicateName\": \""+name+"\", \"data\": ["
					+ "{\"value\": \""+id+"\", \"type\": \"sensor\"}, "
					+ "{\"value\": \""+getReading()+"\", \"type\": \"reading\"} "
				+ "] }";
	}
}
