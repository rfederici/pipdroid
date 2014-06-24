package com.skettidev.pipdroid;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.MapView;

public class VarVault {

	// SPECIAL stats

	protected static Stat strength = new Stat(), perception = new Stat(),
			endurance = new Stat(), charisma = new Stat(),
			intelligence = new Stat(), agility = new Stat(), luck = new Stat();
	protected static ArrayList<Stat> SPECIAL_STAT_VALUES = new ArrayList<Stat>();
	
	// Skill stats
	protected static Stat barter = new Stat(), big_guns = new Stat(),
			energy = new Stat(), explosives = new Stat(),
			lockpick = new Stat(), medicine = new Stat(), melee = new Stat(),
			repair = new Stat(), science = new Stat(), small_guns = new Stat(),
			sneak = new Stat(), speech = new Stat(), unarmed = new Stat();
	protected static ArrayList<Stat> SKILL_STAT_VALUES = new ArrayList<Stat>();
	
	// Three main buttons
	protected static ImageView stats, items, data;
	protected static ArrayList<ImageView> MAIN_BUTTONS = new ArrayList<ImageView>();

	// Top bar content
	protected static TextView title;
	protected static TextView bat;
	protected static TextView hp, ap;
	protected static TextView wg, caps;
	protected static Stat curWG, maxWG;
	protected static Stat curCaps;

	// Bottom bar content
	protected static TextView status, special, skills, perks, general;
	protected static LinearLayout statusLL, specialLL, skillsLL, perksLL,
			generalLL;
	protected static ArrayList<View> BOTTOM_BAR_STATS = new ArrayList<View>();

	protected static TextView weapons, apparel, aid, misc, ammo;
	protected static LinearLayout weaponsLL, apparelLL, aidLL, miscLL, ammoLL;
	protected static ArrayList<View> BOTTOM_BAR_ITEMS = new ArrayList<View>();

	// Sub-menus
	protected static TextView stimpak, cnd, rad, flashlight;

	protected static TextView str, strSTAT, per, perSTAT, end, endSTAT, chr,
			chrSTAT, intel, intelSTAT, agi, agiSTAT, luk, lukSTAT;
	protected static ArrayList<TextView> SUBMENU_SPECIAL = new ArrayList<TextView>();

	protected static TextView bart, barterSTAT, bgns, big_gunsSTAT, nrg,
			energySTAT, expl, explosivesSTAT, lock, lockpickSTAT, medi,
			medicineSTAT, mlee, meleeSTAT, rpar, repairSTAT, sci, scienceSTAT,
			sgns, small_gunsSTAT, snek, sneakSTAT, spch, speechSTAT, uarm,
			unarmedSTAT;
	protected static ArrayList<TextView> SUBMENU_SKILLS = new ArrayList<TextView>();

	// Items
	final static ArrayList<TextView> Weapons = new ArrayList<TextView>();
	final static ArrayList<TextView> Apparel = new ArrayList<TextView>();

	// Images
	protected static ImageView specialImage, skillImage;
	protected static MapView map;
	protected static Typeface font;


	// Camera stuff
	protected static Camera mCamera;
	protected static SurfaceView preview;
	protected static SurfaceHolder mHolder;
	public static boolean isCamOn = false;

	// Battery info receiver
	protected static BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			int level = intent.getIntExtra("level", 0);
			bat.setText("Battery: " + String.valueOf(level) + " % ");
		}
	};

	// Misc stuff
	protected static int numContacts = 0;
}
