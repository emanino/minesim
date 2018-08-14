package uk.ac.soton.em4e15.maven.minesim.useractions;

public class FullEvacuateUserAction implements UserAction {
	
	public FullEvacuateUserAction() {}

	@Override
	public int hashCode() {
		return 31;
	}
	
	public String toJson() {
		return "{\"code\":1}";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}	
	
}
