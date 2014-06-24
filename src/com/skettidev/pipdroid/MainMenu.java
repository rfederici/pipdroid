package com.skettidev.pipdroid;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;


public class MainMenu extends MapActivity implements OnClickListener, OnLongClickListener,
		SurfaceHolder.Callback {
	
	// ########################
	// ## On app start ########
	// ########################

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Init sound
		HandleSound.initSound(this.getApplicationContext());
		
		VarVault.font = Typeface.createFromAsset(getAssets(), "Monofonto.ttf");
		VarVault.curWG = new Stat(); VarVault.maxWG = new Stat(); VarVault.curCaps = new Stat();

		// Set flags and volume buttons
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Get contacts
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		VarVault.numContacts = cursor.getCount();

		// Initialize the three buttons
		VarVault.stats = (ImageView) findViewById(R.id.left_stats);
		TextView x = (TextView) findViewById(R.id.left_button_stats);
		x.setTypeface(VarVault.font);
		x.setTextColor(Color.argb(100, 255, 225, 0));
		VarVault.stats.setOnClickListener(this);

		VarVault.items = (ImageView) findViewById(R.id.left_items);
		TextView y = (TextView) findViewById(R.id.left_button_items);
		y.setTypeface(VarVault.font);
		y.setTextColor(Color.argb(100, 255, 225, 0));
		VarVault.items.setOnClickListener(this);

		VarVault.data = (ImageView) findViewById(R.id.left_data);
		TextView z = (TextView) findViewById(R.id.left_button_data);
		z.setTypeface(VarVault.font);
		z.setTextColor(Color.argb(100, 255, 225, 0));
		VarVault.data.setOnClickListener(this);

		// Get stats and fill arrays
		
		initSkills();
		initSpecial();
		InitializeArrays.all();
		
		onClick(VarVault.stats);
		initCaps();
	}

	public void onClick(View source) {

		VarVault.curCaps.setValue(VarVault.curCaps.getValue()+5);
		
		// Play a tune, dependent on source.

		if (VarVault.MAIN_BUTTONS.contains(source))
			HandleSound.playSound(HandleSound.aud_newTab);
		else if (source == VarVault.stimpak)
			HandleSound.playSound(HandleSound.aud_stimpak);
		else
			HandleSound.playSound(HandleSound.aud_selection);

		// Set the panels for future usage.
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		ViewGroup topBar = (ViewGroup) findViewById(R.id.top_bar);
		ViewGroup bottomBar = (ViewGroup) findViewById(R.id.bottom_bar);

		// Sort the source
		if (source == VarVault.stats)
			statsClicked();
		else if (source == VarVault.statusLL)
			statusClicked();
		else if (source == VarVault.specialLL || source == VarVault.special)
			specialClicked();
		else if (source == VarVault.skillsLL || source == VarVault.skills)
			skillsClicked();
		else if (source == VarVault.perksLL) {}
		else if (source == VarVault.generalLL) {}
		else if (source == VarVault.cnd || source == VarVault.rad || source == VarVault.stimpak) {}
		else if (VarVault.SUBMENU_SPECIAL.contains(source))
			specialStatClicked(source);
		else if (VarVault.SUBMENU_SKILLS.contains(source))
			skillStatClicked(source);
		else if (source == VarVault.flashlight)
			flashlightClicked();

		else if (source == VarVault.items)
			itemsClicked();
		else if (source == VarVault.weaponsLL)
			weaponsClicked();
		else if (VarVault.Weapons.contains(source)) {}
		
		else if (source == VarVault.apparelLL)
			apparelClicked();
		else if (VarVault.Apparel.contains(source)) {}
		
		else if (source == VarVault.aidLL) {updateCAPS();
		} else if (source == VarVault.miscLL) {updateCAPS();
		} else if (source == VarVault.ammoLL) {updateCAPS();
		}

		else if (source == VarVault.data){
			dataClicked();
		}

		else {

			topBar.removeAllViews();
			midPanel.removeAllViews();
			bottomBar.removeAllViews();
		}

	}

	private void flashlightClicked() {
		if (VarVault.mCamera == null) {
			VarVault.preview = (SurfaceView) findViewById(R.id.PREVIEW);
			VarVault.mHolder = VarVault.preview.getHolder();
			VarVault.mCamera = Camera.open();
			try {
				VarVault.mCamera.setPreviewDisplay(VarVault.mHolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// If it's off, turn it on
		if (VarVault.isCamOn == false) {
			Parameters params = VarVault.mCamera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			VarVault.mCamera.setParameters(params);
			VarVault.mCamera.startPreview();
			VarVault.isCamOn = true;
		}

		// If it's on, turn it off
		else {
			Parameters params = VarVault.mCamera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			VarVault.mCamera.setParameters(params);
			VarVault.mCamera.stopPreview();
			VarVault.mCamera.release();
			VarVault.mCamera = null;
			VarVault.isCamOn = false;
		}
	}

	private void statsClicked() {
		// Clear crap
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		ViewGroup topBar = (ViewGroup) findViewById(R.id.top_bar);
		ViewGroup bottomBar = (ViewGroup) findViewById(R.id.bottom_bar);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		topBar.removeAllViews();
		bottomBar.removeAllViews();

		// Main screen turn on
		inf.inflate(R.layout.status_screen, midPanel);
		inf.inflate(R.layout.stats_bar_top, topBar);
		inf.inflate(R.layout.stats_bar_bottom, bottomBar);

		// Format top bar text
		VarVault.title = (TextView) findViewById(R.id.title_stats);
		VarVault.title.setText("STATUS");
		VarVault.title.setTypeface(VarVault.font);

		VarVault.hp = (TextView) findViewById(R.id.hp_stats);
		VarVault.hp.setTypeface(VarVault.font);

		VarVault.ap = (TextView) findViewById(R.id.ap_stats);
		VarVault.ap.setTypeface(VarVault.font);

		VarVault.bat = (TextView) findViewById(R.id.bat_stats);
		VarVault.bat.setTypeface(VarVault.font);
		this.registerReceiver(VarVault.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

		// Button-ize the buttons
		VarVault.status = (TextView) findViewById(R.id.btn_status);
		VarVault.statusLL = (LinearLayout) findViewById(R.id.btn_status_box);
		VarVault.status.setTypeface(VarVault.font);
		VarVault.statusLL.setOnClickListener(this);

		VarVault.special = (TextView) findViewById(R.id.btn_special);
		VarVault.specialLL = (LinearLayout) findViewById(R.id.btn_special_box);
		VarVault.special.setTypeface(VarVault.font);
		VarVault.specialLL.setOnClickListener(this);

		VarVault.skills = (TextView) findViewById(R.id.btn_skills);
		VarVault.skillsLL = (LinearLayout) findViewById(R.id.btn_skills_box);
		VarVault.skills.setTypeface(VarVault.font);
		VarVault.skillsLL.setOnClickListener(this);

		VarVault.perks = (TextView) findViewById(R.id.btn_perks);
		VarVault.perksLL = (LinearLayout) findViewById(R.id.btn_perks_box);
		VarVault.perks.setTypeface(VarVault.font);
		VarVault.perksLL.setOnClickListener(this);

		VarVault.general = (TextView) findViewById(R.id.btn_general);
		VarVault.generalLL = (LinearLayout) findViewById(R.id.btn_general_box);
		VarVault.general.setTypeface(VarVault.font);
		VarVault.generalLL.setOnClickListener(this);

		statusClicked();

	}

	private void itemsClicked() {
		// Clear crap
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		ViewGroup topBar = (ViewGroup) findViewById(R.id.top_bar);
		ViewGroup bottomBar = (ViewGroup) findViewById(R.id.bottom_bar);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		topBar.removeAllViews();
		bottomBar.removeAllViews();

		// Main screen turn on
		inf.inflate(R.layout.weapons_screen, midPanel);
		inf.inflate(R.layout.items_bar_top, topBar);
		inf.inflate(R.layout.items_bar_bottom, bottomBar);

		VarVault.title = (TextView) findViewById(R.id.title_items);
		VarVault.title.setTypeface(VarVault.font);

		VarVault.wg = (TextView) findViewById(R.id.wg_items);
		VarVault.maxWG.setValue(150 + (10 * VarVault.strength.getValue()));
		updateWG();
		VarVault.wg.setTypeface(VarVault.font);

		VarVault.caps = (TextView) findViewById(R.id.caps_items);
		updateCAPS();
		VarVault.caps.setTypeface(VarVault.font);
		//VarVault.caps.setText(VarVault.curCaps.getValue());

		VarVault.bat = (TextView) findViewById(R.id.bat_items);
		VarVault.bat.setTypeface(VarVault.font);
		this.registerReceiver(VarVault.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

		// Button-ize the buttons
		VarVault.weapons = (TextView) findViewById(R.id.btn_weapons);
		VarVault.weaponsLL = (LinearLayout) findViewById(R.id.btn_weapons_box);
		VarVault.weapons.setTypeface(VarVault.font);
		VarVault.weaponsLL.setOnClickListener(this);

		VarVault.apparel = (TextView) findViewById(R.id.btn_apparel);
		VarVault.apparelLL = (LinearLayout) findViewById(R.id.btn_apparel_box);
		VarVault.apparel.setTypeface(VarVault.font);
		VarVault.apparelLL.setOnClickListener(this);

		VarVault.aid = (TextView) findViewById(R.id.btn_aid);
		VarVault.aidLL = (LinearLayout) findViewById(R.id.btn_aid_box);
		VarVault.aid.setTypeface(VarVault.font);
		VarVault.aidLL.setOnClickListener(this);

		VarVault.misc = (TextView) findViewById(R.id.btn_misc);
		VarVault.miscLL = (LinearLayout) findViewById(R.id.btn_misc_box);
		VarVault.misc.setTypeface(VarVault.font);
		VarVault.miscLL.setOnClickListener(this);

		VarVault.ammo = (TextView) findViewById(R.id.btn_ammo);
		VarVault.ammoLL = (LinearLayout) findViewById(R.id.btn_ammo_box);
		VarVault.ammo.setTypeface(VarVault.font);
		VarVault.ammoLL.setOnClickListener(this);

		populateOwnedWeapons();

	}

	private void weaponsClicked(){
		
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		inf.inflate(R.layout.weapons_screen, midPanel);
		
		populateOwnedWeapons();
		
		updateCAPS();
	}
	
	private void apparelClicked(){
		
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		inf.inflate(R.layout.apparel_screen, midPanel);
		
		populateOwnedApparel();
		
		updateCAPS();
	}
	
	private void dataClicked() {
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		ViewGroup topBar = (ViewGroup) findViewById(R.id.top_bar);
		ViewGroup bottomBar = (ViewGroup) findViewById(R.id.bottom_bar);
		LayoutInflater inf = this.getLayoutInflater();
		
		VarVault.map = (MapView) findViewById(R.id.map);
		//map.setEnabled(true);

		midPanel.removeAllViews();
		topBar.removeAllViews();
		bottomBar.removeAllViews();

		// Main screen turn on
		inf.inflate(R.layout.map_screen, midPanel);
		inf.inflate(R.layout.items_bar_top, topBar);
		inf.inflate(R.layout.items_bar_bottom, bottomBar);
	}

	private void statusClicked() {

		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		inf.inflate(R.layout.status_screen, midPanel);

		VarVault.title.setText("STATUS");

		VarVault.cnd = (TextView) findViewById(R.id.btn_cnd);
		VarVault.cnd.setTypeface(VarVault.font);
		VarVault.cnd.setOnClickListener(this);

		VarVault.rad = (TextView) findViewById(R.id.btn_rad);
		VarVault.rad.setTypeface(VarVault.font);
		VarVault.rad.setOnClickListener(this);

		VarVault.stimpak = (TextView) findViewById(R.id.btn_stimpak);
		VarVault.stimpak.setTypeface(VarVault.font);
		VarVault.stimpak.setOnClickListener(this);

		VarVault.flashlight = (TextView) findViewById(R.id.btn_flashlight);
		VarVault.flashlight.setTypeface(VarVault.font);
		VarVault.flashlight.setOnClickListener(this);
	}

	private void specialClicked() {

		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		inf.inflate(R.layout.special_screen, midPanel);

		VarVault.title.setText("SPECIAL");

		VarVault.specialImage = (ImageView) findViewById(R.id.special_image);

		VarVault.str = (TextView) findViewById(R.id.text_strength);
		VarVault.str.setTypeface(VarVault.font);
		VarVault.str.setOnClickListener(this);
		VarVault.strSTAT = (TextView) findViewById(R.id.strength_stat);
		VarVault.strSTAT.setText(String.valueOf(VarVault.strength.getValue()));
		VarVault.strSTAT.setTypeface(VarVault.font);

		VarVault.per = (TextView) findViewById(R.id.text_perception);
		VarVault.per.setTypeface(VarVault.font);
		VarVault.per.setOnClickListener(this);
		VarVault.perSTAT = (TextView) findViewById(R.id.perception_stat);
		VarVault.perSTAT.setText(String.valueOf(VarVault.perception.getValue()));
		VarVault.perSTAT.setTypeface(VarVault.font);

		VarVault.end = (TextView) findViewById(R.id.text_endurance);
		VarVault.end.setTypeface(VarVault.font);
		VarVault.end.setOnClickListener(this);
		VarVault.endSTAT = (TextView) findViewById(R.id.endurance_stat);
		VarVault.endSTAT.setText(String.valueOf(VarVault.endurance.getValue()));
		VarVault.endSTAT.setTypeface(VarVault.font);

		VarVault.chr = (TextView) findViewById(R.id.text_charisma);
		VarVault.chr.setTypeface(VarVault.font);
		VarVault.chr.setOnClickListener(this);
		VarVault.chrSTAT = (TextView) findViewById(R.id.charisma_stat);
		VarVault.chrSTAT.setText(String.valueOf(VarVault.charisma.getValue()));
		VarVault.chrSTAT.setTypeface(VarVault.font);

		VarVault.intel = (TextView) findViewById(R.id.text_intelligence);
		VarVault.intel.setTypeface(VarVault.font);
		VarVault.intel.setOnClickListener(this);
		VarVault.intelSTAT = (TextView) findViewById(R.id.intelligence_stat);
		VarVault.intelSTAT.setText(String.valueOf(VarVault.intelligence.getValue()));
		VarVault.intelSTAT.setTypeface(VarVault.font);

		VarVault.agi = (TextView) findViewById(R.id.text_agility);
		VarVault.agi.setTypeface(VarVault.font);
		VarVault.agi.setOnClickListener(this);
		VarVault.agiSTAT = (TextView) findViewById(R.id.agility_stat);
		VarVault.agiSTAT.setText(String.valueOf(VarVault.agility.getValue()));
		VarVault.agiSTAT.setTypeface(VarVault.font);

		VarVault.luk = (TextView) findViewById(R.id.text_luck);
		VarVault.luk.setTypeface(VarVault.font);
		VarVault.luk.setOnClickListener(this);
		VarVault.lukSTAT = (TextView) findViewById(R.id.luck_stat);
		VarVault.lukSTAT.setText(String.valueOf(VarVault.luck.getValue()));
		VarVault.lukSTAT.setTypeface(VarVault.font);

		InitializeArrays.submenu_special();
	}

	private void skillsClicked() {
		int allocatedpoints = (VarVault.barter.getValue() + VarVault.big_guns.getValue()
				+ VarVault.energy.getValue() + VarVault.explosives.getValue()
				+ VarVault.lockpick.getValue() + VarVault.medicine.getValue() + VarVault.melee.getValue()
				+ VarVault.repair.getValue() + VarVault.science.getValue()
				+ VarVault.small_guns.getValue() + VarVault.sneak.getValue() + VarVault.speech.getValue() + VarVault.unarmed
				.getValue());
		if ((VarVault.numContacts + 130) > allocatedpoints) {
			// Allocate unused points
			Intent i = new Intent(MainMenu.this, SetSkills.class);
			MainMenu.this.startActivityForResult(i, 1);
		} else {
			// Show skills screen
			ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
			LayoutInflater inf = this.getLayoutInflater();

			midPanel.removeAllViews();
			inf.inflate(R.layout.skills_screen, midPanel);

			VarVault.skillImage = (ImageView) findViewById(R.id.skills_image);

			VarVault.title.setText("SKILLS");

			VarVault.bart = (TextView) findViewById(R.id.text_barter);
			VarVault.barterSTAT = (TextView) findViewById(R.id.barter_stat);
			VarVault.bart.setTypeface(VarVault.font);
			VarVault.barterSTAT.setTypeface(VarVault.font);
			VarVault.barterSTAT.setText(String.valueOf(VarVault.barter.getValue()));
			VarVault.bart.setOnClickListener(this);

			VarVault.bgns = (TextView) findViewById(R.id.text_big_guns);
			VarVault.big_gunsSTAT = (TextView) findViewById(R.id.big_guns_stat);
			VarVault.bgns.setTypeface(VarVault.font);
			VarVault.big_gunsSTAT.setTypeface(VarVault.font);
			VarVault.big_gunsSTAT.setText(String.valueOf(VarVault.big_guns.getValue()));
			VarVault.bgns.setOnClickListener(this);

			VarVault.nrg = (TextView) findViewById(R.id.text_energy);
			VarVault.energySTAT = (TextView) findViewById(R.id.energy_stat);
			VarVault.nrg.setTypeface(VarVault.font);
			VarVault.energySTAT.setTypeface(VarVault.font);
			VarVault.energySTAT.setText(String.valueOf(VarVault.energy.getValue()));
			VarVault.nrg.setOnClickListener(this);

			VarVault.expl = (TextView) findViewById(R.id.text_explosives);
			VarVault.explosivesSTAT = (TextView) findViewById(R.id.explosives_stat);
			VarVault.expl.setTypeface(VarVault.font);
			VarVault.explosivesSTAT.setTypeface(VarVault.font);
			VarVault.explosivesSTAT.setText(String.valueOf(VarVault.explosives.getValue()));
			VarVault.expl.setOnClickListener(this);

			VarVault.lock = (TextView) findViewById(R.id.text_lockpick);
			VarVault.lockpickSTAT = (TextView) findViewById(R.id.lockpick_stat);
			VarVault.lock.setTypeface(VarVault.font);
			VarVault.lockpickSTAT.setTypeface(VarVault.font);
			VarVault.lockpickSTAT.setText(String.valueOf(VarVault.lockpick.getValue()));
			VarVault.lock.setOnClickListener(this);

			VarVault.medi = (TextView) findViewById(R.id.text_medicine);
			VarVault.medicineSTAT = (TextView) findViewById(R.id.medicine_stat);
			VarVault.medi.setTypeface(VarVault.font);
			VarVault.medicineSTAT.setTypeface(VarVault.font);
			VarVault.medicineSTAT.setText(String.valueOf(VarVault.medicine.getValue()));
			VarVault.medi.setOnClickListener(this);

			VarVault.mlee = (TextView) findViewById(R.id.text_melee);
			VarVault.meleeSTAT = (TextView) findViewById(R.id.melee_stat);
			VarVault.mlee.setTypeface(VarVault.font);
			VarVault.meleeSTAT.setTypeface(VarVault.font);
			VarVault.meleeSTAT.setText(String.valueOf(VarVault.melee.getValue()));
			VarVault.mlee.setOnClickListener(this);

			VarVault.rpar = (TextView) findViewById(R.id.text_repair);
			VarVault.repairSTAT = (TextView) findViewById(R.id.repair_stat);
			VarVault.rpar.setTypeface(VarVault.font);
			VarVault.repairSTAT.setTypeface(VarVault.font);
			VarVault.repairSTAT.setText(String.valueOf(VarVault.repair.getValue()));
			VarVault.rpar.setOnClickListener(this);

			VarVault.sci = (TextView) findViewById(R.id.text_science);
			VarVault.scienceSTAT = (TextView) findViewById(R.id.science_stat);
			VarVault.sci.setTypeface(VarVault.font);
			VarVault.scienceSTAT.setTypeface(VarVault.font);
			VarVault.scienceSTAT.setText(String.valueOf(VarVault.science.getValue()));
			VarVault.sci.setOnClickListener(this);

			VarVault.sgns = (TextView) findViewById(R.id.text_small_guns);
			VarVault.small_gunsSTAT = (TextView) findViewById(R.id.small_guns_stat);
			VarVault.sgns.setTypeface(VarVault.font);
			VarVault.small_gunsSTAT.setTypeface(VarVault.font);
			VarVault.small_gunsSTAT.setText(String.valueOf(VarVault.small_guns.getValue()));
			VarVault.sgns.setOnClickListener(this);

			VarVault.snek = (TextView) findViewById(R.id.text_sneak);
			VarVault.sneakSTAT = (TextView) findViewById(R.id.sneak_stat);
			VarVault.snek.setTypeface(VarVault.font);
			VarVault.sneakSTAT.setTypeface(VarVault.font);
			VarVault.sneakSTAT.setText(String.valueOf(VarVault.sneak.getValue()));
			VarVault.snek.setOnClickListener(this);

			VarVault.spch = (TextView) findViewById(R.id.text_speech);
			VarVault.speechSTAT = (TextView) findViewById(R.id.speech_stat);
			VarVault.spch.setTypeface(VarVault.font);
			VarVault.speechSTAT.setTypeface(VarVault.font);
			VarVault.speechSTAT.setText(String.valueOf(VarVault.speech.getValue()));
			VarVault.spch.setOnClickListener(this);

			VarVault.uarm = (TextView) findViewById(R.id.text_unarmed);
			VarVault.unarmedSTAT = (TextView) findViewById(R.id.unarmed_stat);
			VarVault.uarm.setTypeface(VarVault.font);
			VarVault.unarmedSTAT.setTypeface(VarVault.font);
			VarVault.unarmedSTAT.setText(String.valueOf(VarVault.unarmed.getValue()));
			VarVault.uarm.setOnClickListener(this);

			InitializeArrays.submenu_skills();
		}
	}

	private void skillStatClicked(View source) {

		if (source == VarVault.bart || source == VarVault.barterSTAT)
			VarVault.skillImage.setImageResource(R.drawable.barter);
		else if (source == VarVault.bgns || source == VarVault.big_gunsSTAT)
			VarVault.skillImage.setImageResource(R.drawable.big_guns);
		else if (source == VarVault.nrg || source == VarVault.energySTAT)
			VarVault.skillImage.setImageResource(R.drawable.energy);
		else if (source == VarVault.expl || source == VarVault.explosivesSTAT)
			VarVault.skillImage.setImageResource(R.drawable.explosives);
		else if (source == VarVault.lock || source == VarVault.lockpickSTAT)
			VarVault.skillImage.setImageResource(R.drawable.lockpick);
		else if (source == VarVault.medi || source == VarVault.medicineSTAT)
			VarVault.skillImage.setImageResource(R.drawable.medicine);
		else if (source == VarVault.mlee || source == VarVault.meleeSTAT)
			VarVault.skillImage.setImageResource(R.drawable.melee);
		else if (source == VarVault.rpar || source == VarVault.repairSTAT)
			VarVault.skillImage.setImageResource(R.drawable.repair);
		else if (source == VarVault.sci || source == VarVault.scienceSTAT)
			VarVault.skillImage.setImageResource(R.drawable.science);
		else if (source == VarVault.sgns || source == VarVault.small_gunsSTAT)
			VarVault.skillImage.setImageResource(R.drawable.small_guns);
		else if (source == VarVault.snek || source == VarVault.sneakSTAT)
			VarVault.skillImage.setImageResource(R.drawable.sneak);
		else if (source == VarVault.spch || source == VarVault.speechSTAT)
			VarVault.skillImage.setImageResource(R.drawable.speech);
		else if (source == VarVault.uarm || source == VarVault.unarmedSTAT)
			VarVault.skillImage.setImageResource(R.drawable.unarmed);
	}

	private void specialStatClicked(View source) {
		if (source == VarVault.str)
			VarVault.specialImage.setImageResource(R.drawable.strength);
		else if (source == VarVault.per)
			VarVault.specialImage.setImageResource(R.drawable.perception);
		else if (source == VarVault.end)
			VarVault.specialImage.setImageResource(R.drawable.endurance);
		else if (source == VarVault.chr)
			VarVault.specialImage.setImageResource(R.drawable.charisma);
		else if (source == VarVault.intel)
			VarVault.specialImage.setImageResource(R.drawable.intelligence);
		else if (source == VarVault.agi)
			VarVault.specialImage.setImageResource(R.drawable.agility);
		else if (source == VarVault.luk)
			VarVault.specialImage.setImageResource(R.drawable.luck);
	}

	private void initCaps() {
		SharedPreferences prefs = getSharedPreferences("STATS", 0);
		
		VarVault.curCaps.setValue(prefs.getInt("CAPS", 2000));
		Log.i("VALUE", "The cap value is " + VarVault.curCaps.getValue() + " at this time.");
	}
	
	private void initSpecial() {
		SharedPreferences prefs = getSharedPreferences("SPECIAL", 0);
		// prefs.edit().clear().commit();

		if (!prefs.contains("STRENGTH")) {
			Intent i = new Intent(MainMenu.this, SetSpecial.class);
			MainMenu.this.startActivityForResult(i, 0);
		} else {
			VarVault.strength.setValue(prefs.getInt("STRENGTH", -2));
			VarVault.perception.setValue(prefs.getInt("PERCEPTION", -2));
			VarVault.endurance.setValue(prefs.getInt("ENDURANCE", -2));
			VarVault.charisma.setValue(prefs.getInt("CHARISMA", -2));
			VarVault.intelligence.setValue(prefs.getInt("INTELLIGENCE", -2));
			VarVault.agility.setValue(prefs.getInt("AGILITY", -2));
			VarVault.luck.setValue(prefs.getInt("LUCK", -2));
		}
	}

	private void initSkills() {
		SharedPreferences prefs = getSharedPreferences("SKILLS", 0);
		// prefs.edit().clear().commit();

		if (!prefs.contains("BARTER")) {
			Intent i = new Intent(MainMenu.this, SetSkills.class);
			MainMenu.this.startActivityForResult(i, 1);
		} else {
			VarVault.barter.setValue(prefs.getInt("BARTER", -2));
			VarVault.big_guns.setValue(prefs.getInt("BIG_GUNS", -2));
			VarVault.energy.setValue(prefs.getInt("ENERGY", -2));
			VarVault.explosives.setValue(prefs.getInt("EXPLOSIVES", -2));
			VarVault.lockpick.setValue(prefs.getInt("LOCKPICK", -2));
			VarVault.medicine.setValue(prefs.getInt("MEDICINE", -2));
			VarVault.melee.setValue(prefs.getInt("MELEE", -2));
			VarVault.repair.setValue(prefs.getInt("REPAIR", -2));
			VarVault.science.setValue(prefs.getInt("SCIENCE", -2));
			VarVault.small_guns.setValue(prefs.getInt("SMALL_GUNS", -2));
			VarVault.sneak.setValue(prefs.getInt("SNEAK", -2));
			VarVault.speech.setValue(prefs.getInt("SPEECH", -2));
			VarVault.unarmed.setValue(prefs.getInt("UNARMED", -2));

			if ((VarVault.numContacts + 130) > (VarVault.barter.getValue() + VarVault.big_guns.getValue()
					+ VarVault.energy.getValue() + VarVault.explosives.getValue()
					+ VarVault.lockpick.getValue() + VarVault.medicine.getValue()
					+ VarVault.melee.getValue() + VarVault.repair.getValue() + VarVault.science.getValue()
					+ VarVault.small_guns.getValue() + VarVault.sneak.getValue()
					+ VarVault.speech.getValue() + VarVault.unarmed.getValue())) {
				Intent i = new Intent(MainMenu.this, SetSkills.class);
				MainMenu.this.startActivityForResult(i, 1);
			}

			onActivityResult(1, RESULT_OK, null);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 0 && resultCode == RESULT_OK) {
			SharedPreferences prefs = getSharedPreferences("SPECIAL", 0);
			VarVault.strength.setValue(prefs.getInt("STRENGTH", -2));
			VarVault.perception.setValue(prefs.getInt("PERCEPTION", -2));
			VarVault.endurance.setValue(prefs.getInt("ENDURANCE", -2));
			VarVault.charisma.setValue(prefs.getInt("CHARISMA", -2));
			VarVault.intelligence.setValue(prefs.getInt("INTELLIGENCE", -2));
			VarVault.agility.setValue(prefs.getInt("AGILITY", -2));
			VarVault.luck.setValue(prefs.getInt("LUCK", -2));
		}

		if (requestCode == 1 && resultCode == RESULT_OK) {
			SharedPreferences prefs = getSharedPreferences("SKILLS", 0);
			VarVault.barter.setValue(prefs.getInt("BARTER", -2));
			VarVault.big_guns.setValue(prefs.getInt("BIG_GUNS", -2));
			VarVault.energy.setValue(prefs.getInt("ENERGY", -2));
			VarVault.explosives.setValue(prefs.getInt("EXPLOSIVES", -2));
			VarVault.lockpick.setValue(prefs.getInt("LOCKPICK", -2));
			VarVault.medicine.setValue(prefs.getInt("MEDICINE", -2));
			VarVault.melee.setValue(prefs.getInt("MELEE", -2));
			VarVault.repair.setValue(prefs.getInt("REPAIR", -2));
			VarVault.science.setValue(prefs.getInt("SCIENCE", -2));
			VarVault.small_guns.setValue(prefs.getInt("SMALL_GUNS", -2));
			VarVault.sneak.setValue(prefs.getInt("SNEAK", -2));
			VarVault.speech.setValue(prefs.getInt("SPEECH", -2));
			VarVault.unarmed.setValue(prefs.getInt("UNARMED", -2));
		}

	}

	protected void onPause() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (VarVault.isCamOn == true) {
			Parameters params = VarVault.mCamera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			VarVault.mCamera.setParameters(params);
			VarVault.mCamera.stopPreview();
			VarVault.mCamera.release();
			VarVault.mCamera = null;
			VarVault.isCamOn = false;
		}
		super.onPause();
	}

	protected void onResume() {
		super.onResume();
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Clear crap
		ViewGroup midPanel = (ViewGroup) findViewById(R.id.mid_panel);
		ViewGroup topBar = (ViewGroup) findViewById(R.id.top_bar);
		ViewGroup bottomBar = (ViewGroup) findViewById(R.id.bottom_bar);
		LayoutInflater inf = this.getLayoutInflater();

		midPanel.removeAllViews();
		topBar.removeAllViews();
		bottomBar.removeAllViews();

		// Main screen turn on
		inf.inflate(R.layout.status_screen, midPanel);
		inf.inflate(R.layout.stats_bar_top, topBar);
		inf.inflate(R.layout.stats_bar_bottom, bottomBar);

		// Format top bar text
		VarVault.title = (TextView) findViewById(R.id.title_stats);
		VarVault.title.setText("STATUS");
		VarVault.title.setTypeface(VarVault.font);

		VarVault.hp = (TextView) findViewById(R.id.hp_stats);
		VarVault.hp.setTypeface(VarVault.font);

		VarVault.ap = (TextView) findViewById(R.id.ap_stats);
		VarVault.ap.setTypeface(VarVault.font);

		VarVault.bat = (TextView) findViewById(R.id.bat_stats);
		VarVault.bat.setTypeface(VarVault.font);
		this.registerReceiver(VarVault.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

		// Button-ize the buttons
		VarVault.status = (TextView) findViewById(R.id.btn_status);
		VarVault.statusLL = (LinearLayout) findViewById(R.id.btn_status_box);
		VarVault.status.setTypeface(VarVault.font);
		VarVault.status.setOnClickListener(this);
		VarVault.statusLL.setOnClickListener(this);

		VarVault.special = (TextView) findViewById(R.id.btn_special);
		VarVault.specialLL = (LinearLayout) findViewById(R.id.btn_special_box);
		VarVault.special.setTypeface(VarVault.font);
		VarVault.special.setOnClickListener(this);
		VarVault.specialLL.setOnClickListener(this);

		VarVault.skills = (TextView) findViewById(R.id.btn_skills);
		VarVault.skillsLL = (LinearLayout) findViewById(R.id.btn_skills_box);
		VarVault.skills.setTypeface(VarVault.font);
		VarVault.skills.setOnClickListener(this);
		VarVault.skillsLL.setOnClickListener(this);

		VarVault.perks = (TextView) findViewById(R.id.btn_perks);
		VarVault.perksLL = (LinearLayout) findViewById(R.id.btn_perks_box);
		VarVault.perks.setTypeface(VarVault.font);
		VarVault.perks.setOnClickListener(this);
		VarVault.perksLL.setOnClickListener(this);

		VarVault.general = (TextView) findViewById(R.id.btn_general);
		VarVault.generalLL = (LinearLayout) findViewById(R.id.btn_general_box);
		VarVault.general.setTypeface(VarVault.font);
		VarVault.general.setOnClickListener(this);
		VarVault.generalLL.setOnClickListener(this);

		VarVault.cnd = (TextView) findViewById(R.id.btn_cnd);
		VarVault.cnd.setTypeface(VarVault.font);
		VarVault.cnd.setOnClickListener(this);

		VarVault.rad = (TextView) findViewById(R.id.btn_rad);
		VarVault.rad.setTypeface(VarVault.font);
		VarVault.rad.setOnClickListener(this);

		VarVault.stimpak = (TextView) findViewById(R.id.btn_stimpak);
		VarVault.stimpak.setTypeface(VarVault.font);
		VarVault.stimpak.setOnClickListener(this);

		VarVault.flashlight = (TextView) findViewById(R.id.btn_flashlight);
		VarVault.flashlight.setTypeface(VarVault.font);
		VarVault.flashlight.setOnClickListener(this);

	}

	protected void onDestroy() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		if (VarVault.isCamOn == true) {
			Parameters params = VarVault.mCamera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			VarVault.mCamera.setParameters(params);
			VarVault.mCamera.stopPreview();
			VarVault.mCamera.release();
			VarVault.mCamera = null;
			VarVault.isCamOn = false;
		}
		this.unregisterReceiver(VarVault.mBatInfoReceiver);
		super.onDestroy();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		VarVault.mHolder = holder;
		try {
			VarVault.mCamera.setPreviewDisplay(VarVault.mHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		VarVault.mCamera.stopPreview();
		VarVault.mHolder = null;
	}

	private void populateOwnedWeapons() {

		// Open Database
		dbHelper database = new dbHelper(MainMenu.this);

		Log.d("DB", "Getting a writable database...");
		SQLiteDatabase db = database.getWritableDatabase();
		Log.d("DB", "...writable database gotten!");

		// Get EVERYTHING from OwnedWeapons
		Cursor allWeapons = db.query("OwnedWeapons", new String[]{dbHelper.colName, dbHelper.colIsWearing}, null, null, null, null, "_id");
		allWeapons.moveToFirst();
		LinearLayout weaponsList = (LinearLayout) findViewById(R.id.weaponsList);

		for (int i = 0; i < 20 && !allWeapons.isAfterLast(); i++) {

			String tempName = "";
			int isWearing = 0;

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(15, 0, 0, 15);
			tempName = allWeapons.getString(0);
			isWearing = allWeapons.getInt(1);

			VarVault.Weapons.add(i, new TextView(this));

			VarVault.Weapons.get(i).setLayoutParams(lp);
			VarVault.Weapons.get(i).setTypeface(VarVault.font);
			VarVault.Weapons.get(i).setTextSize((float) 22.0);
			VarVault.Weapons.get(i).setTextColor(Color.parseColor("#AAFFAA"));
			
			// Are they wearing it?
			if (isWearing == 0)
				VarVault.Weapons.get(i).setText("  " + tempName);
			else
				VarVault.Weapons.get(i).setText("\u25a0 " + tempName);
			
			VarVault.Weapons.get(i).setOnLongClickListener(this);
			weaponsList.addView(VarVault.Weapons.get(i));

			allWeapons.moveToNext();
		}
	}
	
	private void populateOwnedApparel() {

		// Open Database
		dbHelper database = new dbHelper(MainMenu.this);

		Log.d("DB", "Getting a writable database...");
		SQLiteDatabase db = database.getWritableDatabase();
		Log.d("DB", "...writable database gotten!");

		// Get EVERYTHING from OwnedWeapons
		Cursor allApparel = db.query("OwnedApparel", new String[]{dbHelper.colName, dbHelper.colIsWearing}, null, null, null, null, "_id");
		allApparel.moveToFirst();
		LinearLayout apparelList = (LinearLayout) findViewById(R.id.apparelList);

		for (int i = 0; i < 20 && !allApparel.isAfterLast(); i++) {

			String tempName = "";
			int isWearing = 0;

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(15, 0, 0, 15);
			tempName = allApparel.getString(0);
			isWearing = allApparel.getInt(1);

			VarVault.Apparel.add(i, new TextView(this));

			VarVault.Apparel.get(i).setLayoutParams(lp);
			VarVault.Apparel.get(i).setTypeface(VarVault.font);
			VarVault.Apparel.get(i).setTextSize((float) 22.0);
			VarVault.Apparel.get(i).setTextColor(Color.parseColor("#AAFFAA"));
			
			// Are they wearing it?
			if (isWearing == 0)
				VarVault.Apparel.get(i).setText("  " + tempName);
			else
				VarVault.Apparel.get(i).setText("\u25a0 " + tempName);
			
			VarVault.Apparel.get(i).setOnLongClickListener(this);
			apparelList.addView(VarVault.Apparel.get(i));

			allApparel.moveToNext();
		}
	}

	public boolean onLongClick(View source) {
		// TODO For Items, wear them.
		ContentValues values = new ContentValues();
		ContentValues NOTvalues = new ContentValues();
		
		if (VarVault.Weapons.contains(source))
		{
			dbHelper database = new dbHelper(MainMenu.this);
	
			Log.d("DB", "Getting a writable database...");
			SQLiteDatabase db = database.getWritableDatabase();
			Log.d("DB", "...writable database gotten!");
			
			Log.d("DB", "Querying DB's current status...");
			
			String clipped = (String) VarVault.Weapons.get(VarVault.Weapons.indexOf(source)).getText();
			clipped = clipped.replace('\u25a0', ' ');
			clipped = clipped.replaceAll("^\\s+", "");
			
			
			Cursor currentWeapon = db.query("OwnedWeapons", new String[]{dbHelper.colName, dbHelper.colIsWearing}, "WeaponName='" + clipped + "'", null, null, null, "_id");
			
			if (currentWeapon.moveToFirst() == false)
				return false;
			
			int isWearing = currentWeapon.getInt(1);
			Log.d("DB", "...got the info!");
			
			if (isWearing == 1){
				values.put(dbHelper.colIsWearing, 0);
				NOTvalues.put(dbHelper.colIsWearing, 0);
			}
			else{
				values.put(dbHelper.colIsWearing, 1);
				NOTvalues.put(dbHelper.colIsWearing, 0);
			}
			
			Log.d("DB", "Trying to update equip...");
			db.update("OwnedWeapons", values, dbHelper.colName + "='" + clipped + "'", null);
			db.update("OwnedWeapons", NOTvalues, dbHelper.colName + "<>'" + clipped + "'", null);
			Log.d("DB", "...updated equipped status!");
			
			
			Cursor allWeapons = db.query("OwnedWeapons", new String[]{dbHelper.colName, dbHelper.colIsWearing}, null, null, null, null, "_id");
			allWeapons.moveToFirst();

			for (int i = 0; !allWeapons.isAfterLast(); i++){
				
				String temp = allWeapons.getString(0);
				int ruWear = allWeapons.getInt(1);
				
				if(ruWear == 1)
					VarVault.Weapons.get(i).setText("\u25a0 " + temp);
				else
					VarVault.Weapons.get(i).setText("  " + temp);
				
				allWeapons.moveToNext();
			}
			updateWG();
			return true;
		}
		
		if (VarVault.Apparel.contains(source))
		{
			dbHelper database = new dbHelper(MainMenu.this);
	
			Log.d("DB", "Getting a writable database...");
			SQLiteDatabase db = database.getWritableDatabase();
			Log.d("DB", "...writable database gotten!");
			
			Log.d("DB", "Querying DB's current status...");
			
			String clipped = (String) VarVault.Apparel.get(VarVault.Apparel.indexOf(source)).getText();
			clipped = clipped.replace('\u25a0', ' ');
			clipped = clipped.replaceAll("^\\s+", "");
			
			
			Cursor currentApparel = db.query("OwnedApparel", new String[]{dbHelper.colName, dbHelper.colType, dbHelper.colIsWearing}, "WeaponName='" + clipped + "'", null, null, null, "_id");
			
			if (currentApparel.moveToFirst() == false)
				return false;
			
			int type = currentApparel.getInt(1);
			int isWearing = currentApparel.getInt(2);
			Log.d("DB", "...got the info!");
			
			if (isWearing == 1){
				values.put(dbHelper.colIsWearing, 0);
				NOTvalues.put(dbHelper.colIsWearing, 0);
			}
			else{
				values.put(dbHelper.colIsWearing, 1);
				NOTvalues.put(dbHelper.colIsWearing, 0);
			}
			
			Log.d("DB", "Trying to update wear...");
			// Update wear based on type
			switch (type){
			
			case 1:
				db.update("OwnedApparel", values, dbHelper.colName + "='" + clipped + "' AND " + dbHelper.colType + "=" + 1, null);
				db.update("OwnedApparel", NOTvalues, dbHelper.colName + "<>'" + clipped + "' AND (" + dbHelper.colType + "=" + 1 + " OR "+ dbHelper.colType + "=" + 3 + ")", null);
				break;
			case 2:
				db.update("OwnedApparel", values, dbHelper.colName + "='" + clipped + "' AND " + dbHelper.colType + "=" + 2, null);
				db.update("OwnedApparel", NOTvalues, dbHelper.colName + "<>'" + clipped + "' AND (" + dbHelper.colType + "=" + 2 + " OR "+ dbHelper.colType + "=" + 3 + ")", null);
				break;
			case 3:
				db.update("OwnedApparel", values, dbHelper.colName + "='" + clipped + "' AND " + dbHelper.colType + "=" + 3, null);
				db.update("OwnedApparel", NOTvalues, dbHelper.colName + "<>'" + clipped + "' AND (" + dbHelper.colType + "=" + 1 + " OR "+ dbHelper.colType + "=" + 2 + " OR "+ dbHelper.colType + "=" + 3 + ")", null);
				break;
			case 4:
				db.update("OwnedApparel", values, dbHelper.colName + "='" + clipped + "' AND " + dbHelper.colType + "=" + 4, null);
				db.update("OwnedApparel", NOTvalues, dbHelper.colName + "<>'" + clipped + "' AND " + dbHelper.colType + "=" + 4, null);
				break;
			}
			Log.d("DB", "...updated wear status!");
			
			
			Cursor allApparel = db.query("OwnedApparel", new String[]{dbHelper.colName, dbHelper.colIsWearing}, null, null, null, null, "_id");
			allApparel.moveToFirst();

			for (int i = 0; !allApparel.isAfterLast(); i++){
				
				String temp = allApparel.getString(0);
				int ruWear = allApparel.getInt(1);
				
				if(ruWear == 1)
					VarVault.Apparel.get(i).setText("\u25a0 " + temp);
				else
					VarVault.Apparel.get(i).setText("  " + temp);
				
				allApparel.moveToNext();
			}
			updateWG();
			return true;
		}
		
		return false;
	}
	
	private void updateWG(){
		
		VarVault.curWG.setValue(0);
		
		dbHelper database = new dbHelper(MainMenu.this);
		
		Log.d("DB", "Getting a writable database...");
		SQLiteDatabase db = database.getWritableDatabase();
		Log.d("DB", "...writable database gotten!");
		
		Log.d("DB", "Querying DB's current status...");
		Cursor apparel = db.query("OwnedApparel", new String[]{dbHelper.colWG}, null, null, null, null, "_id");
		
		apparel.moveToFirst();

		while (!apparel.isAfterLast()){
			
			int weight = apparel.getInt(0);
			VarVault.curWG.setValue(VarVault.curWG.getValue() + weight);
			
			apparel.moveToNext();
		}
		
		Log.d("DB", "Querying DB's current status...");
		Cursor weapons = db.query("OwnedWeapons", new String[]{dbHelper.colWG}, null, null, null, null, "_id");
		
		weapons.moveToFirst();

		while (!weapons.isAfterLast()){
			
			int weight = weapons.getInt(0);
			VarVault.curWG.setValue(VarVault.curWG.getValue() + weight);
			
			weapons.moveToNext();
		}
		
		VarVault.wg.setText("WG: " + VarVault.curWG.getValue() + "/" + VarVault.maxWG.getValue());
		
	}

	private void updateCAPS() {
		Log.i("UPDATE", "Going into update");
		SharedPreferences prefs = getSharedPreferences("STATS", 0);
		prefs.edit().putInt("CAPS", VarVault.curCaps.getValue()).commit();
		
		VarVault.caps.setText("Caps: " + VarVault.curCaps.getValue());
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
