package uk.ac.soton.em4e15.maven.resultreader;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import uk.ac.soton.em4e15.maven.minesim.Mine;
import uk.ac.soton.em4e15.maven.resultreader.tests.TestResult_a0;

abstract public class TestResultPositiveFact extends TestResultPositive implements TestResultPositiveFactInterface  {

	@Override
	public boolean isIfThen() {
		return false;
	}
	
	
	@Override
	public double evaluateQueryResults(Mine m, TupleQueryResult result, Result r) {
		if(this.getClass() == TestResult_a0.class) {
			return evaluateQueryResultsTwoParams(m,result,r);
		} else {
			return evaluateQueryResultsOneParam(m,result,r);
		}
	}
	
	public double evaluateQueryResultsOneParam(Mine m, TupleQueryResult result, Result r) {
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
	public double evaluateQueryResultsTwoParams(Mine m, TupleQueryResult result, Result r) {
		// collect set of desired answers
				Set<String> expectedAandB = expectedSet(m);
				Set<String> results = new HashSet<String>();
				
				while (result.hasNext()) {
					BindingSet bindingSet = result.next();
					Value v1 = bindingSet.getBinding("v"+TestResultUtil.stringToInt("A")).getValue();
					Value v2 = bindingSet.getBinding("v"+TestResultUtil.stringToInt("B")).getValue();
					String found1 = v1.stringValue();	
					String found2 = v2.stringValue();
					results.add(found1+"|"+found2);
			    }
				if(expectedAandB.size() != results.size()) return 0;
				expectedAandB.removeAll(results);
				// if there are no more tunnels, then it found them all, and so the result is correct
				if(expectedAandB.size() == 0) return 1;
				// otherwise it failed
				return 0;
	}
}
