package es.uca.tabu;

import java.io.Serializable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class ResultActivity extends Activity {

	TextView questions;
	TextView clues;
	GridView gridview;
	NumberImageAdapter adapter;
	Button backToMenuBtn;
	GameManager gameManager;
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	

	@Override
	public void onResume() {
		super.onResume();
		TabuUtils.updateLanguage(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabuUtils.hideActionBar(this);
		setContentView(R.layout.activity_result);
		// Show the Up button in the action bar.
		//setupActionBar();
		
		questions = (TextView) findViewById(R.id.questions);
		clues = (TextView) findViewById(R.id.clues);
		gridview = (GridView) findViewById(R.id.gridview);
		backToMenuBtn = (Button) findViewById(R.id.backToMenu);
		
		gameManager = GameManager.getInstance(ResultActivity.this);
		
		questions.setText(String.valueOf(gameManager.getNumOfPassedQuestions()) + "/" + String.valueOf(gameManager.getNumOfQuestions()));
		clues.setText(String.valueOf(gameManager.getNumOfUsedClues()) + " " + getString(R.string.pistas));
		//questions.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100f * getResources().getDisplayMetrics().density);
		//clues.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f * getResources().getDisplayMetrics().density);
		
		
		adapter = new NumberImageAdapter(ResultActivity.this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent review = new Intent(getApplicationContext(), ReviewQuestionActivity.class);
				review.putExtra("EXTRA_QUESTION", (Serializable) gameManager.getQuestions().get(position));
				startActivity(review);
				finish();
			}
		});
		
		backToMenuBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent menu = new Intent(getApplicationContext(), MainMenuActivity.class);
				menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				gameManager.clean();
				startActivity(menu);
				finish();
			} 
		});
	}

	@Override
	public void onBackPressed() {
		if(StatisticsManager.isAlive()) {
			Intent mainmenu = new Intent(getApplicationContext(), IndividualStatistics.class);
			mainmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mainmenu);
			/**
			 * Close Login Screen
			 **/
			finish();
		} else {
			Intent mainmenu = new Intent(getApplicationContext(), MainMenuActivity.class);
			mainmenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mainmenu);
			/**
			 * Close Login Screen
			 **/
			finish();
		}

	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
