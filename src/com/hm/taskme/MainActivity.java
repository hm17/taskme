package com.hm.taskme;

import com.hm.taskme.db.DatabaseDAO;
import com.hm.taskme.tasks.Task;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private DatabaseDAO dbDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbDAO = new DatabaseDAO(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void saveTask(View view) {
		String description = getValueFromEditTextAndValidate(R.id.edit_task);
		if(description.length() == 0){
			//TODO: Clear text box
		} else {
			long id = dbDAO.saveTask(description);
			
			Task task = new Task(description);
			task.setId(id);
			
			//TODO:Add new task to list item.
		}
		
	}
	
	//public markComplete
	// Toast msg: Woot! Look at you doing big things! //Dynamic message
	
	//public deleteTask
	// Toast: Must not have been that important anyways.
	
    private String getValueFromEditTextAndValidate (int viewId) {
    	EditText editText = (EditText) findViewById(viewId);
    	String value = editText.getText().toString();
    	if(value.length() == 0) 
    		editText.setError("Oops, you're missing something.");
    	
    	return value;
    }

}
