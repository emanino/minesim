package uk.ac.soton.em4e15.maven.minesim;
//
import java.util.Set;

public class MoveMicroAction implements MicroAction {
	
	private Set<Integer> recipients;
	private Integer target;
	
	MoveMicroAction(Set<Integer> recipients, Integer target) {
		this.recipients = recipients;
		this.target = target;
	}

	@Override
	public Set<Integer> getRecipientIds() {
		return recipients;
	}

	public Integer getTargetId() {
		return target;
	}
}
