package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

import uk.ac.soton.em4e15.maven.minesim.useractions.FullEvacuateUserAction;
import uk.ac.soton.em4e15.maven.minesim.useractions.UserAction;

public class FirePerson extends Person {
	
	private FireExtinguishingSkill skill;
	
	FirePerson(Position pos, MineState state, PersonStatus status, FireExtinguishingSkill skill, MineStatistics stats) {
		super(pos, state, status, stats);
		this.skill = skill;
	}

	FirePerson(FirePerson person, MineState next) {
		super(person, next);
		skill = person.getSkill();
	}
	
	public FireExtinguishingSkill getSkill() {
		return skill;
	}
	
	@Override
	public void update(Set<UserAction> actions, MineObjectScheduler scheduler, Random rand, MineState next) {
		FirePerson person = new FirePerson(this, next);
		
		// find the current LayoutAtom
		LayoutAtom currAtom = this.getState().getClosestLayoutAtom(this.getPosition());
		
		Path path = null;
		SortedSet<Integer> evacuate = scheduler.getForbiddenAtoms();
		
		if(actions.size() == 1 && actions.iterator().next().getClass() == FullEvacuateUserAction.class) {
			path = goOut(currAtom, evacuate);
		} else {
			// NO DAMAGE OR REST FOR FIREPEOPLE (FOR NOW)
			
			// MOVE AROUND:
			// FirePeople do not care about evacuated atoms
			// thus just move toward the target atoms
			
			// move along the shortest path to any target
			Integer layoutId = scheduler.getLayoutObject(this);
			if(layoutId != null) {
				LayoutObject obj = this.getState().getObject(LayoutObject.class, layoutId);
				path = currAtom.shortestPathTo(obj.getAtoms(), new HashSet<Integer>());
			}
			
			// TO DO: actively go around the LayoutObject looking for more Fires to smother
			
			// start extinguishing fires when stationary
			if(this.getPosition().getXYZ().equals(person.getPosition().getXYZ()))
				person.extinguishFires();
		}
		// make a move
		if(path != null) {
			Position newPos = this.moveAlongPath(path, currAtom);
			person.setPosition(newPos);
		}
	}
	
	private void extinguishFires() {
		
		// find the fires around the FirePerson
		SortedSet<Fire> fires = this.getState().getObjectsInRadius(Fire.class, this.getPosition(), skill.getRadius());
		
		// smother the fires, remove them when finished
		for(Fire fire: fires) {
			double strength = fire.getStatus().getStrength();
			strength -= skill.getPower();
			if(strength > 0.0)
				fire.getStatus().setStrength(strength);
			else
				this.getState().removeObject(fire.getId());
		}
	}
	
	@Override
	public String toJsonGui() {
		return "{\"type\":\"fireperson\",\"name\":\"P"+ this.getId() + "\",\"c\":" + this.getPosition().toJsonGui() + "}";
	}
}
