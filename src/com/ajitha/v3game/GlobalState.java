package com.ajitha.v3game;

import android.app.Application;

public class GlobalState extends Application {
	private String filename;

	public String getTestMe() {
		return filename;
	}

	public void setTestMe(String testMe) {
		this.filename = testMe;
	}
	
	
}