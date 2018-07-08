package uk.ac.soton.em4e15.maven.minesim;

public class SendFireTeamUserAction {
	
	private Integer layoutId;
	
	SendFireTeamUserAction(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
	public Integer getLayoutId() {
		return layoutId;
	}
}
