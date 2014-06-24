package com.skettidev.pipdroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends Activity implements OnClickListener{
	
	private LinearLayout frame = null;
	private TextView txt = null;
	private Typeface font;
	private MediaPlayer mp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        mp = MediaPlayer.create(getApplicationContext(), R.raw.boot);
        mp.start();
        
        txt = (TextView) findViewById(R.id.splash_info);
        frame = (LinearLayout) findViewById(R.id.splash_frame);
        
        //Set custom font
        font = Typeface.createFromAsset(getAssets(), "Monofonto.ttf");  
        txt.setTypeface(font);
        
        frame.setOnClickListener(this);
    }

	public void onClick(View source) {
		
		if (source.equals(frame))
		{
			txt.setTextAppearance(getApplicationContext(), R.style.text_clicked);
			txt.setTypeface(font);
			
			mp.stop();
			
			Intent i = new Intent(Splash.this, MainMenu.class);
			Splash.this.startActivity(i);
		}
	}
	
	@Override
	public void onDestroy(){
		mp.stop();
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	this.finish();
	    	return true;
	        //Log.d(this.getClass().getName(), "back button pressed");
	    }
	    return super.onKeyDown(keyCode, event);
	}
}