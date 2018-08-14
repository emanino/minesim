package uk.ac.soton.em4e15.maven.minesim.useractions;

public class PartialEvacuateUserAction implements UserAction {
	
	private Integer layoutId;
	
	public PartialEvacuateUserAction(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
	public Integer getLayoutId() {
		return layoutId;
	}
	
	public String toJson() {
		return "{\"code\":2,\"param\":\""+layoutId+"\"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((layoutId == null) ? 0 : layoutId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartialEvacuateUserAction other = (PartialEvacuateUserAction) obj;
		if (layoutId == null) {
			if (other.layoutId != null)
				return false;
		} else if (!layoutId.equals(other.layoutId))
			return false;
		return true;
	}
}
