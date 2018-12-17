package uk.ac.soton.em4e15.maven.resultreader.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

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
import uk.ac.soton.em4e15.maven.resultreader.TestResultUtil;

public class TestResult_a0 extends TestResultPositiveFact{
	
	/**
	 * The condition A0 is "Person A is in a geofenced area B"
	 * @param positive
	 * @param m
	 * @return
	 */
	@Override
	public boolean conditionHolds(boolean positive, Mine m) {
		if(positive) return expectedSet(m).size() > 0;
		else return expectedSet(m).size() == 0;
	}
	/*public boolean conditionHolds(boolean positive, Mine m) {
		for(LayoutAtom t: m.getState().getObjects(LayoutAtom.class)) {
			double CO = t.getStatus().getCO();
			double temp = t.getStatus().getTemp();
			if(CO > (78+MineTestHelper.getCOslack()) && 
					temp > (48+MineTestHelper.getTempslack())) return true;
		}
		return false;
	}*/

	public Set<String> expectedSet(Mine m){
		Set<String> geofencedTunnel = TestResultUtil.getGeofencedTunnels(m);
		Set<String> tunnelsWithHumans = TestResultUtil.getTunnelsWithHumans(m);
		geofencedTunnel.retainAll(tunnelsWithHumans);
		return geofencedTunnel;
	}




}
