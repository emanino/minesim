package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import uk.ac.soton.em4e15.maven.minesim.LayoutAtomStatusVariable.LayoutAtomStatusLevel;
import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class MinerPerson extends Person {
	
	boolean isTheShiftEnding;
	
	MinerPerson(Position pos, MineState state, PersonStatus status, MineStatistics stats) {
		super(pos, state, status, stats);
		isTheShiftEnding = false;
	}

	MinerPerson(MinerPerson person, MineState next) {
		super(person, next);
		isTheShiftEnding = person.isTheShiftEnding();
	}
	
	public boolean isTheShiftEnding() {
		return isTheShiftEnding;
	}
	
	public void setTheShiftIsEnding(boolean value) {
		isTheShiftEnding = value;
	}
	
	@Override
	public void postUpdate(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		setCarriedObjectsPosition(this.getPosition());
	}
	
	
	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		MinerPerson person = new MinerPerson(this, next);
		
		// THIS PERSON IS DEAD, LEAVE THEM THERE
		if(this.getStatus().getHealth() <= 0.0) {
			getStatistics().recordDeath(this);
		
		// ELSE DO STUFF
		} else {
			
			// evaluate the current position
			LayoutAtom currAtom = this.getState().getClosestLayoutAtom(this.getPosition());
			boolean outsideMine = this.isOutside();
			
			// update the status
			if(outsideMine) {
				person.getStatus().restAndRecover(); // recover when outside
			} else {
				person.getStatus().workAndTire(currAtom.getStatus()); // get tired when inside
			}
			
			// shift has ended, restore the flag
			if(outsideMine && isTheShiftEnding) {
				getStatistics().recordEndOfShift(this);
				person.setTheShiftIsEnding(false);
			}
			
			// health statistics (temperature)
			if(currAtom.getStatus().getVariableTemp().isAboveLevel(LayoutAtomStatusLevel.DANGER))
				getStatistics().recordTempRiskEvent(person, currAtom);
			if(currAtom.getStatus().getVariableTemp().isAboveLevel(LayoutAtomStatusLevel.HIGH))
				getStatistics().recordTempInjuryEvent(person, currAtom);
			
			// health statistics (CO2)
			if(currAtom.getStatus().getVariableCO2().isAboveLevel(LayoutAtomStatusLevel.DANGER))
				getStatistics().recordCO2RiskEvent(person, currAtom);
			if(currAtom.getStatus().getVariableCO2().isAboveLevel(LayoutAtomStatusLevel.HIGH))
				getStatistics().recordCO2InjuryEvent(person, currAtom);
				
			// MOVE AROUND:
			// extract the atoms we need to evacuate (if any)
			// if we need to evacuate the atom we are on, do so
			// else try to reach the target atom (if any)
			// while avoiding the atoms to be evacuated
			
			Path path = null;
			SortedSet<Integer> evacuate = scheduler.getForbiddenAtoms();
			
			// If there is an evacuate user action, go outside the mine.
			// This takes priority over other activities
			if(actions.size() == 1 && actions.iterator().next().getClass() == FullEvacuateUserAction.class) {
				if(!outsideMine) {
					path = goOut(currAtom, evacuate);
					person.setTheShiftIsEnding(true); // the shift is ending now
				}
			} else { //If there is no evacuation, then do other activities:
				
				// run away (if need be)!
				if(evacuate.contains(currAtom.getId())) {
					path = currAtom.shortestPathOut(evacuate);
					
				} else if(getStatus().getRestBar() <= 0) { // if tired go out of the mine
					path = goOut(currAtom, evacuate);
					person.setTheShiftIsEnding(true); // the shift is ending now
					
				} else { // keep going towards/staying at the target
					Integer siteId = scheduler.getMiningSite(this);
					
					// shortest path to the target
					if(siteId != null) {
						MiningSite site = this.getState().getObject(MiningSite.class, siteId);
						Position sitePos = site.getPosition();
						Integer siteAtomId = this.getState().getClosestLayoutAtom(sitePos).getId();
						path = currAtom.shortestPathTo(new HashSet<Integer>(Arrays.asList(siteAtomId)), evacuate);
						
						// if the miner is in position, production starts
						if(path.getLength() <= 1) {
							getStatistics().recordProduction(person, site);
						}
					}
				}
				
				// if the miner has no target, leave the mine
				if(path == null && !outsideMine) {
					path = goOut(currAtom, evacuate);
					person.setTheShiftIsEnding(true); // the shift is ending now
				}	
			}
			// make a move
			if(path != null) {
				Position newPos = this.moveAlongPath(path, currAtom);
				person.setPosition(newPos);
			}
		}
	}
	
	public boolean isOutside() {
		double exitRadius = Double.parseDouble(getState().getProp().getProperty("exitRadius"));
		for(Position exit: getState().getExits())
			if(this.getPosition().distanceTo(exit) < exitRadius)
				return true;
		return false;
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"minerperson\",\"name\":\"P"+ this.getId() + "\",\"c\":" + this.getPosition().toJsonGui() + "}";
	}
}
