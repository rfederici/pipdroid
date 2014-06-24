package com.skettidev.pipdroid;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class HandleSound {
	
	public static SoundPool sp;
	public static int aud_newTab = 0;
	public static int aud_selection = 0;
	public static int aud_stimpak = 0;
	
	public static void initSound(Context context){
		sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		aud_newTab = sp.load(context, R.raw.newtab, 1);
		aud_selection = sp.load(context, R.raw.selection, 1);
		aud_stimpak = sp.load(context, R.raw.stimpak, 1);
	}
	
	public static void playSound(int sound){
		sp.play(sound, 1, 1, 0, 0, 1);
	}
	public static void playSound(){
		sp.play(aud_selection, 1, 1, 0, 0, 1);
	}
}
