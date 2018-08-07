package uk.ac.soton.em4e15.maven.minesim.observresult;

import uk.ac.soton.em4e15.maven.minesim.Position;

public class ObservedPosition implements ObservationValue {
	
	private Position pos;

	public ObservedPosition(Position p) {
		pos = new Position(p.getX(), p.getY(), p.getZ());
	}
	
	@Override
	public Position getValue() {
		// TODO Auto-generated method stub
		return pos;
	}
	
	@Override
	public String toJsonGui() {
		return pos.toJsonGui();
	}
}
