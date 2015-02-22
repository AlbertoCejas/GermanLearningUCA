package es.uca.tabu;

import android.app.Activity;
import android.os.Bundle;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabuUtils.hideActionBar(this);
		setContentView(R.layout.activity_about);
		
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		TabuUtils.updateLanguage(this);
	}
	
}
