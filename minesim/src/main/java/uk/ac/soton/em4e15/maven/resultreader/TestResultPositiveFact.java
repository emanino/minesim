package uk.ac.soton.em4e15.maven.resultreader;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import uk.ac.soton.em4e15.maven.minesim.Mine;

abstract public class TestResultPositiveFact extends TestResultPositive implements TestResultPositiveFactInterface  {

	@Override
	public boolean isIfThen() {
		return false;
	}
	
	
	@Override
	public double evaluateQueryResults(Mine m, TupleQueryResult result) {
		// collect set of desired answers
		Set<String> tunnels = expectedSet(m);
		Set<String> results = new HashSet<String>();
		while (result.hasNext()) {
			BindingSet bindingSet = result.next();
			Value v = bindingSet.getBinding("v"+TestResultUtil.stringToInt("X")).getValue();
			String found = v.stringValue();	
			results.add(found);
				//if(tunnels.contains(found)) tunnels.remove(found);
				// it finds a wrong tunnel
				//else return 0;
			
	    }
		if(tunnels.size() != results.size()) return 0;
		tunnels.removeAll(results);
		// if there are no more tunnels, then it found them all, and so the result is correct
		if(tunnels.size() == 0) return 1;
		// otherwise it failed
		return 0;
	}
}
