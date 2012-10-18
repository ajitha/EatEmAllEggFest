package com.ajitha.v3game;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class props extends Activity{
	public InitProperties ip = new InitProperties();
	GlobalState gs;
	private EditText InitStars;
	private EditText InitGrenade;
	private EditText InitThunder;
	private EditText InitCharges;
	private EditText InitFlame;
	private CheckBox grenadeBuyable;
	private CheckBox ThunderBuyable;
	private CheckBox ChargesBuyable;
	private CheckBox FlameBuyable;
	private File file;
	private EditText InitBridge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.props);
		gs = (GlobalState) getApplication();

		InitStars = (EditText) findViewById(R.id.editText1);
		InitGrenade = (EditText) findViewById(R.id.editText2);
		InitThunder = (EditText) findViewById(R.id.editText3);
		InitCharges = (EditText) findViewById(R.id.editText4);
		InitFlame = (EditText) findViewById(R.id.editText5);
		InitBridge = (EditText) findViewById(R.id.editText6);

		grenadeBuyable = (CheckBox) findViewById(R.id.checkBox1);
		ThunderBuyable = (CheckBox) findViewById(R.id.checkBox2);
		ChargesBuyable = (CheckBox) findViewById(R.id.checkBox3);
		FlameBuyable = (CheckBox) findViewById(R.id.checkBox4);

		grenadeBuyable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ip.grenadeBuyable = true;
				}
				else{
					ip.grenadeBuyable = false;
				}

			}
		});

		FlameBuyable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ip.FlameBuyable = true;
				}
				else{
					ip.FlameBuyable = false;
				}

			}
		});
		ThunderBuyable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ip.ThunderBuyable = true;
				}
				else{
					ip.ThunderBuyable = false;
				}

			}
		});
		ChargesBuyable.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					ip.ChargesBuyable = true;
				}
				else{
					ip.ChargesBuyable = false;
				}

			}
		});
		Button b = (Button) findViewById(R.id.button1);
		Button b2 = (Button) findViewById(R.id.button2);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ip.InitStars = Integer.parseInt(InitStars.getText().toString());
				ip.InitGrenade = Integer.parseInt(InitGrenade.getText().toString());
				ip.InitThunde = Integer.parseInt(InitThunder.getText().toString());
				ip.InitCharges = Integer.parseInt(InitCharges.getText().toString());
				ip.InitFlame = Integer.parseInt(InitFlame.getText().toString());
				ip.InitBridge = Integer.parseInt(InitBridge.getText().toString());
//				ip.grenadeBuyable = grenadeBuyable.isChecked();
//				ip.ThunderBuyable = ThunderBuyable.isChecked();
//				ip.ChargesBuyable = ChargesBuyable.isChecked();
//				ip.FlameBuyable = FlameBuyable.isChecked();
				
				
				String filename = gs.getTestMe();
				
				byte[] baInit = toByteArray(ip);
				savetosd(filename+"-init", baInit);
				
				Toast.makeText(props.this, "Files successfull saved!!", Toast.LENGTH_LONG).show();
				Intent i = new Intent(props.this, V3Activity.class);
				startActivity(i);
			}
			
			
		});
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ip.InitStars = Integer.parseInt(InitStars.getText().toString());
				ip.InitGrenade = Integer.parseInt(InitGrenade.getText().toString());
				ip.InitThunde = Integer.parseInt(InitThunder.getText().toString());
				ip.InitCharges = Integer.parseInt(InitCharges.getText().toString());
				ip.InitFlame = Integer.parseInt(InitFlame.getText().toString());
				ip.InitBridge = Integer.parseInt(InitBridge.getText().toString());
//				ip.grenadeBuyable = grenadeBuyable.isChecked();
//				ip.ThunderBuyable = ThunderBuyable.isChecked();
//				ip.ChargesBuyable = ChargesBuyable.isChecked();
//				ip.FlameBuyable = FlameBuyable.isChecked();
//				
				
				String filename = gs.getTestMe();
				
				byte[] baInit = toByteArray(ip);
				savetosd(filename+"-init", baInit);
				
				Toast.makeText(props.this, "Files successfull saved!!", Toast.LENGTH_LONG).show();
				Intent i = new Intent(props.this, Level2.class);
				startActivity(i);
			}
			
			
		});
		
	}
	
	public void savetosd(String filename,byte[] ba){
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/dir1/dir2");
		dir.mkdirs();
		file = new File(dir, filename);
		
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(ba);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public byte[] toByteArray (Object obj)
	{
	  byte[] bytes = null;
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos); 
	    oos.writeObject(obj);
	    oos.flush(); 
	    oos.close(); 
	    bos.close();
	    bytes = bos.toByteArray ();
	  }
	  catch (IOException ex) {
		  Log.e("serializeObject", "error", ex); 
	  }
	  return bytes;
	}
}
