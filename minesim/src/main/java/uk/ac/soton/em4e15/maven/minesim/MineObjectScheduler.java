package uk.ac.soton.em4e15.maven.minesim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.PartialEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class MineObjectScheduler {
	
	private MineState state;
	private double maxShiftLength; // from properties
	private Double currShiftLength;
	private double timeStep;
	private SortedMap<Integer, Integer> minerSitePair;
	private SortedMap<Integer, Integer> firemanLayoutPair;
	private SortedSet<Integer> forbiddenAtoms;
	
	MineObjectScheduler(MineState state, Properties prop) {
		this.state = state;
		maxShiftLength = (int) (Double.parseDouble(prop.getProperty("shiftLength")) / Double.parseDouble(prop.getProperty("timeStep")));
		currShiftLength = maxShiftLength; // force a new shift at the first iteration
		timeStep = Double.parseDouble(prop.getProperty("timeStep"));
		minerSitePair = new TreeMap<Integer, Integer>();
		firemanLayoutPair = new TreeMap<Integer, Integer>();
		forbiddenAtoms = new TreeSet<Integer>();
	}
	
	public Integer getMiningSite(MinerPerson person) {
		return minerSitePair.get(person.getId());
	}
	
	public Integer getLayoutObject(FirePerson person) {
		return firemanLayoutPair.get(person.getId());
	}
	
	public SortedSet<Integer> getForbiddenAtoms() {
		return forbiddenAtoms;
	}
	
	public void update(Set<UserAction> actions, MineState next) {
		// end of the current shift
		if(currShiftLength >= maxShiftLength) {
			currShiftLength = currShiftLength-maxShiftLength;
			refreshMinerSitePair();
		} else {			
			currShiftLength += timeStep;
		}
		
		// evacuate user action
		//forbiddenAtoms.clear();
		for(UserAction action: actions)
			if(action instanceof PartialEvacuateUserAction)
				evacuateLayoutObject(state.getObject(LayoutObject.class, ((PartialEvacuateUserAction) action).getLayoutId()));
			//else if(action instanceof FullEvacuateUserAction)
			//	evacuateMine();
		
		// send fire team user action
		for(UserAction action: actions)
			if(action instanceof SendFireTeamUserAction) {
				Integer layoutId = ((SendFireTeamUserAction) action).getLayoutId();
				if(!firemanLayoutPair.containsValue(layoutId))
					assignFirePersonToTarget(state.getObject(LayoutObject.class,  layoutId));
			}
		
		// advance the state
		state = next;
	}
	
	private void refreshMinerSitePair() {
		// remove the previous shift
		minerSitePair.clear();
		
		// identify the mining sites
		SortedSet<MiningSite> sites = state.getObjects(MiningSite.class);
		Iterator<MiningSite> it = sites.iterator();
		
		// assign well-rested miners to them
		for(MinerPerson person: state.getObjects(MinerPerson.class)) {			
			if(person.getStatus().isRested() && it.hasNext())
				minerSitePair.put(person.getId(), it.next().getId());
		}
	}
	
	private void assignFirePersonToTarget(LayoutObject target) {
		
		// assign a free FirePerson to the target
		for(FirePerson person: state.getObjects(FirePerson.class))
			if(!firemanLayoutPair.containsKey(person.getId()))
				firemanLayoutPair.put(person.getId(), target.getId());
	}
	
	/*// very hacky! improve it by:
	// identifying ALL the exits
	private void evacuateMine() {
		LayoutAtom exit = state.getClosestLayoutAtom(new Position(0.0, 0.0, 0.0));
		for(LayoutAtom atom: state.getObjects(LayoutAtom.class))
				forbiddenAtoms.add(atom.getId());
		forbiddenAtoms.remove(exit.getId());
	}*/
	
	private void evacuateLayoutObject(LayoutObject obj) {
		forbiddenAtoms.addAll(obj.getAtoms());		
	}
	
	public List<String> toJsonGui() {
		List<String> strings = new ArrayList<String>();
		for(int id : forbiddenAtoms) {
			strings.add("{\"type\":\"geofencedAtom\",\"c\":" + state.getObject(LayoutAtom.class, id).getPosition().toJsonGui() + "}");
		}
		return strings;
		}
}
