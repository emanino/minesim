package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.ResourceFactory;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class Tunnel implements LayoutObject {
	
	private Integer id;
	private MineState state;
	private Position head;
	private Position tail;
	private List<Integer> atoms;
	
	private boolean allowVehicles;
	
	// create a new Tunnel between two given positions
	Tunnel(MineState state, Position head, Position tail, int nAtoms, LayoutAtomStatus status, double radius, SortedSet<LayoutAtom> layoutAtomtoUpdate, boolean allowVehicles) {
		id = state.getNextId();
		status.setSensorId(id);
		this.state = state;
		this.head = head;
		this.tail = tail;
		atoms = new ArrayList<Integer>();
		this.allowVehicles = allowVehicles;
		state.addNew(this);
		
		// check increment.length inside (radius/2, radius)
		Position increment = tail.minus(head).times(1.0 / (double) nAtoms);
		double length = increment.length();
		if(radius >= length * 2.0)
			throw new IllegalArgumentException("The required number of atoms is too large for this radius");
		if(radius <= length)
			throw new IllegalArgumentException("The required number of atoms is too small for this radius");
		
		// create uniform atoms along the length of the Tunnel
		Position pos = head.plus(increment.times(0.5));
		for(int i = 0; i < nAtoms; ++i) {
			LayoutAtom atom = new LayoutAtom(id, pos, state, new LayoutAtomStatus(status,id), radius, allowVehicles);
			layoutAtomtoUpdate.add(atom);
			atoms.add(atom.getId());
			pos = pos.plus(increment);
		}
	}
	
	// copy the Tunnel into the next state
	Tunnel(Tunnel tunnel, MineState next) {
		id = tunnel.getId();
		state = next;
		head = tunnel.getHead();
		tail = tunnel.getTail();
		atoms = tunnel.getAtomList();
		allowVehicles = tunnel.areVehiclesAllowed();
		state.addOld(this);
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	public MineState getState() {
		return state;
	}
	
	public Position getHead() {
		return head;
	}
	
	public Position getTail() {
		return tail;
	}
	
	@Override
	public Set<Integer> getAtoms() {
		return new HashSet<Integer>(atoms);
	}
	
	public List<Integer> getAtomList() {
		return atoms;
	}
	
	public Integer getFirstAtom() {
		if(atoms.size() > 0)
			return atoms.get(0);
		else
			return null;
	}
	
	public Integer getLastAtom() {
		if(atoms.size() > 0)
			return atoms.get(atoms.size() - 1);
		else
			return null;
	}
	
	public boolean areVehiclesAllowed() {
		return allowVehicles;
	}

	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		new Tunnel(this, next);
		// update tunnel...
		// - e.g. if an explosion increases its length
	}

	@Override
	public String toJsonGui() {
		return "{\"type\":\"tunnel\",\"name\":\"T"+ id + "\",\"c1\":" + head.toJsonGui() + ",\"c2\":" + tail.toJsonGui() + "}";
	}

	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Set<Triple> getSensorInfoRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String baseURI = state.getProp().getProperty("baseURI");
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+id), 
				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), 
				NodeFactory.createURI(baseURI+"Tunnel")));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+id), 
				NodeFactory.createURI("http://www.opengis.net/rdf#hasGeometry"), 
				NodeFactory.createURI(baseURI+"geo"+id)));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+"geo"+id), 
				NodeFactory.createURI("http://www.opengis.net/ont/gmlpos"), 
				ResourceFactory.createTypedLiteral("{"+head.toJsonGui()+" , "+tail.toJsonGui()+"}").asNode()));
		return triples;
	}
	
	@Override
	public Set<Triple> getSensorSchemaRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String lambda = state.getProp().getProperty("lambdaURI");
		String baseURI = state.getProp().getProperty("baseURI");
		triples.add(new Triple(
				NodeFactory.createURI(lambda),
				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), 
				NodeFactory.createURI(baseURI+"Tunnel")));
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
