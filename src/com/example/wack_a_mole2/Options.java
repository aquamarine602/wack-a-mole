package com.example.wack_a_mole2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class Options extends Activity implements OnClickListener {

	private void setUpSpinner() {
		Spinner mole_sp = (Spinner)findViewById(R.id.spNumMoles);
		String [] mole_ar = {"3", "4", "5", "6", "7", "8"};
		ArrayAdapter<String> mole_ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mole_ar);
		mole_sp.setAdapter(mole_ad);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		setUpSpinner();
		Button play = (Button)findViewById(R.id.pgButton);
		play.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() != R.id.pgButton) {
			return;
		} else {
			Intent intent = new Intent(this, Game.class);
			EditText name_tx = (EditText)findViewById(R.id.etName);
			RadioButton easy = (RadioButton)findViewById(R.id.rbEasy);
			RadioButton medium = (RadioButton)findViewById(R.id.rbMedium);
			RadioButton hard = (RadioButton)findViewById(R.id.rbHard);
			SeekBar durationsb = (SeekBar)findViewById(R.id.sbDuration);
			Spinner num_molessp = (Spinner)findViewById(R.id.spNumMoles);
			
			int difficulty = 0;
			if (easy.isChecked()) {
				difficulty = 3;
			} else if (medium.isChecked()) {
				difficulty = 2;
			} else if (hard.isChecked()) {
				difficulty = 1;
			}
			
			String name = name_tx.getText().toString();
			int duration = durationsb.getProgress();
			int num_moles = num_molessp.getSelectedItemPosition()+3;
			
			saveSettingsIntent(difficulty, name, num_moles, duration, intent);
			startActivity(intent);
		}
	}
	
	private void saveSettingsIntent(int difficulty, String name, int num_moles, int duration, Intent intent) {
		intent.putExtra("name", name);
		intent.putExtra("difficulty", difficulty);
		intent.putExtra("moles", num_moles);
		intent.putExtra("duration", duration);
	}

}
