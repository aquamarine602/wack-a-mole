package com.example.wack_a_mole2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

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
		
		Bundle bun = getIntent().getExtras();
		player_name = bun.getString("name");
		whacks = bun.getInt("score");
		
		TextView tvhits = (TextView)findViewById(R.id.tvHits);
		TextView tvgo = (TextView)findViewById(R.id.tvGameOver);
		tvhits.setText("You hit " + whacks + " times!");
		tvgo.setText("Game over, " + player_name + "!");
		
		Button play = (Button)findViewById(R.id.buttonPlay);
		Button scores = (Button)findViewById(R.id.buttonScores);
		play.setOnClickListener(this);
		scores.setOnClickListener(this);
		
		// checks if device has SD card
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			saveHighScoreSD();
		} else {
			saveHighScoreInternalFile();
		}
	}
	
	// save player's score into the specified fileoutputstream
	private void writeToFOS(FileOutputStream fos) {
		try {
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			String endLine = System.getProperty("line.separator");
			osw.write(player_name + "\n");
			String score = whacks + "\n";
			osw.write(score);
			osw.flush();
			osw.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	// save player's score into internal memory
	private void saveHighScoreInternalFile() {
		try {
			FileOutputStream fos = openFileOutput("HighScores.txt", MODE_APPEND);
			writeToFOS(fos);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
	
	// save score to sd card
	private void saveHighScoreSD() {
		try {
			File privateSD = getExternalFilesDir(null);
			File sdscores = new File(privateSD, "HighScoresSD.txt");
			FileOutputStream fos = new FileOutputStream(sdscores, true);
			writeToFOS(fos);
		} catch (Exception e) {
			//e.printStackTrace();
		}
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
