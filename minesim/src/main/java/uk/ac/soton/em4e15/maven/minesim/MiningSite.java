package uk.ac.soton.em4e15.maven.minesim;

import java.util.Random;

public class MiningSite implements AtomObject {
	
	private Integer id;
	private Position pos;
	private MineState state;
	
	MiningSite(Position pos, MineState state) {
		id = state.getNextId();
		this.pos = pos;
		this.state = state;
		state.addNew(this);
	}
	
	MiningSite(MiningSite site, MineState next) {
		id = site.getId();
		pos = site.getPosition();
		state = next;
		state.addOld(this);
	}

	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public void update(MineObjectScheduler scheduler, Random rand, MineState next) {
		new MiningSite(this, next);
	}

	@Override
	public String toJsonGui() {
		return "{\"type\":\"site\",\"name\":\"S"+ id + "\",\"c\":" + pos.toJsonGui() + "}";
	}
}
