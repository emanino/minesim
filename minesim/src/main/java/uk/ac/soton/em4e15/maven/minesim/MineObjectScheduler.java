package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MineObjectScheduler {
	
	private MineState state;
	private double maxShiftLength; // from properties
	private double currShiftLength;
	private double timeStep;
	private Map<Integer, Integer> minerSitePair;
	private Map<Integer, Integer> firemanLayoutPair;
	private Set<Integer> forbiddenAtoms;
	
	MineObjectScheduler(MineState state, Properties prop) {
		
		this.state = state;
		maxShiftLength = (int) (Double.parseDouble(prop.getProperty("shiftLength")) * 60 / Double.parseDouble(prop.getProperty("timeStep")));
		currShiftLength = maxShiftLength; // force a new shift at the first iteration
		timeStep = Double.parseDouble(prop.getProperty("timeStep"));
		minerSitePair = new HashMap<Integer, Integer>();
		firemanLayoutPair = new HashMap<Integer, Integer>();
		forbiddenAtoms = new HashSet<Integer>();
	}
	
	public Integer getMiningSite(MinerPerson person) {
		return minerSitePair.get(person.getId());
	}
	
	public Integer getLayoutObject(FirePerson person) {
		return firemanLayoutPair.get(person.getId());
	}
	
	public Set<Integer> getForbiddenAtoms() {
		return forbiddenAtoms;
	}
	
	public void update(Set<UserAction> actions, MineState next) {
		
		// end of the current shift
		if(currShiftLength >= maxShiftLength) {
			refreshMinerSitePair();
			currShiftLength = 0.0;
		} else
			currShiftLength += timeStep;
		
		// evacuate user action
		forbiddenAtoms.clear();
		for(UserAction action: actions)
			if(action instanceof PartialEvacuateUserAction)
				evacuateLayoutObject(state.getObject(LayoutObject.class, ((PartialEvacuateUserAction) action).getLayoutId()));
			else if(action instanceof FullEvacuateUserAction)
				evacuateMine();
		
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
		Set<MiningSite> sites = state.getObjects(MiningSite.class);
		Iterator<MiningSite> it = sites.iterator();
		
		// assign well-rested miners to them
		for(MinerPerson person: state.getObjects(MinerPerson.class))
			if(person.getStatus().isRested() && it.hasNext())
				minerSitePair.put(person.getId(), it.next().getId());
	}
	
	private void assignFirePersonToTarget(LayoutObject target) {
		
		// assign a free FirePerson to the target
		for(FirePerson person: state.getObjects(FirePerson.class))
			if(!firemanLayoutPair.containsKey(person.getId()))
				firemanLayoutPair.put(person.getId(), target.getId());
	}
	
	// very hacky! improve it by:
	// identifying ALL the exits
	private void evacuateMine() {
		LayoutAtom exit = state.getClosestLayoutAtom(new Position(0.0, 0.0, 0.0));
		for(LayoutAtom atom: state.getObjects(LayoutAtom.class))
				forbiddenAtoms.add(atom.getId());
		forbiddenAtoms.remove(exit.getId());
	}
	
	private void evacuateLayoutObject(LayoutObject obj) {
		forbiddenAtoms.addAll(obj.getAtoms());
	}
}