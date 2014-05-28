package runtime;

import datastructure.PaxosValue;
import datastructure.RoleAddress;
import role.Acceptor;
import role.Learner;
import role.Proposer;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
	
	public static void main(String[] args) throws IOException {
        GlobalConfig.INSTANCE.init(Integer.parseInt(args[0]), ConnectionProtocol.TCP_CONNECTION);
        Proposer proposer = new Proposer(GlobalConfig.INSTANCE.getCurrentRoleAddressByRoleClass(Proposer.class));
        Acceptor acceptor = new Acceptor(GlobalConfig.INSTANCE.getCurrentRoleAddressByRoleClass(Acceptor.class));
        Learner learner = new Learner(GlobalConfig.INSTANCE.getCurrentRoleAddressByRoleClass(Learner.class));
        proposer.start();
        acceptor.start();
        learner.start();
        if (args.length == 2) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            proposer.initiatePaxos(new PaxosValue(args[1]));
        }
    }
}
