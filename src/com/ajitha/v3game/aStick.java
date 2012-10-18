package com.ajitha.v3game;

import java.io.Serializable;

public class aStick implements Serializable {
	public int ID;
	public int p1ID ;
	public int p2ID ;
	public int strength ;
	
	public aStick(int iD, int p1id, int p2id, int strength) {
		this.ID = iD;
		this.p1ID = p1id;
		this.p2ID = p2id;
		this.strength = strength;
	}
	
	
}