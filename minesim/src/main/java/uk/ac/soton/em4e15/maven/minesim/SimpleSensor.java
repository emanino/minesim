package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Random;
import java.util.Set;

import uk.ac.soton.em4e15.maven.minesim.observresult.ObservationValue;
import uk.ac.soton.em4e15.maven.minesim.observresult.ObservedPosition;
import uk.ac.soton.em4e15.maven.minesim.observresult.ObservedDouble;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class SimpleSensor implements MovingObject {
	
	

	private Integer id;
	private Position pos;
	private MineState state;
	private SensorType type;
	private ObservationValue reading;
	
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
	
	public ObservationValue getReading() {
		return reading;
	}
	
	public void updateReading() {
		LayoutAtom atom = state.getClosestLayoutAtom(pos);
		switch(type) {
			case TEMP:
				reading = new ObservedDouble(atom.getStatus().getTemp()); // perfect sensor for now
				break;
			case CO2:
				reading = new ObservedDouble(atom.getStatus().getCO2()); // perfect sensor for now
				break;
			case LOCATION:
				reading = new ObservedPosition(pos); // perfect sensor for now
				break;
		}
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		new SimpleSensor(this, next);
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
			name = "Carbon Monoxide Concentration";
			break;
		case LOCATION:
			name = "Location";
			break;
		case WORKERLOCATION:
			name = "Worker's Location";
			break;
		}
		return "{\"type\":\"sensor\",\"name\":\""+ id + "\",\"c\":" + pos.toJsonGui() + ",\"reading\":" + getReading().toJsonGui() + "}, "
				+ "{ \"type\": \"infoPredicate\", \"predicateName\": \""+name+"\", \"data\": ["
					+ "{\"value\": \""+id+"\", \"type\": \"sensor\"}, "
					+ "{\"value\": "+getReading().toJsonGui()+", \"type\": \"reading\"} "
				+ "] }";
	}

	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		// TODO Auto-generated method stub
		
	}
}
