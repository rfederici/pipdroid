package com.skettidev.pipdroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SetSpecial extends Activity implements OnClickListener{
	
	private SoundPool sp;
	private Typeface font;
	private TextView str_text, per_text, end_text, chr_text, int_text, agi_text, luk_text;
	private ImageView str_up, str_down, per_up, per_down, end_up, end_down, chr_up, chr_down, int_up, int_down, agi_up, agi_down, luk_up, luk_down;
	private TextView str_stat, per_stat, end_stat, chr_stat, int_stat, agi_stat, luk_stat, REMAINING;
	private Button finished;
	private int aud_selection;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_special);
		
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		aud_selection = sp.load(this, R.raw.selection, 1);
		
		font = Typeface.createFromAsset(getAssets(), "Monofonto.ttf");
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		str_text = (TextView) findViewById(R.id.text_strength);
			str_text.setOnClickListener(this);
			str_text.setTypeface(font);
		str_stat = (TextView) findViewById(R.id.strength_stat);
			str_stat.setOnClickListener(this);
			str_stat.setTypeface(font);
		str_up = (ImageView) findViewById(R.id.str_inc);
			str_up.setOnClickListener(new StatIncreaseListener(str_stat));
		str_down = (ImageView) findViewById(R.id.str_dec);
			str_down.setOnClickListener(new StatDecreaseListener(str_stat));
		
		per_text = (TextView) findViewById(R.id.text_perception);
			per_text.setOnClickListener(this);
			per_text.setTypeface(font);
		per_stat = (TextView) findViewById(R.id.perception_stat);
			per_stat.setOnClickListener(this);
			per_stat.setTypeface(font);
		per_up = (ImageView) findViewById(R.id.per_inc);
			per_up.setOnClickListener(new StatIncreaseListener(per_stat));
		per_down = (ImageView) findViewById(R.id.per_dec);
			per_down.setOnClickListener(new StatDecreaseListener(per_stat));
		
		end_text = (TextView) findViewById(R.id.text_endurance);
			end_text.setOnClickListener(this);
			end_text.setTypeface(font);
		end_stat = (TextView) findViewById(R.id.endurance_stat);
			end_stat.setOnClickListener(this);
			end_stat.setTypeface(font);
		end_up = (ImageView) findViewById(R.id.end_inc);
			end_up.setOnClickListener(new StatIncreaseListener(end_stat));
		end_down = (ImageView) findViewById(R.id.end_dec);
			end_down.setOnClickListener(new StatDecreaseListener(end_stat));
		
		chr_text = (TextView) findViewById(R.id.text_charisma);
			chr_text.setOnClickListener(this);
			chr_text.setTypeface(font);
		chr_stat = (TextView) findViewById(R.id.charisma_stat);
			chr_stat.setOnClickListener(this);
			chr_stat.setTypeface(font);
		chr_up = (ImageView) findViewById(R.id.chr_inc);
			chr_up.setOnClickListener(new StatIncreaseListener(chr_stat));
		chr_down = (ImageView) findViewById(R.id.chr_dec);
			chr_down.setOnClickListener(new StatDecreaseListener(chr_stat));
		
		int_text = (TextView) findViewById(R.id.text_intelligence);
			int_text.setOnClickListener(this);
			int_text.setTypeface(font);
		int_stat = (TextView) findViewById(R.id.intelligence_stat);
			int_stat.setOnClickListener(this);
			int_stat.setTypeface(font);
		int_up = (ImageView) findViewById(R.id.int_inc);
			int_up.setOnClickListener(new StatIncreaseListener(int_stat));
		int_down = (ImageView) findViewById(R.id.int_dec);
			int_down.setOnClickListener(new StatDecreaseListener(int_stat));
		
		agi_text = (TextView) findViewById(R.id.text_agility);
			agi_text.setOnClickListener(this);
			agi_text.setTypeface(font);
		agi_stat = (TextView) findViewById(R.id.agility_stat);
			agi_stat.setOnClickListener(this);
			agi_stat.setTypeface(font);
		agi_up = (ImageView) findViewById(R.id.agi_inc);
			agi_up.setOnClickListener(new StatIncreaseListener(agi_stat));
		agi_down = (ImageView) findViewById(R.id.agi_dec);
			agi_down.setOnClickListener(new StatDecreaseListener(agi_stat));
		
		luk_text = (TextView) findViewById(R.id.text_luck);
			luk_text.setOnClickListener(this);
			luk_text.setTypeface(font);
		luk_stat = (TextView) findViewById(R.id.luck_stat);
			luk_stat.setOnClickListener(this);
			luk_stat.setTypeface(font);
		luk_up = (ImageView) findViewById(R.id.luk_inc);
			luk_up.setOnClickListener(new StatIncreaseListener(luk_stat));
		luk_down = (ImageView) findViewById(R.id.luk_dec);
			luk_down.setOnClickListener(new StatDecreaseListener(luk_stat));
		
		REMAINING = (TextView) findViewById(R.id.remaining_stats);
			REMAINING.setText("5");
			REMAINING.setTypeface(font);
			REMAINING.setOnClickListener(this);
		
		finished = (Button) findViewById(R.id.finished);
			finished.setOnClickListener(this);
			
		str_stat.setText("5");
		per_stat.setText("5");
		end_stat.setText("5");
		chr_stat.setText("5");
		int_stat.setText("5");
		agi_stat.setText("5");
		luk_stat.setText("5");
		
	}

	public void onClick(View source) {
		
		sp.play(aud_selection, 1, 1, 1, 0, 1);
		
		// IMAGE CHANGES
		ImageView picture = (ImageView) findViewById(R.id.set_special_image);
		
		if (source == str_text || source == str_up || source == str_stat || source == str_down)
			picture.setImageResource(R.drawable.strength);
		else if (source == per_text || source == per_up || source == per_stat || source == per_down)
			picture.setImageResource(R.drawable.perception);
		else if (source == end_text || source == end_up || source == end_stat || source == end_down)
			picture.setImageResource(R.drawable.endurance);
		else if (source == chr_text || source == chr_up || source == chr_stat || source == chr_down)
			picture.setImageResource(R.drawable.charisma);
		else if (source == int_text || source == int_up || source == int_stat || source == int_down)
			picture.setImageResource(R.drawable.intelligence);
		else if (source == agi_text || source == agi_up || source == agi_stat || source == agi_down)
			picture.setImageResource(R.drawable.agility);
		else if (source == luk_text || source == luk_up || source == luk_stat || source == luk_down)
			picture.setImageResource(R.drawable.luck);
		
		// STAT DISTRIBUTION
		
		if (source == finished){
			
			SharedPreferences prefs = getSharedPreferences("SPECIAL",0);
			prefs.edit().putInt("STRENGTH", Integer.parseInt(str_stat.getText().toString())).commit();
			prefs.edit().putInt("PERCEPTION", Integer.parseInt(per_stat.getText().toString())).commit();
			prefs.edit().putInt("ENDURANCE", Integer.parseInt(end_stat.getText().toString())).commit();
			prefs.edit().putInt("CHARISMA", Integer.parseInt(chr_stat.getText().toString())).commit();
			prefs.edit().putInt("INTELLIGENCE", Integer.parseInt(int_stat.getText().toString())).commit();
			prefs.edit().putInt("AGILITY", Integer.parseInt(agi_stat.getText().toString())).commit();
			prefs.edit().putInt("LUCK", Integer.parseInt(luk_stat.getText().toString())).commit();
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
