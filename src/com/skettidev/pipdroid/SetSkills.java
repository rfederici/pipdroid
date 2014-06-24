package com.skettidev.pipdroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SetSkills extends Activity implements OnClickListener{
	
	private SoundPool sp;
	private Typeface  font;
	private TextView  barterTEXT, big_gunsTEXT, energyTEXT, explosivesTEXT, lockpickTEXT, medicineTEXT, meleeTEXT,
					  repairTEXT, scienceTEXT, small_gunsTEXT, sneakTEXT, speechTEXT, unarmedTEXT;
	private ImageView bart_up, bgns_up, nrg_up, expl_up, lock_up, medi_up, mlee_up, rpar_up, sci_up, sgns_up, snek_up, spch_up, uarm_up,
					  bart_down, bgns_down, nrg_down, expl_down, lock_down, medi_down, mlee_down, rpar_down, sci_down, sgns_down, snek_down, spch_down, uarm_down;
	private TextView  barterSTAT, big_gunsSTAT, energySTAT, explosivesSTAT, lockpickSTAT, medicineSTAT, meleeSTAT,
					  repairSTAT, scienceSTAT, small_gunsSTAT, sneakSTAT, speechSTAT, unarmedSTAT, REMAINING;
	private Button    finished;
	private int       aud_selection;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_skills);
		
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		aud_selection = sp.load(this, R.raw.selection, 1);
		
		font = Typeface.createFromAsset(getAssets(), "Monofonto.ttf");
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		barterTEXT = (TextView) findViewById(R.id.text_barter);
			barterTEXT.setOnClickListener(this);
			barterTEXT.setTypeface(font);
		barterSTAT = (TextView) findViewById(R.id.barter_stat);
			barterSTAT.setOnClickListener(this);
			barterSTAT.setTypeface(font);
		bart_up = (ImageView) findViewById(R.id.bart_inc);
			bart_up.setOnClickListener(new StatIncreaseListener(barterSTAT));
		bart_down = (ImageView) findViewById(R.id.bart_dec);
			bart_down.setOnClickListener(new StatDecreaseListener(barterSTAT));
			
		big_gunsTEXT = (TextView) findViewById(R.id.text_big_guns);
			big_gunsTEXT.setOnClickListener(this);
			big_gunsTEXT.setTypeface(font);
		big_gunsSTAT = (TextView) findViewById(R.id.big_guns_stat);
			big_gunsSTAT.setOnClickListener(this);
			big_gunsSTAT.setTypeface(font);
		bgns_up = (ImageView) findViewById(R.id.bgns_inc);
			bgns_up.setOnClickListener(new StatIncreaseListener(big_gunsSTAT));
		bgns_down = (ImageView) findViewById(R.id.bgns_dec);
			bgns_down.setOnClickListener(new StatDecreaseListener(big_gunsSTAT));
			
		explosivesTEXT = (TextView) findViewById(R.id.text_explosives);
			explosivesTEXT.setOnClickListener(this);
			explosivesTEXT.setTypeface(font);
		explosivesSTAT = (TextView) findViewById(R.id.explosives_stat);
			explosivesSTAT.setOnClickListener(this);
			explosivesSTAT.setTypeface(font);
		expl_up = (ImageView) findViewById(R.id.expl_inc);
			expl_up.setOnClickListener(new StatIncreaseListener(explosivesSTAT));
		expl_down = (ImageView) findViewById(R.id.expl_dec);
			expl_down.setOnClickListener(new StatDecreaseListener(explosivesSTAT));
			
		energyTEXT = (TextView) findViewById(R.id.text_energy_weapons);
			energyTEXT.setOnClickListener(this);
			energyTEXT.setTypeface(font);
		energySTAT = (TextView) findViewById(R.id.energy_weapons_stat);
			energySTAT.setOnClickListener(this);
			energySTAT.setTypeface(font);
		nrg_up = (ImageView) findViewById(R.id.nrg_inc);
			nrg_up.setOnClickListener(new StatIncreaseListener(energySTAT));
		nrg_down = (ImageView) findViewById(R.id.nrg_dec);
			nrg_down.setOnClickListener(new StatDecreaseListener(energySTAT));
			
		lockpickTEXT = (TextView) findViewById(R.id.text_lockpick);
			lockpickTEXT.setOnClickListener(this);
			lockpickTEXT.setTypeface(font);
		lockpickSTAT = (TextView) findViewById(R.id.lockpick_stat);
			lockpickSTAT.setOnClickListener(this);
			lockpickSTAT.setTypeface(font);
		lock_up = (ImageView) findViewById(R.id.lock_inc);
			lock_up.setOnClickListener(new StatIncreaseListener(lockpickSTAT));
		lock_down = (ImageView) findViewById(R.id.lock_dec);
			lock_down.setOnClickListener(new StatDecreaseListener(lockpickSTAT));
			
		medicineTEXT = (TextView) findViewById(R.id.text_medicine);
			medicineTEXT.setOnClickListener(this);
			medicineTEXT.setTypeface(font);
		medicineSTAT = (TextView) findViewById(R.id.medicine_stat);
			medicineSTAT.setOnClickListener(this);
			medicineSTAT.setTypeface(font);
		medi_up = (ImageView) findViewById(R.id.medi_inc);
			medi_up.setOnClickListener(new StatIncreaseListener(medicineSTAT));
		medi_down = (ImageView) findViewById(R.id.medi_dec);
			medi_down.setOnClickListener(new StatDecreaseListener(medicineSTAT));
		
		meleeTEXT = (TextView) findViewById(R.id.text_melee);
			meleeTEXT.setOnClickListener(this);
			meleeTEXT.setTypeface(font);
		meleeSTAT = (TextView) findViewById(R.id.melee_stat);
			meleeSTAT.setOnClickListener(this);
			meleeSTAT.setTypeface(font);
		mlee_up = (ImageView) findViewById(R.id.mlee_inc);
			mlee_up.setOnClickListener(new StatIncreaseListener(meleeSTAT));
		mlee_down = (ImageView) findViewById(R.id.mlee_dec);
			mlee_down.setOnClickListener(new StatDecreaseListener(meleeSTAT));
			
		repairTEXT = (TextView) findViewById(R.id.text_repair);
			repairTEXT.setOnClickListener(this);
			repairTEXT.setTypeface(font);
		repairSTAT = (TextView) findViewById(R.id.repair_stat);
			repairSTAT.setOnClickListener(this);
			repairSTAT.setTypeface(font);
		rpar_up = (ImageView) findViewById(R.id.rpar_inc);
			rpar_up.setOnClickListener(new StatIncreaseListener(repairSTAT));
		rpar_down = (ImageView) findViewById(R.id.rpar_dec);
			rpar_down.setOnClickListener(new StatDecreaseListener(repairSTAT));
		
		scienceTEXT = (TextView) findViewById(R.id.text_science);
			scienceTEXT.setOnClickListener(this);
			scienceTEXT.setTypeface(font);
		scienceSTAT = (TextView) findViewById(R.id.science_stat);
			scienceSTAT.setOnClickListener(this);
			scienceSTAT.setTypeface(font);
		sci_up = (ImageView) findViewById(R.id.sci_inc);
			sci_up.setOnClickListener(new StatIncreaseListener(scienceSTAT));
		sci_down = (ImageView) findViewById(R.id.sci_dec);
			sci_down.setOnClickListener(new StatDecreaseListener(scienceSTAT));
			
		small_gunsTEXT = (TextView) findViewById(R.id.text_small_guns);
			small_gunsTEXT.setOnClickListener(this);
			small_gunsTEXT.setTypeface(font);
		small_gunsSTAT = (TextView) findViewById(R.id.small_guns_stat);
			small_gunsSTAT.setOnClickListener(this);
			small_gunsSTAT.setTypeface(font);
		sgns_up = (ImageView) findViewById(R.id.sgns_inc);
			sgns_up.setOnClickListener(new StatIncreaseListener(small_gunsSTAT));
		sgns_down = (ImageView) findViewById(R.id.sgns_dec);
			sgns_down.setOnClickListener(new StatDecreaseListener(small_gunsSTAT));
			
		sneakTEXT = (TextView) findViewById(R.id.text_sneak);
			sneakTEXT.setOnClickListener(this);
			sneakTEXT.setTypeface(font);
		sneakSTAT = (TextView) findViewById(R.id.sneak_stat);
			sneakSTAT.setOnClickListener(this);
			sneakSTAT.setTypeface(font);
		snek_up = (ImageView) findViewById(R.id.snek_inc);
			snek_up.setOnClickListener(new StatIncreaseListener(sneakSTAT));
		snek_down = (ImageView) findViewById(R.id.snek_dec);
			snek_down.setOnClickListener(new StatDecreaseListener(sneakSTAT));
			
		speechTEXT = (TextView) findViewById(R.id.text_speech);
			speechTEXT.setOnClickListener(this);
			speechTEXT.setTypeface(font);
		speechSTAT = (TextView) findViewById(R.id.speech_stat);
			speechSTAT.setOnClickListener(this);
			speechSTAT.setTypeface(font);
		spch_up = (ImageView) findViewById(R.id.spch_inc);
			spch_up.setOnClickListener(new StatIncreaseListener(speechSTAT));
		spch_down = (ImageView) findViewById(R.id.spch_dec);
			spch_down.setOnClickListener(new StatDecreaseListener(speechSTAT));
			
		unarmedTEXT = (TextView) findViewById(R.id.text_unarmed);
			unarmedTEXT.setOnClickListener(this);
			unarmedTEXT.setTypeface(font);
		unarmedSTAT = (TextView) findViewById(R.id.unarmed_stat);
			unarmedSTAT.setOnClickListener(this);
			unarmedSTAT.setTypeface(font);
		uarm_up = (ImageView) findViewById(R.id.uarm_inc);
			uarm_up.setOnClickListener(new StatIncreaseListener(unarmedSTAT));
		uarm_down = (ImageView) findViewById(R.id.uarm_dec);
			uarm_down.setOnClickListener(new StatDecreaseListener(unarmedSTAT));
			
		REMAINING = (TextView) findViewById(R.id.remaining_skillpoints);
		
		@SuppressWarnings("deprecation")
		Cursor cursor =  managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		int numContacts = cursor.getCount();
		
		SharedPreferences prefs = getSharedPreferences("SKILLS", 0);
		if (!prefs.contains("BARTER"))
		{
			REMAINING.setText(String.valueOf(numContacts));
			REMAINING.setTypeface(font);
			
			barterSTAT.setText("10");
			big_gunsSTAT.setText("10");
			energySTAT.setText("10");
			explosivesSTAT.setText("10");
			lockpickSTAT.setText("10");
			medicineSTAT.setText("10");
			meleeSTAT.setText("10");
			repairSTAT.setText("10");
			scienceSTAT.setText("10");
			small_gunsSTAT.setText("10");
			sneakSTAT.setText("10");
			speechSTAT.setText("10");
			unarmedSTAT.setText("10");
		}
		else
		{
			barterSTAT.setText(String.valueOf(prefs.getInt("BARTER", 10)));
			big_gunsSTAT.setText(String.valueOf(prefs.getInt("BIG_GUNS", 10)));
			energySTAT.setText(String.valueOf(prefs.getInt("ENERGY", 10)));
			explosivesSTAT.setText(String.valueOf(prefs.getInt("EXPLOSIVES", 10)));
			lockpickSTAT.setText(String.valueOf(prefs.getInt("LOCKPICK", 10)));
			medicineSTAT.setText(String.valueOf(prefs.getInt("MEDICINE", 10)));
			meleeSTAT.setText(String.valueOf(prefs.getInt("MELEE", 10)));
			repairSTAT.setText(String.valueOf(prefs.getInt("REPAIR", 10)));
			scienceSTAT.setText(String.valueOf(prefs.getInt("SCIENCE", 10)));
			small_gunsSTAT.setText(String.valueOf(prefs.getInt("SMALL_GUNS", 10)));
			sneakSTAT.setText(String.valueOf(prefs.getInt("SNEAK", 10)));
			speechSTAT.setText(String.valueOf(prefs.getInt("SPEECH", 10)));
			unarmedSTAT.setText(String.valueOf(prefs.getInt("UNARMED", 10)));
			
			int remaining = 130 + numContacts - (prefs.getInt("BARTER", 10) + prefs.getInt("BIG_GUNS", 10) + prefs.getInt("ENERGY", 10) + prefs.getInt("EXPLOSIVES", 10) +
					prefs.getInt("LOCKPICK", 10) + prefs.getInt("MEDICINE", 10) + prefs.getInt("MELEE", 10) + prefs.getInt("REPAIR", 10) + prefs.getInt("SCIENCE", 10) +
					prefs.getInt("SMALL_GUNS", 10) + prefs.getInt("SNEAK", 10) + prefs.getInt("SPEECH", 10) + prefs.getInt("UNARMED", 10));
			REMAINING.setText(String.valueOf(remaining));
			REMAINING.setTypeface(font);
		}
		
		finished = (Button) findViewById(R.id.finished_skills);
			finished.setOnClickListener(this);
		
	}

	public void onClick(View source) {
		
		sp.play(aud_selection, 1, 1, 1, 0, 1);
		
		// IMAGE CHANGES
		ImageView picture = (ImageView) findViewById(R.id.set_skills_image);
		
		if (source == barterTEXT || source == bart_down || source == bart_up || source == barterSTAT)
			picture.setImageResource(R.drawable.barter);
		else if (source == big_gunsTEXT || source == bgns_down || source == bgns_up || source == big_gunsSTAT)
			picture.setImageResource(R.drawable.big_guns);
		else if (source == energyTEXT || source == nrg_down || source == nrg_up || source == energySTAT)
			picture.setImageResource(R.drawable.energy);
		else if (source == explosivesTEXT || source == expl_down || source == expl_up || source == explosivesSTAT)
			picture.setImageResource(R.drawable.explosives);
		else if (source == lockpickTEXT || source == lock_down || source == lock_up || source == lockpickSTAT)
			picture.setImageResource(R.drawable.lockpick);
		else if (source == medicineTEXT || source == medi_down || source == medi_up || source == medicineSTAT)
			picture.setImageResource(R.drawable.medicine);
		else if (source == big_gunsTEXT || source == bgns_down || source == bgns_up || source == big_gunsSTAT)
			picture.setImageResource(R.drawable.big_guns);
		else if (source == meleeTEXT || source == mlee_down || source == mlee_up || source == meleeSTAT)
			picture.setImageResource(R.drawable.melee);
		else if (source == repairTEXT || source == rpar_down || source == rpar_up || source == repairSTAT)
			picture.setImageResource(R.drawable.repair);
		else if (source == scienceTEXT || source == bgns_down || source == bgns_up || source == scienceSTAT)
			picture.setImageResource(R.drawable.science);
		else if (source == small_gunsTEXT || source == sgns_down || source == sgns_up || source == small_gunsSTAT)
			picture.setImageResource(R.drawable.small_guns);
		else if (source == sneakTEXT || source == snek_down || source == snek_up || source == sneakSTAT)
				picture.setImageResource(R.drawable.sneak);
		else if (source == speechTEXT || source == spch_down || source == spch_up || source == speechSTAT)
			picture.setImageResource(R.drawable.speech);
		else if (source == unarmedTEXT || source == uarm_down || source == uarm_up || source == unarmedSTAT)
			picture.setImageResource(R.drawable.unarmed);
		
		// STAT DISTRIBUTION
		
		if (source == finished){
			
			SharedPreferences prefs = getSharedPreferences("SKILLS",0);
			prefs.edit().putInt("BARTER", Integer.parseInt(barterSTAT.getText().toString())).commit();
			prefs.edit().putInt("BIG_GUNS", Integer.parseInt(big_gunsSTAT.getText().toString())).commit();
			prefs.edit().putInt("ENERGY", Integer.parseInt(energySTAT.getText().toString())).commit();
			prefs.edit().putInt("EXPLOSIVES", Integer.parseInt(explosivesSTAT.getText().toString())).commit();
			prefs.edit().putInt("LOCKPICK", Integer.parseInt(lockpickSTAT.getText().toString())).commit();
			prefs.edit().putInt("MEDICINE", Integer.parseInt(medicineSTAT.getText().toString())).commit();
			prefs.edit().putInt("MELEE", Integer.parseInt(meleeSTAT.getText().toString())).commit();
			prefs.edit().putInt("REPAIR", Integer.parseInt(repairSTAT.getText().toString())).commit();
			prefs.edit().putInt("SCIENCE", Integer.parseInt(scienceSTAT.getText().toString())).commit();
			prefs.edit().putInt("SMALL_GUNS", Integer.parseInt(small_gunsSTAT.getText().toString())).commit();
			prefs.edit().putInt("SNEAK", Integer.parseInt(sneakSTAT.getText().toString())).commit();
			prefs.edit().putInt("SPEECH", Integer.parseInt(speechSTAT.getText().toString())).commit();
			prefs.edit().putInt("UNARMED", Integer.parseInt(unarmedSTAT.getText().toString())).commit();
			this.setResult(RESULT_OK);
			finish();
		}
		
		
	}
	
	private void increaseStat(TextView toIncrease){
		String currentRemaining = REMAINING.getText().toString();
		
		if (Integer.parseInt(currentRemaining) > 0){
			String currentStat = toIncrease.getText().toString();
			int nowRemaining = Integer.parseInt(currentRemaining) - 1;
			int nowStat = Integer.parseInt(currentStat) + 1;
			
			REMAINING.setText(String.valueOf(nowRemaining));
			toIncrease.setText(String.valueOf(nowStat));
		}
	}
	
	private void decreaseStat(TextView toDecrease){
		String currentStat = toDecrease.getText().toString();
		
		if (Integer.parseInt(currentStat) > 0){
			String currentRemaining = REMAINING.getText().toString();
			int nowRemaining = Integer.parseInt(currentRemaining) + 1;
			int nowStat = Integer.parseInt(currentStat) - 1;
			
			REMAINING.setText(String.valueOf(nowRemaining));
			toDecrease.setText(String.valueOf(nowStat));
		}
	}
	
	private class StatIncreaseListener implements OnClickListener{
		
		private TextView currentStat;
		
		public StatIncreaseListener(TextView x) {
			currentStat = x;
		}
		
		public void onClick(View v) {
			increaseStat(currentStat);
		}
		
	}
	
	private class StatDecreaseListener implements OnClickListener{
		
		private TextView currentStat;
		
		public StatDecreaseListener(TextView x) {
			currentStat = x;
		}
		
		public void onClick(View v) {
			decreaseStat(currentStat);
		}
		
	}

}
