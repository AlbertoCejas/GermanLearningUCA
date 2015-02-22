package es.uca.tabu;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class IndividualStatistics extends Activity {

	static int ACTIVITY_NO = 1;

	GridView gridView = null; 
	ArrayList<Item> gridArray = new ArrayList<Item>(); 
	MenuAdapter customGridAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		TabuUtils.hideActionBar(this);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View app = inflater.inflate(R.layout.individual_activity_statistics, null);
		setContentView(app);
		setTitle("Activity " + ACTIVITY_NO);

		//set grid view item 
		Bitmap lastGameIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.lastgame);
		Bitmap PlayIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.playstat);
		Bitmap defIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.definitionstat);
		
		gridArray.add(new Item(lastGameIcon,getString(R.string.lastGame)));
		gridArray.add(new Item(PlayIcon, getString(R.string.playStatistics)));
		gridArray.add(new Item(defIcon, getString(R.string.defStatistics)));

		gridView = (GridView) app.findViewById(R.id.gridView1);

		customGridAdapter = new MenuAdapter(this, R.layout.row_grid, gridArray); 
		gridView.setAdapter(customGridAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() { 
			@Override public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
				switch(position) {
				case 0: //LastGame
					GameManager gamemanager = GameManager.getInstance(IndividualStatistics.this);
					if(gamemanager.initializeFromSharedPreferences()) {
						Intent conclusion = new Intent(getApplicationContext(), ResultActivity.class);
						conclusion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(conclusion);
						finish();
					}
					else 
						TabuUtils.showDialog(getString(R.string.error), getString(R.string.nolastgame), IndividualStatistics.this);
					break;
				case 1: //PlayStatistics
					StatisticsActivity.clfs.inmediateChangeTo(PlayStatistics.ACTIVITY_NO);
					break;
				case 2: //DefinitionsStatistics
					StatisticsActivity.clfs.inmediateChangeTo(DefStatistics.ACTIVITY_NO);
					break;
				}

			} });

		// Header button to show left menu
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);

		tabBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showMenu();
			}
		});

	}

	@Override
	public void onBackPressed() {
		Intent mainmenu = new Intent(getApplicationContext(), MainMenuActivity.class);
		mainmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(mainmenu);
		/**
		 * Close Login Screen
		 **/
		finish();
	}

	private void showMenu() {
		Bitmap viewCapture = null;
		View rootView = findViewById(android.R.id.content).getRootView();
		rootView.setDrawingCacheEnabled(true);
		viewCapture = Bitmap.createBitmap(rootView.getDrawingCache());
		rootView.setDrawingCacheEnabled(false);

		if (viewCapture != null)
			StatisticsActivity.clfs.setCache(viewCapture);

		// mark an index for the activity
		StatisticsActivity.clfs.performClick(ACTIVITY_NO);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		overridePendingTransition(0, 0);
		TabuUtils.updateLanguage(this);
	}
}
