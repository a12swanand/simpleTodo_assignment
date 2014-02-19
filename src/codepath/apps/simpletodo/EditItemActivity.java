package codepath.apps.simpletodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText etEditItem;
	private int itemIndex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		String currentText = getIntent().getStringExtra("currentText").toString();
		itemIndex = (getIntent().getIntExtra("index", -1));
		etEditItem = (EditText)findViewById(R.id.etEditItem);
		etEditItem.setText(currentText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	/**
	 * method to capture and send the updated text back
	 * @param v
	 */
	public void updateItem(View v){
		String updatedText = etEditItem.getText().toString();
		Intent data = new Intent();
		data.putExtra("updatedText", updatedText); 
		data.putExtra("index", itemIndex); 
		setResult(RESULT_OK, data);
		finish();
	}
}
