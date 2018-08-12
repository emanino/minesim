package uk.ac.soton.em4e15.maven.minesim;

public class GasLeakStatus {
	
	boolean isBigLeak;
	
	GasLeakStatus(boolean isBigLeak) {
		this.isBigLeak = isBigLeak;
	}
	
	public boolean isBigLeak() {
		return isBigLeak;
	}
}
