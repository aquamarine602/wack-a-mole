package com.example.wack_a_mole2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import java.io.*;
import java.util.*;
import android.widget.*;

@SuppressWarnings("unused")
public class Highscores extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		
		// load scores
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			loadHighScoresSD();
		} else {
			loadHighScoresInternalFile();
		}
	}

	// load scores from internal file
	private void loadHighScoresInternalFile() {
		try {
			FileInputStream fis = openFileInput("HighScores.txt");
			readScoresFIS(fis);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
	}
	
	// load scores from sd card
	private void loadHighScoresSD() {
		try {
			File privateSD = getExternalFilesDir(null);
			File file = new File(privateSD, "HighScoresSD.txt");
			FileInputStream fis = new FileInputStream(file);
			readScoresFIS(fis);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
	}
	
	// this method will load the scores from the fileinputstream
	// sort them by score and select the top 5 to put in the
	// high scores list on the screen
	private void readScoresFIS(FileInputStream fis) {
		
		// create input stream reader
		InputStreamReader isr = new InputStreamReader(fis);
		// get a handle to the two text fields on the screen
		TextView tvName = (TextView)findViewById(R.id.tvPlayerName);
		TextView tvScore = (TextView)findViewById(R.id.tvPlayerScore);
		// figure out which character is an endline on the current device
		String endline = System.getProperty("line.seperator");
		
		LinkedList<String> player_names = new LinkedList<String>();
		LinkedList<Integer> player_scores = new LinkedList<Integer>();
		
		try {
			
			// user bufferedreader to allow for easy reading of the file data
			BufferedReader buffread = new BufferedReader(isr);
			// read in the data line by line
			String name = buffread.readLine();
			
			while (name != null) {
				String strScore = buffread.readLine();
				int score = Integer.parseInt(strScore);
				ListIterator<Integer> scoreIter = player_scores.listIterator();
				ListIterator<String> playerIter = player_names.listIterator();
				while (scoreIter.hasNext()) {
					// get the next integer and also iterate to the next name
					Integer thisScore = scoreIter.next();
					playerIter.next();
					// if new score is larger than this one
					if (score >= thisScore) {
						break;	// stop looking, we know where to insert
					}
				}
				
				// if there are any scores at all, we need to rewind both iterators
				// by one to insert in the correct spot
				if (player_scores.size() > 0) {
					scoreIter.previous();
					playerIter.previous();
				}
				
				// add this score and name into the linked lists in sorted order
				scoreIter.add(new Integer(score));
				playerIter.add(name);
				
				// read in the next line in the file
				name = buffread.readLine();
			}
			
			// close the bufferedreader object
			buffread.close();
			
		} catch (Exception e) {
			//file handling error
		}
		
		// now, iterate again over the sorted list
		ListIterator<Integer> scoreIter = player_scores.listIterator();
		ListIterator<String> playerIter = player_names.listIterator();
		
		//these strings will contain the sorted scores and corresponding names
		String sortedNames = "";
		String sortedScores = "";
		
		int numPresent = 0;
		while (scoreIter.hasNext()) {
			// get the score and corresponding name
			Integer score = scoreIter.next();
			String name = playerIter.next();
			
			// add score and name plus a newline to respective text fields
			sortedScores += score.toString() + "\n";
			sortedNames += name + "\n";
			
			// count scores and stop after top 5
			numPresent++;
			if (numPresent >= 5) {
				break;
			}
			
			// put the stored names in the name textview
			tvName.setText(sortedNames);
			// put the sorted scores in the score textview
			tvScore.setText(sortedScores);
		}
		
		
		
	}

}
