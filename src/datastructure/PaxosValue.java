package datastructure;

import java.io.Serializable;

public class PaxosValue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1744215336341276159L;
	
	private String paxosString = null;

	private PaxosValue() {
		
	}
	
	public PaxosValue(String paxosValue) {
		this.paxosString = paxosValue;
	}
	
	public String getValue() {
		return this.paxosString;
	}

    public String toString() {
        return this.paxosString!=null ? this.paxosString : "RequestLeader";
    }
}
