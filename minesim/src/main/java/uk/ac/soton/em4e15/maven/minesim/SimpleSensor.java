package uk.ac.soton.em4e15.maven.minesim;
import java.util.Date;
import java.util.HashSet;
//
import java.util.Random;
import java.util.Set;

import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.ResourceFactory;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;

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
	private String featureOfInterest;
	
	// create a new SimpleSensor
	SimpleSensor(Position pos, MineState state, SensorType type, String featureOfInterest) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		this.type = type;
		this.featureOfInterest = featureOfInterest;
		this.updateReading();
		state.addNew(this);
	}
	
	// copy the SimpleSensor into the next state
	SimpleSensor(SimpleSensor sensor, MineState next) {
		id = sensor.getId();
		pos = sensor.getPosition();
		state = next;
		type = sensor.getType();
		featureOfInterest = sensor.getFeatureOfInterest();
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
	
	public String getFeatureOfInterest() {
		return featureOfInterest;
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
			case CO:
				reading = new ObservedDouble(atom.getStatus().getCO()); // perfect sensor for now
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
		String unitName = null;
		switch(type) {
		case TEMP:
			propertyName = "Temperature";
			unitName = "degrees celsius";
			break;
		case CO:
			propertyName = "Carbon Monoxide Concentration";
			unitName = "ppm";
			break;
		case LOCATION:
			propertyName = "Location";
			unitName = "coordinates";
			break;
		case WORKERLOCATION:
			propertyName = "Worker's Location";
			break;
		}
		return "{\"type\":\"sensor\",\"name\":\""+ id + "\",\"propertyName\":\""+ propertyName + "\",\"c\":" + pos.toJsonGui() + ",\"reading\":" + getReading().toJsonGui() + "}, "
				+ "{ \"type\": \"infoPredicate\", \"predicateName\": \""+propertyName+"\", \"data\": ["
					+ "{\"value\": \""+id+"\", \"type\": \"sensor\", \"label\":\"Sensor ID\" }, "
					+ "{\"value\": "+getReading().toJsonGui()+", \"type\": \"reading\", \"label\":\"Reading ("+unitName+")\"} "
				+ "] }";
	}

	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Triple> getSensorInfoRDF() {
		BaseDatatype datatype = new BaseDatatype("http://www.opengis.net/ont/geosparql#wktLiteral");
		switch(type) {
		case TEMP:
			datatype = new BaseDatatype(XMLSchema.DECIMAL.stringValue());
			break;
		case CO:
			datatype = new BaseDatatype(XMLSchema.DECIMAL.stringValue());
			break;
		case LOCATION:
			datatype = new BaseDatatype("http://www.opengis.net/ont/geosparql#wktLiteral");
			break;
		case WORKERLOCATION:
			datatype  = new BaseDatatype("http://www.opengis.net/ont/geosparql#wktLiteral");
			break;
		}
		
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
		/*if(type.equals(SensorType.LOCATION)) {			
			triples.add(new Triple(
					NodeFactory.createURI(baseURI+observationId), // this should not be simple result, but the complex one
					NodeFactory.createURI("http://www.w3.org/ns/sosa/hasSimpleResult"), 
					ResourceFactory.createTypedLiteral( getReading().toJsonGui() ).asNode()));
			triples.add(new Triple(
					NodeFactory.createURI(baseURI+observationId), 
					NodeFactory.createURI("http://www.opengis.net/rdf#hasGeometry"), 
					NodeFactory.createURI(baseURI+"geo"+observationId)));
			triples.add(new Triple(
					NodeFactory.createURI(baseURI+"geo"+observationId), 
					NodeFactory.createURI("http://www.opengis.net/ont/geosparql#asWKT"), 
					ResourceFactory.createTypedLiteral("POINT("+pos.getX()+" "+pos.getY()+")", new BaseDatatype("http://www.opengis.net/ont/geosparql#wktLiteral")).asNode()));
		} else {*/
		String literalValue = getReading().getLexicalValue();
		if(type.equals(SensorType.LOCATION)) {
			literalValue = ((ObservedPosition)getReading()).toWKT2D();
		}
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+observationId), 
				NodeFactory.createURI("http://www.w3.org/ns/sosa/hasSimpleResult"), 
				ResourceFactory.createTypedLiteral( literalValue, datatype).asNode()));
		//}
		if(featureOfInterest != null) {
			triples.add(new Triple(
					NodeFactory.createURI(baseURI+observationId), 
					NodeFactory.createURI("http://www.w3.org/ns/sosa/hasFeatureOfInterest"), 
					NodeFactory.createURI(featureOfInterest)));
		}
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
		if(featureOfInterest != null) {
			triples.add(new Triple(
					NodeFactory.createURI(lambda), 
					NodeFactory.createURI("http://www.w3.org/ns/sosa/hasFeatureOfInterest"), 
					NodeFactory.createURI(lambda)));
		}
		return triples;
	}
}
