package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Random;

public interface MineObject {
	
	public Integer getId();
	public void update(MineObjectScheduler scheduler, Random rand, MineState next);
	public String toJsonGui();
}
