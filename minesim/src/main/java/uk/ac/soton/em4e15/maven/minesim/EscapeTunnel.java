package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.ResourceFactory;

import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class EscapeTunnel extends Tunnel {
	
	EscapeTunnel(MineState state, Position head, Position tail, int nAtoms, LayoutAtomStatus status, double radius, SortedSet<LayoutAtom> layoutAtomtoUpdate) {
		super(state, head, tail, nAtoms, status, radius, layoutAtomtoUpdate, false); // no vehicles allowed
	}
	
	EscapeTunnel(EscapeTunnel tunnel, MineState next) {
		super(tunnel, next);
	}
	
	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		new EscapeTunnel(this, next);
		// update tunnel...
		// - e.g. if an explosion increases its length
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"escapetunnel\",\"name\":\"T"+ this.getId() + "\",\"c1\":" + this.getHead().toJsonGui() + ",\"c2\":" + this.getTail().toJsonGui() + "}";
	}
	
	@Override
	public Set<Triple> getSensorInfoRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String baseURI = this.getState().getProp().getProperty("baseURI");
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+this.getId()), 
				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), 
				NodeFactory.createURI(baseURI+"EscapeTunnel")));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+this.getId()), 
				NodeFactory.createURI("http://www.opengis.net/rdf#hasGeometry"), 
				NodeFactory.createURI(baseURI+"geo"+this.getId())));
		triples.add(new Triple(
				NodeFactory.createURI(baseURI+"geo"+this.getId()), 
				NodeFactory.createURI("http://www.opengis.net/ont/gmlpos"), 
				ResourceFactory.createTypedLiteral("{"+this.getHead().toJsonGui()+" , "+this.getTail().toJsonGui()+"}").asNode()));
		return triples;
	}
	
	@Override
	public Set<Triple> getSensorSchemaRDF() {
		Set<Triple> triples = new HashSet<Triple>();
		String lambda = this.getState().getProp().getProperty("lambdaURI");
		String baseURI = this.getState().getProp().getProperty("baseURI");
		triples.add(new Triple(
				NodeFactory.createURI(lambda),
				NodeFactory.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"), 
				NodeFactory.createURI(baseURI+"EscapeTunnel")));
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
