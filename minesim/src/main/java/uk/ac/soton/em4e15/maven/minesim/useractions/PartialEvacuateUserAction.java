package uk.ac.soton.em4e15.maven.minesim.useractions;

public class PartialEvacuateUserAction implements UserAction {
	
	private Integer layoutId;
	
	public PartialEvacuateUserAction(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
	public Integer getLayoutId() {
		return layoutId;
	}
}
