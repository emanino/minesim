package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

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
			
			// find the current LayoutAtom
			LayoutAtom currAtom = this.getState().getClosestLayoutAtom(this.getPosition());
			
			boolean outsideMine = false;
			for(Position exit : getState().getExits()) {
				if(!outsideMine && getPosition().distanceTo(exit) < Double.parseDouble(getState().getProp().getProperty("exitRadius")))
					outsideMine = true;
			}
			
			if(outsideMine) {
				// RECOVER WHEN OUTSIDE
				person.getStatus().restAndRecover();
			} else {
				// TAKE DAMAGE FROM THE ENVIRONMENT
				person.getStatus().workAndTire(currAtom.getStatus());
			}
			
			// the shift has ended
			if(outsideMine && isTheShiftEnding) {
				getStatistics().recordEndOfShift(this);
				person.setTheShiftIsEnding(false);
			}
			
			// MOAR STATISTICS (temperature)
			if(currAtom.getStatus().getVariableTemp().isAboveDangerThreshold())
				getStatistics().recordTempRiskEvent(person, currAtom);
			if(currAtom.getStatus().getVariableTemp().isAboveMaxThreshold())
				getStatistics().recordTempInjuryEvent(person, currAtom);
			
			// MOAR STATISTICS (CO2)
			if(currAtom.getStatus().getVariableCO2().isAboveDangerThreshold())
				getStatistics().recordCO2RiskEvent(person, currAtom);
			if(currAtom.getStatus().getVariableCO2().isAboveMaxThreshold())
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
			} else {
				//If there is no evacuation, then do other activities:
				
				// run away (if need be)!
				if(evacuate.contains(currAtom.getId())) {
					path = currAtom.shortestPathOut(evacuate);
					
				} else if(getStatus().getRestBar() <= 0){
					// if tired go out of the mine
					path = goOut(currAtom, evacuate);
					person.setTheShiftIsEnding(true); // the shift is ending now
				}else {
					// find the target
					Integer siteId = scheduler.getMiningSite(this);
					
					// shortest path to the target
					if(siteId != null) {
						MiningSite site = this.getState().getObject(MiningSite.class, siteId);
						Position sitePos = site.getPosition();
						if(sitePos.distanceTo(this.getPosition()) > 0.01) {					
							// if the miner is still far from the position, move to the position
							Integer siteAtomId = this.getState().getClosestLayoutAtom(sitePos).getId();
							path = currAtom.shortestPathTo(new HashSet<Integer>(Arrays.asList(siteAtomId)), evacuate);
						} else {
							getStatistics().recordProduction(person, site); // if the miner is in position, production starts
						}
					}
				}
				
				if(path == null && !outsideMine) {
					// if the miner has no target, leave the mine
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
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"minerperson\",\"name\":\"P"+ this.getId() + "\",\"c\":" + this.getPosition().toJsonGui() + "}";
	}
}
