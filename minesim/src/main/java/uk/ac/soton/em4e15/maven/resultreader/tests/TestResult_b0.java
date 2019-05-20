package uk.ac.soton.em4e15.maven.resultreader.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.rio.RDFFormat;

import logic.ExternalDB;
import logic.ExternalDB_GraphDB;
import uk.ac.soton.em4e15.maven.minesim.AtomObject;
import uk.ac.soton.em4e15.maven.minesim.FirePerson;
import uk.ac.soton.em4e15.maven.minesim.LayoutAtom;
import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.minesim.SensorType;
import uk.ac.soton.em4e15.maven.minesim.SimpleSensor;
import uk.ac.soton.em4e15.maven.minesim.Tunnel;
import uk.ac.soton.em4e15.maven.minesim.observresult.ObservedDouble;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;
import uk.ac.soton.em4e15.maven.resultreader.Result;
import uk.ac.soton.em4e15.maven.resultreader.TestResult;
import uk.ac.soton.em4e15.maven.resultreader.TestResultNegative;
import uk.ac.soton.em4e15.maven.resultreader.TestResultPositive;
import uk.ac.soton.em4e15.maven.resultreader.TestResultPositiveFact;
import uk.ac.soton.em4e15.maven.resultreader.TestResultPositiveIfThen;
import uk.ac.soton.em4e15.maven.resultreader.TestResultUtil;

public class TestResult_b0 extends TestResultPositiveIfThen{
	
	/**
	 * If a person is in a geofenced tunnel, then recall that person."
	 * @param positive
	 * @param m
	 * @return
	 */
	@Override
	public boolean conditionHolds(boolean positive, Mine m) {
		if(positive) return expectedSet(m).getLeft().size() > 0;
		else return expectedSet(m).getLeft().size() == 0 ;
	}

	@Override
	public Pair<Set<String>, String> expectedSet(Mine m) {
		Set<String> geofenced = TestResultUtil.getGeofencedTunnels(m);
		Set<String> tunnelsWithPeople = TestResultUtil.getTunnelsWithHumans(m);
		geofenced.retainAll(tunnelsWithPeople);
		
		Set<String> humansInGeofencedTunnel = new HashSet<String>();
		for(String s : geofenced) {
			humansInGeofencedTunnel.addAll(TestResultUtil.getHumansInTunnel(m, s));
		}
		
		/*if(humansInGeofencedTunnel.size() >0) {
			System.out.print("");
			for(String s : geofenced) {
				humansInGeofencedTunnel.addAll(TestResultUtil.getHumansInTunnel(m, s));
			}			
		}*/
		
		return new ImmutablePair<Set<String>, String>(humansInGeofencedTunnel,"recall");
	}
	



}
