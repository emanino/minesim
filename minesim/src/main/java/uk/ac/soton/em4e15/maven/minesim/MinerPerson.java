package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

public class MinerPerson extends Person {
	
	MinerPerson(Position pos, MineState state, PersonStatus status) {
		super(pos, state, status);
	}

	MinerPerson(MinerPerson person, MineState next) {
		super(person, next);
	}
	
	@Override
	public void update(MineObjectScheduler scheduler, Random rand, MineState next) {
		MinerPerson person = new MinerPerson(this, next);
		
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
		
			
		// MOVE AROUND:
		// extract the atoms we need to evacuate (if any)
		// if we need to evacuate the atom we are on, do so
		// else try to reach the target atom (if any)
		// while avoiding the atoms to be evacuated
		
		Path path = null;
		
		// run away (if need be)!
		SortedSet<Integer> evacuate = scheduler.getForbiddenAtoms();
		if(evacuate.contains(currAtom.getId())) {
			path = currAtom.shortestPathOut(evacuate);
		
		} else if(getStatus().getRestBar() <= 0){
			// if tired go out of the mine
			Set<Integer> exitIds =  new HashSet<Integer>();
			for(Position exit : this.getState().getExits()) {
				exitIds.add(this.getState().getClosestLayoutAtom(exit).getId());
			}
			path = currAtom.shortestPathTo(exitIds, evacuate);
		}else {
			// find the target
			Integer siteId = scheduler.getMiningSite(this);
			
			// shortest path to the target
			if(siteId != null) {
				Position sitePos = this.getState().getObject(MiningSite.class, siteId).getPosition();
				if(sitePos.distanceTo(this.getPosition()) > 0.01) {					
					// if the miner is still far from the position, move to the position
					Integer siteAtomId = this.getState().getClosestLayoutAtom(sitePos).getId();
					path = currAtom.shortestPathTo(new HashSet<Integer>(Arrays.asList(siteAtomId)), evacuate);
				}
			}
		}
		
		if(path == null && outsideMine) {
			// if the miner has no target, leave the mine
			Set<Integer> exitIds =  new HashSet<Integer>();
			for(Position exit : this.getState().getExits()) {
				exitIds.add(this.getState().getClosestLayoutAtom(exit).getId());
			}
			path = currAtom.shortestPathTo(exitIds, evacuate);
		}	
		// make a move
		if(path != null) {
			Position newPos = this.moveAlongPath(path, currAtom);
			person.setPosition(newPos);
		}
		
		// DO OTHER STUFF (e.g. mining coal)
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"minerperson\",\"name\":\"P"+ this.getId() + "\",\"c\":" + this.getPosition().toJsonGui() + "}";
	}
}
