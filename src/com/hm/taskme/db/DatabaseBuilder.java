package com.hm.taskme.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseBuilder extends SQLiteOpenHelper{
	private static final String CREATE_TASK_TABLE_QUERY="CREATE TABLE " + 
		DatabaseConstants.TASK_TABLE_NAME + " (" + 
		DatabaseConstants.KEY_ID +" integer primary key autoincrement, " +
		DatabaseConstants.DESCRIPTION_NAME + " text not null);";

	public DatabaseBuilder(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("DatabaseBuilder onCreate", "Creating all the tables.");
		try {
			db.execSQL(CREATE_TASK_TABLE_QUERY);
		} catch(SQLiteException e) {
			Log.e("DatabaseBuilder onCreate", 
					"Create table exception: " + e.getMessage());		
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println  ("TaskDBAdapter: Upgrading from version " 
				+ oldVersion + " to " + newVersion + " , which will destroy all old data");
		
		db.execSQL("drop table if exists " + DatabaseConstants.TASK_TABLE_NAME);
		onCreate(db);		
	}
}



