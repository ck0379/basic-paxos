package message;

import datastructure.PaxosValue;
import datastructure.ProposalNumber;
import datastructure.RoleAddress;

public class AcceptedMessage extends PaxosMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1309580691380441299L;

	private ProposalNumber proposalNumber;
	private PaxosValue paxosValue;

	public AcceptedMessage(ProposalNumber proposalNumber, PaxosValue value, RoleAddress senderAddress, RoleAddress receiverAddress) {
		super(senderAddress, receiverAddress);
		this.proposalNumber = proposalNumber;
		this.paxosValue = value;
	}
	
	public ProposalNumber getProposalNumber() {
		return proposalNumber;
	}
	
	public PaxosValue getPaxosValue() {
		return paxosValue;
	}

	@Override
	public String messageBodyToString() {
		return String.format("Accepted(%d,%s)", this.proposalNumber.getNumberValue(), this.paxosValue.getValue());
	}
}
