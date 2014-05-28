package role;

import datastructure.RoleAddress;
import message.AcceptedMessage;
import message.PaxosMessage;
import runtime.GlobalConfig;

public class Learner extends PaxosRole {

    public Learner(RoleAddress myAddress) {
        super(myAddress);
    }

	public void handleMessage(PaxosMessage message) {
		super.handleMessage(message);
		assert(message instanceof AcceptedMessage);
		synchronized (System.out) {
			System.out.println("Learner " + GlobalConfig.INSTANCE.getCurrentNode() + " get the Paxos value: " + ((AcceptedMessage) message).getPaxosValue().getValue());
			System.out.flush();
		}
	}

}
