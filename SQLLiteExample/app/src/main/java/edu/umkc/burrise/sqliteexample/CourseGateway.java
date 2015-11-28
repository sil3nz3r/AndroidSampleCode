package edu.umkc.burrise.sqliteexample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.Assert;

public class CourseGateway {
	
	private Context context;
	
	public CourseGateway(Context context) {
		this.context = context;
	}
	
	public ArrayList<Course> findAll() {
		ArrayList<Course> courses = new ArrayList<Course>();

		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] from = { DatabaseHelper.FIELD_ID, DatabaseHelper.FIELD_COURSE_NAME};
		Cursor cursor = db.query(DatabaseHelper.COURSES_TABLE, from, null, null, null,
		null, null);
		
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String course_name = cursor.getString(1);
			courses.add(new Course(course_name,id));
		}
		dbHelper.close();
		return courses;
	}
	
	public long insert(String courseName) {
		// insert record into DB and get back primary
		// key for new record.
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.FIELD_COURSE_NAME, courseName);
		long cs490_id = db.insertOrThrow(DatabaseHelper.COURSES_TABLE, null, values);

		// Query for the value of the autoincrement field
		String[] from = { DatabaseHelper.FIELD_ID,
				DatabaseHelper.FIELD_COURSE_NAME };

		Cursor cursor = db.query(DatabaseHelper.COURSES_TABLE,
				from,
				DatabaseHelper.FIELD_COURSE_NAME + "= '" + courseName + "'",
				null,
				null,
				null,
				null);
	
		cursor.moveToNext();
		long value_of_autoincrement_field = cursor.getLong(0);
		dbHelper.close();
		return value_of_autoincrement_field; // return the primary key created by the DB
	}

	// id is primary key for record to update
	// courseName is an alpha numeric value including blank but can not be null
	// Throws:
	//   Exception - if there is no record for given id
	public void update(long id, String courseName) throws Exception {
		// Note, this method doesn't actually update the DB, but
		//   it does demonstrate exceptions and assertions.
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] from = { DatabaseHelper.FIELD_ID};
		Cursor cursor = db.query(DatabaseHelper.COURSES_TABLE,
				from,
				DatabaseHelper.FIELD_ID + "= " + id + "",
				null,
				null,
				null,
				null);
		// If there isn't a record for this ID, Throw an exception
		if (!cursor.moveToNext()){
			Exception e = new Exception("id " + id + " invalid for update");
			throw e;
		}
		if (AssertSettings.PRIORITY1_ASSERTIONS)
			// There are many methods on Assert that can be called
			Assert.assertNotNull("courseName is null",courseName);

		// Note, Java also has builtin support for assertion checking.
		// Assertion checking in Java is off by default.
		// To turn on assertion checking, execute the following
		// from the command line (before installing your app):
		// adb shell setprop debug.assert 1
		// To turn it off:
		// adb shell setprop debug.assert 0
		// Note, adb is a program in:
		// C:\Program Files (x86)\Android\android-sdk\platform-tools

		// Builtin Java support for assertion
		int x = 1;
		assert x == 1;
	}

    // returns true if delete successful
	public boolean delete(long id) { // id is primary key for record
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DatabaseHelper.COURSES_TABLE, DatabaseHelper.FIELD_ID + "=" + id, null) > 0;
	}
}
