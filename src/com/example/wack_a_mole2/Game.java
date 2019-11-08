package com.example.wack_a_mole2;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

public class Game extends Activity implements OnClickListener {
	
	ArrayList<Integer> myButtonIDs = new ArrayList<Integer>();
	protected Handler taskHandler = new Handler();
	protected Boolean isComplete = false;
	Button currentMole;
	long startTime = System.currentTimeMillis();
	int numWhacks = 0;
	String playerName = "Default";
	int difficultyLevel = 2;    
	int numMoles = 8;           
	int duration = 20;			
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		// data coming from prefs, not intent
		/** Bundle bun = getIntent().getExtras();
		playerName = bun.getString("name");
		difficultyLevel = bun.getInt("difficulty");
		numMoles = bun.getInt("num_moles");
		duration = bun.getInt("duration"); **/
		
		SharedPreferences prefs = getSharedPreferences("WhackSettings", MODE_PRIVATE);
		playerName = prefs.getString("name", playerName);
		difficultyLevel = prefs.getInt("difficulty", difficultyLevel);
		numMoles = prefs.getInt("moles", numMoles);
		duration = prefs.getInt("duration", duration);
		
		TextView name = (TextView)findViewById(R.id.tvName);
		name.setText(playerName);
		
		initButtons();
		setNewMole();
		setTimer(difficultyLevel*1000);
	}

	public void onClick(View v) {
		if (isComplete = true) {
			return;
		}
		if (v == currentMole) {
			numWhacks++;
			TextView tv = (TextView)findViewById(R.id.tvNumWhacks);
			tv.setText("Number of Whacks: " + numWhacks);
			setNewMole();
		}
	}

	public void gameOver(){
		isComplete = true;
		TextView tv = (TextView)findViewById(R.id.tvNumWhacks);
		tv.setText("Game OVer! Score: " + numWhacks);
	}

	public void setNewMole() {
		Random generator = new Random();
		int randomItem = generator.nextInt(numMoles);
		int newButtonId = myButtonIDs.get(randomItem);
		if (currentMole != null)
			currentMole.setVisibility(View.INVISIBLE);
		Button newMole = (Button)findViewById(newButtonId);
		newMole.setVisibility(View.VISIBLE);
		currentMole = newMole;
	}

	
	public void initButtons() {
		ViewGroup group = (ViewGroup)findViewById(R.id.GameLayout);
		View v;

		for(int i = 0; i < group.getChildCount(); i++) {
			v = group.getChildAt(i);
			if (v instanceof Button)
			{
				v.setOnClickListener(this); 

				if (!isComplete)	
				{
					myButtonIDs.add(v.getId());	
					v.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	
	protected void setTimer( long time ) {
		final long elapse = time;
		Runnable t = new Runnable() {
			public void run() {
				onTimerTask();
				if( !isComplete ) {
					taskHandler.postDelayed( this, elapse );
				}
			}
		};
		taskHandler.postDelayed( t, elapse );
		}

	protected void onTimerTask() {
		long endTime = startTime + (duration * 1000);
		if (endTime > System.currentTimeMillis()) {
			setNewMole();
		} else {
			gameOver();	
		}
	}

}



