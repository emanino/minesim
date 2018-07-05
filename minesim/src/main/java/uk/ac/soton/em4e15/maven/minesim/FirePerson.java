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
	public void update(Set<Action> actions, Random rand, MineState next) {
		FirePerson person = new FirePerson(this, next);
		
		// find the subset of Actions that pertain this FirePerson
		Set<Action> myActions = new HashSet<Action>();
		for(Action act: actions)
			if(act.getRecipientIds().contains(this.getId()))
				myActions.add(act);
		
		// find the current LayoutAtom
		LayoutAtom currAtom = this.getState().getClosestLayoutAtom(this.getPosition());
		
		// NO DAMAGE FOR FIREPEOPLE
		
		// MOVE AROUND:
		// FirePeople do not care about evacuated atoms
		// thus just move toward the target atom(s)
			
		// find the target(s)
		Set<Integer> targets = new HashSet<Integer>();
		for(Action act: myActions)
			if(act instanceof MoveAction)
				targets.add(((MoveAction) act).getTargetId());
		
		// move along the shortest path to the targets
		if(targets.size() > 0) {
			Path path = currAtom.shortestPathTo(targets, new HashSet<Integer>());
			Position newPos = this.moveAlongPath(path, currAtom);
			person.setPosition(newPos);
		}
		
		// start extinguishing fires when stationary
		if(this.getPosition().getXYZ().equals(person.getPosition().getXYZ()))
			person.extinguishFires(); 
	}
	
	private void extinguishFires() {
		
		// find the fires around the FirePerson
		Set<Fire> fires = new HashSet<Fire>();
		for(AtomObject obj: this.getState().getObjectsInRadius(this.getPosition(), skill.getRadius()))
			if(obj instanceof Fire)
				fires.add((Fire) obj);
		
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
