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

public class TestResult_b1 extends TestResultPositiveIfThen{
	
	/**
	 * The condition B2 is "If there is a fire in a tunnel, send the fire team in that tunnel."
	 * @param positive
	 * @param m
	 * @return
	 */
	@Override
	public boolean conditionHolds(boolean positive, Mine m) {
		if(positive) return expectedSet(m).getLeft().size() > 0;
		else return Math.random() > 0.5 ?
				expectedSet(m).getLeft().size() == 0 : 
				expectedSetHelper(m).getLeft().size() > 0 && expectedSet(m).getLeft().size() == 0;
	}

	@Override
	public Pair<Set<String>, String> expectedSet(Mine m) {
		Set<String> highCO = TestResultUtil.getTunnelsAbove(m, SensorType.CO, 78);
		Set<String> highTemp = TestResultUtil.getTunnelsAbove(m, SensorType.TEMP, 48);
		highCO.retainAll(highTemp);
		Set<String> mainTunnels = TestResultUtil.getMainTunnels(m);
		highCO.retainAll(mainTunnels);
		
		return new ImmutablePair<Set<String>, String>(highCO,"fullEvacuation");
	}
	
	public Pair<Set<String>, String> expectedSetHelper(Mine m) {
		Set<String> highCO = TestResultUtil.getTunnelsAbove(m, SensorType.CO, 78);
		Set<String> highTemp = TestResultUtil.getTunnelsAbove(m, SensorType.TEMP, 48);
		highCO.retainAll(highTemp);
		
		return new ImmutablePair<Set<String>, String>(highCO,"dispatchFireTeam");
	}


}
