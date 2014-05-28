package message;

import datastructure.ProposalNumber;
import datastructure.RoleAddress;

public class PrepareMessage extends PaxosMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5290439593924074528L;
	
	private ProposalNumber proposalNumber;
	
	public PrepareMessage(ProposalNumber proposalNumber, RoleAddress senderAddress, RoleAddress receiverAddress) {
		super(senderAddress, receiverAddress);
		this.proposalNumber = proposalNumber;
	}
	
	public ProposalNumber getProposalNumber() {
		return proposalNumber;
	}

	public String messageBodyToString() {
		return String.format("Prepare(%d)", this.proposalNumber.getNumberValue());
	}
}
