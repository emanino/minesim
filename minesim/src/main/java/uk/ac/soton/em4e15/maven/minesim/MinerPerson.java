package uk.ac.soton.em4e15.maven.minesim;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
		
		// TAKE DAMAGE FROM THE ENVIRONMENT
		person.getStatus().workAndSuffer(currAtom.getStatus());
		
		// SLOWLY RECOVER WHEN OUTSIDE
		if(person.getPosition().getY() <= 0.1) // temporary definition of "outside"
			person.getStatus().restAndRecover();
			
		// MOVE AROUND:
		// extract the atoms we need to evacuate (if any)
		// if we need to evacuate the atom we are on, do so
		// else try to reach the target atom (if any)
		// while avoiding the atoms to be evacuated
		
		Path path = null;
		
		// run away (if need be)!
		Set<Integer> evacuate = scheduler.getForbiddenAtoms();
		if(evacuate.contains(currAtom.getId())) {
			path = currAtom.shortestPathOut(evacuate);
		
		// find the target
		} else {
			Integer siteId = scheduler.getMiningSite(this);
			
			// shortest path to the target
			if(siteId != null) {
				Position sitePos = this.getState().getObject(MiningSite.class, siteId).getPosition();
				Integer siteAtomId = this.getState().getClosestLayoutAtom(sitePos).getId();
				path = currAtom.shortestPathTo(new HashSet<Integer>(Arrays.asList(siteAtomId)), evacuate);
			}
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
