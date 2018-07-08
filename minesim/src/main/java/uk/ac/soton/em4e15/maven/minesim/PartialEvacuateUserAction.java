package uk.ac.soton.em4e15.maven.minesim;

public class PartialEvacuateUserAction implements UserAction {
	
	private Integer layoutId;
	
	PartialEvacuateUserAction(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
	public Integer getLayoutId() {
		return layoutId;
	}
}
