package com.ajitha.v3game;

import java.io.Serializable;
import java.util.ArrayList;

public class aPoint implements Serializable{
	
	public aPoint(float x, float y){
		this.x = x;
		this.y = y;
	}
	public int ID ;
	
	public float x,y,centerX,centerY;
	public int visitable = 0 ;
	
	public boolean startable = true;
	public boolean visited = false;
	public boolean mustStart = false;
	public boolean currentPos = false;
	
	public boolean hasStar = false;
	public boolean hasCharges = false;
	public boolean hasFlame = false;
	public boolean hasGrenade = false;
	public boolean hasThunder = false;
	
	public boolean selected = false;
	public boolean init = false;
	public boolean SLinit = false;
	
	public ArrayList<Integer> connectedaPoints = new ArrayList<Integer>();
	public ArrayList<Integer> connectedaSticks = new ArrayList<Integer>();
}