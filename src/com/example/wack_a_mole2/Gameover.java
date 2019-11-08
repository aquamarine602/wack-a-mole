package com.example.wack_a_mole2;

import android.app.Activity;
import android.os.Bundle;
import java.io.*;
import android.content.Intent;
import android.view.View;
import android.view.View.*;
import android.widget.*;

@SuppressWarnings("unused")
public class Gameover extends Activity implements OnClickListener{
	
	String player_name;
	int whacks;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
	}
	
	// save player's score into the specified fileoutputstream
	private void writeToFOS(FileOutputStream fos) {
		
	}
	
	// save player's score into internal memory
	private void saveHighScoreInternalFile() {
		
	}
	
	// save score to sd card
	private void saveHighScoreSD() {
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.buttonPlay) {
			Intent startover = new Intent(this, Game.class);
			startActivity(startover);
			finish();		// closes activity permanently
		} else if (v.getId() == R.id.buttonScores) {
			Intent highscores = new Intent(this, Highscores.class);
			startActivity(highscores);
			finish();
		}
	}
}
