package uk.ac.soton.em4e15.maven.minesim;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FirePerson extends Person {
	
	private FireExtinguishingSkill skill;
	
	FirePerson(Position pos, MineState state, PersonStatus status, FireExtinguishingSkill skill) {
		super(pos, state, status);
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
	public void update(MineObjectScheduler scheduler, Random rand, MineState next) {
		FirePerson person = new FirePerson(this, next);
		
		// find the current LayoutAtom
		LayoutAtom currAtom = this.getState().getClosestLayoutAtom(this.getPosition());
		
		// NO DAMAGE OR REST FOR FIREPEOPLE (FOR NOW)
		
		// MOVE AROUND:
		// FirePeople do not care about evacuated atoms
		// thus just move toward the target atoms
		
		// move along the shortest path to any target
		Integer layoutId = scheduler.getLayoutObject(this);
		if(layoutId != null) {
			LayoutObject obj = this.getState().getObject(LayoutObject.class, layoutId);
			Path path = currAtom.shortestPathTo(obj.getAtoms(), new HashSet<Integer>());
			Position newPos = this.moveAlongPath(path, currAtom);
			person.setPosition(newPos);
		}
		
		// TO DO: actively go around the LayoutObject looking for more Fires to smother
		
		// start extinguishing fires when stationary
		if(this.getPosition().getXYZ().equals(person.getPosition().getXYZ()))
			person.extinguishFires();
	}
	
	private void extinguishFires() {
		
		// find the fires around the FirePerson
		Set<Fire> fires = this.getState().getObjectsInRadius(Fire.class, this.getPosition(), skill.getRadius());
		
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
