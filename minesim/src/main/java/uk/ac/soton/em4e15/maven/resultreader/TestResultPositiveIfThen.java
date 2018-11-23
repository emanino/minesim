package uk.ac.soton.em4e15.maven.resultreader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;

import logic.Binding;
import uk.ac.soton.em4e15.maven.minesim.Mine;

abstract public class TestResultPositiveIfThen extends TestResultPositive implements TestResultPositiveIfThenInterface {

	@Override
	public boolean isIfThen() {
		return true;
	}
	
	@Override
	public double evaluateQueryResults(Mine m, TupleQueryResult result, Result r) {
		
		JsonArray ja = r.getThenBlock();
		// in these examples the action to take is a single one
		if(ja.size() != 1) return 0;
		JsonObject jo = (JsonObject) ja.get(0);
		String predicateName = jo.getString("name");
		Map<Integer,Binding> bindings = new HashMap<Integer,Binding>();
		for(JsonValue var: jo.asJsonObject().getJsonArray("variables")){
			TestResultUtil.processBinding(bindings, var);
		}		
		// in these examples, the action to take has either 1 or 0 variables
		if(bindings.keySet().size() > 1) return 0;
		Binding requiredBinding = null;
		if(bindings.keySet().size() == 1) {
			requiredBinding = bindings.values().iterator().next();
			// the binding for the consequent must be a variable
			if(!requiredBinding.isVar()) return 0;
		}
		
		Pair<Set<String>,String> expected = expectedSet(m);
		
		// check that the action is the right one
		if(! predicateName.equals(expected.getRight())) return 0;
		
		if(requiredBinding == null) {
			// if the consequent predicate does not have variables, we should just check whether it triggers or not
			if(result.hasNext() && expected.getLeft().size() > 0) return 1;
			else return 0;
		} else {
			// otherwise we need to check that the variable binding is correct
			Set<String> results = new HashSet<String>();
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();
				Value v = bindingSet.getBinding("v"+requiredBinding.getVar()).getValue();
				String found = v.stringValue();	
				results.add(found);
		    }
			if(expected.getLeft().size() != results.size()) return 0;
			expected.getLeft().removeAll(results);
			if(expected.getLeft().size() == 0) return 1;
		}
		return 0;
	}
	
}
