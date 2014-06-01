package runtime;

import datastructure.RoleAddress;
import role.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum GlobalConfig {
    INSTANCE;
    private int currentNodeNumber;
    private ConnectionProtocol connectionProtocol;
    public int ports[][] = {{10001, 10002, 10003}, {20001, 20002, 20003}, {30001, 30002, 30003}};//, {40001, 40002, 40003}, {50001, 50002, 50003}};
    private InetAddress ip;

    public void init(int currentNode, ConnectionProtocol connectionProtocol) {

        this.currentNodeNumber = currentNode;
        this.connectionProtocol = connectionProtocol;
        try {
            this.ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public RoleAddress getCurrentRoleAddressByRoleClass(Class paxosRoleClass) {
        if (paxosRoleClass.equals(Proposer.class)) {
            return (new RoleAddress(this.ip, this.ports[this.currentNodeNumber][0]));
        } else if (paxosRoleClass.equals(Acceptor.class)) {
            return (new RoleAddress(this.ip, this.ports[this.currentNodeNumber][1]));
        } else if (paxosRoleClass.equals(Learner.class)) {
            return (new RoleAddress(this.ip, this.ports[this.currentNodeNumber][2]));
        } else {
            return null;
        }
    }

    public RoleAddress getCurrentRoleAddress(PaxosRole paxosRole) {
        return this.getCurrentRoleAddressByRoleClass(paxosRole.getClass());
    }

    public RoleAddress[] getAllProposerAddresses() {
        RoleAddress[] allProposerAddresses = new RoleAddress[ports.length];
        int i;
        for (i = 0; i < ports.length; i++) {
            allProposerAddresses[i] = new RoleAddress(this.ip, this.ports[i][0]);
        }
        return allProposerAddresses;
    }

    public RoleAddress[] getAllAcceptorAddresses() {
        RoleAddress[] allAcceptorAddresses = new RoleAddress[ports.length];
        int i;
        for (i = 0; i < ports.length; i++) {
            allAcceptorAddresses[i] = new RoleAddress(this.ip, this.ports[i][1]);
        }
        return allAcceptorAddresses;
    }

    public RoleAddress[] getAllLearnerAddresses() {
        RoleAddress[] allLearnerAddresses = new RoleAddress[ports.length];
        int i;
        for (i = 0; i < ports.length; i++) {
            allLearnerAddresses[i] = new RoleAddress(this.ip, this.ports[i][2]);
        }
        return allLearnerAddresses;
    }

    public int getNumberOfQuorum() {
        return ports.length / 2 + 1;
    }

    public int whichNodeIsThisAddress(RoleAddress roleAddress) {
        int nodeNumber = -1;
        for (int i = 0; i < ports.length; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.ports[i][j] == roleAddress.getPortNumber()) {
                    nodeNumber = i;
                }
            }
        }
        return nodeNumber;
    }

    public Class whichRoleIsThisAddress(RoleAddress roleAddress) {

        switch (roleAddress.getPortNumber() % 10000) {
            case 1:
                return Proposer.class;
            case 2:
                return Acceptor.class;
            case 3:
                return Learner.class;
            default:
                // What kind of exception to throw?
                return PaxosRole.class;
        }
    }

    public int getClientListenPortNumber() {

        return (this.currentNodeNumber + 1) * 10000;
    }

    public int getCurrentNodeNumber() {
        return currentNodeNumber;
    }

    public int getMaximumNodeNumber() {
        return 10;
    }

    public ConnectionProtocol getConnectionProtocol() {
        return connectionProtocol;
    }
}


