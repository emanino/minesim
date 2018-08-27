package uk.ac.soton.em4e15.maven.minesim;
import java.util.Date;
import java.util.HashSet;
//
import java.util.Random;
import java.util.Set;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.datatypes.xsd.impl.XSDDateTimeStampType;
import org.apache.jena.datatypes.xsd.impl.XSDDateTimeType;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.ResourceFactory;

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
		String propertyName = null;
		switch(type) {
		case TEMP:
			propertyName = "Temperature";
			break;
		case CO2:
			propertyName = "Carbon Monoxide Concentration";
			break;
		case LOCATION:
			propertyName = "Location";
			break;
		case WORKERLOCATION:
			propertyName = "Worker's Location";
			break;
		}
		return "{\"type\":\"sensor\",\"name\":\""+ id + "\",\"propertyName\":\""+ propertyName + "\",\"c\":" + pos.toJsonGui() + ",\"reading\":" + getReading().toJsonGui() + "}, "
				+ "{ \"type\": \"infoPredicate\", \"predicateName\": \""+propertyName+"\", \"data\": ["
					+ "{\"value\": \""+id+"\", \"type\": \"sensor\"}, "
					+ "{\"value\": "+getReading().toJsonGui()+", \"type\": \"reading\"} "
				+ "] }";
	}

	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Triple> getSensorInfoRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String baseURI = state.getProp().getProperty("baseURI");
		String observationId = id+(new Date().getTime()+"");
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/madeBySensor"), 
				NodeFactory.createURI(baseURI+id)));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/observedProperty"), 
				NodeFactory.createURI(baseURI+type.getCode())));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/resultTime"), 
				ResourceFactory.createTypedLiteral(new Date()).asNode()));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/hasSimpleResult"), 
				ResourceFactory.createTypedLiteral( getReading().toJsonGui() ).asNode()));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.opengis.net/rdf#hasGeometry"), 
				NodeFactory.createURI("geo"+baseURI+observationId)));
		triples.add(new Triple(
				NodeFactory.createURI("geo"+baseURI+observationId), 
				NodeFactory.createURI("http://www.opengis.net/ont/gml#pos"), 
				ResourceFactory.createTypedLiteral(pos.toJsonGui()).asNode()));
		return triples;
	}
	
	@Override
	public Set<Triple> getSensorSchemaRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String lambda = state.getProp().getProperty("lambdaURI");
		String baseURI = state.getProp().getProperty("baseURI");
		String observationId = id+(new Date().getTime()+"");
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/madeBySensor"), 
				NodeFactory.createURI(lambda)));
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/observedProperty"), 
				NodeFactory.createURI(baseURI+type.getCode())));
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/resultTime"), 
				NodeFactory.createURI(lambda)));
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/hasSimpleResult"), 
				NodeFactory.createURI(lambda)));
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.opengis.net/rdf#hasGeometry"), 
				NodeFactory.createURI(lambda)));
		triples.add(new Triple(
				NodeFactory.createURI(lambda), 
				NodeFactory.createURI("http://www.opengis.net/ont/gmlpos"), 
				NodeFactory.createURI(lambda)));
		return triples;
	}
}
