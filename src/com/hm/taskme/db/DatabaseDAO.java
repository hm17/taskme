package com.hm.taskme.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hm.taskme.tasks.Task;

public class DatabaseDAO {
	
	private Context context;
	private DatabaseBuilder dbBuilder;
	private SQLiteDatabase db;
	
	private List<Task> tasks;
	
	public static final long ERROR_CODE = -1;

	public DatabaseDAO(Context c) {
		context = c;
		dbBuilder = new DatabaseBuilder(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
		openDB();
		
		tasks = new ArrayList<Task>();
	}
	
	public long saveTask(final String description) {		
		try {
			ContentValues newTaskValue = new ContentValues();
			newTaskValue.put(DatabaseConstants.DESCRIPTION_NAME, description);
			
			return db.insert(DatabaseConstants.TASK_TABLE_NAME, null, newTaskValue);
		} catch (SQLException e) {
			Log.v("insertUser", "Insert into database exception caught: " + e.getMessage());
			return ERROR_CODE;
		}
	}
	
	public Task getTaskById(long id) {
		for(Task task : tasks) {
			if(task.getId() == id)
				return task;
		}
		return null;
	}
	
	public List<Task> getTasks() {
		if(tasks.size() == 0) {
			tasks = getTasksFromDB();
		}
		
		return tasks;
	}
	
	private List<Task> getTasksFromDB() {
		List<Task> tasksFromDB = new ArrayList<Task>();
		
		Cursor cursor = db.query(DatabaseConstants.TASK_TABLE_NAME, null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				String description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.DESCRIPTION_NAME));
				long id = cursor.getLong(cursor.getColumnIndex(DatabaseConstants.KEY_ID));
				
				Task task = new Task(description);
				task.setId(id);
				
				tasksFromDB.add(task);
			} while(cursor.moveToNext());
		}		
		cursor.close();
		
		return tasksFromDB;
	}
	
	private void openDB() {
		try {
			db = dbBuilder.getWritableDatabase();
		} catch (SQLException e) {
			Log.v("openDB", "Open database exception caught: " + e.getMessage());
			db = dbBuilder.getReadableDatabase();
		}
	}
	
	private void close() {
		db.close();
	}

}
