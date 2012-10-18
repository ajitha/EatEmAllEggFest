package com.ajitha.v3game;

import java.io.Serializable;


public class InitProperties implements Serializable{
	public int InitStars = 0;
	public int InitGrenade = 0;
	public int InitThunde = 0;
	public int InitCharges = 0;
	public int InitFlame = 0;
	public int InitBridge = 0;
	
	public boolean grenadeBuyable = false;
	public boolean ThunderBuyable = false;
	public boolean ChargesBuyable= false;
	public boolean FlameBuyable = false;
	
	
	
	public InitProperties(){
		InitBridge = 0;
		InitStars = 0;
		InitGrenade = 0;
		InitThunde = 0;
		InitCharges = 0;
		InitFlame = 0;
		grenadeBuyable = false;
		ThunderBuyable = false;
		ChargesBuyable= false;
		FlameBuyable = false;
	}
	
	
}
