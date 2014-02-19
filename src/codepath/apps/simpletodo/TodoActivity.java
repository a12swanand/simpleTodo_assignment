package codepath.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private ArrayList<String> items;
	private ListView lvItems;
	private EditText etNewItem;
	ArrayAdapter<String> itemsAdapter;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		lvItems = (ListView) findViewById(R.id.lvItems);
		etNewItem = (EditText) findViewById(R.id.etNewItem);

		readItems();

		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);

		setupListViewListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	/**
	 * method to add item to the to do Items
	 * @param v
	 */
	public void addTodoItem(View v) {
		String itemText = etNewItem.getText().toString();
		itemsAdapter.add(itemText);
		writeItems();
		etNewItem.setText("");
	}

	/**
	 * method to hold all event listeners on list view listener
	 */
	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
		});

		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aView, View item, int pos,
					long id) {
				Intent edit = new Intent(TodoActivity.this,
						EditItemActivity.class);
				edit.putExtra("index", pos);
				edit.putExtra("currentText", items.get(pos));
				startActivityForResult(edit, REQUEST_CODE);
			}
		});

	}

	/**
	 * utility method to read data from text file
	 */
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todostorage.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
		}
	}

	/**
	 * utility method to write data to text file
	 */
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todostorage.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			
			String updatedItem = data.getStringExtra("updatedText");
			int index = data.getIntExtra("index", -1);
			
			items.set(index, updatedItem);
			itemsAdapter.notifyDataSetChanged();
			writeItems();
		}
	}
}
