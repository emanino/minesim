package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class MiningSite implements AtomObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	
	MiningSite(Position pos, MineState state) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		state.addNew(this);
	}
	
	MiningSite(MiningSite site, MineState next) {
		id = site.getId();
		pos = site.getPosition();
		state = next;
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
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		new MiningSite(this, next);
	}

	@Override
	public String toJsonGui() {
		return "{\"type\":\"site\",\"name\":\"S"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
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
				NodeFactory.createURI(baseURI+"MiningSite")));
		return triples;
	}

	@Override
	public Set<Triple> getSensorSchemaRDF() {
		// TODO Auto-generated method stub
		return null;
	}



}
